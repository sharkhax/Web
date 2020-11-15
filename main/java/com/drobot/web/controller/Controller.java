package com.drobot.web.controller;

import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandProvider;
import com.drobot.web.exception.CommandException;
import com.drobot.web.exception.ConnectionPoolException;
import com.drobot.web.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Controller class used to process all requests from users.
 *
 * @author Vladislav Drobot
 */
@WebServlet(urlPatterns = {UrlPattern.MAIN_CONTROLLER})
public class Controller extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Optional<ActionCommand> optionalCommand = CommandProvider.defineCommand(request);
        String page;
        try {
            ActionCommand command = optionalCommand.orElseThrow();
            page = command.execute(request);
            if (page == null) {
                LOGGER.log(Level.DEBUG, "Error 404, page is not found");
                page = JspPath.ERROR_404;
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + page);
            }
        } catch (CommandException e) {
            LOGGER.log(Level.ERROR, "Redirecting to the error page", e);
            throw new ServletException(e);
        }
    }

    @Override
    public void destroy() {
        try {
            ConnectionPool.INSTANCE.destroyPool();
        } catch (ConnectionPoolException e) {
            LOGGER.log(Level.ERROR, e);
        }
    }
}
