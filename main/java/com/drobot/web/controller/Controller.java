package com.drobot.web.controller;

import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandProvider;
import com.drobot.web.controller.command.JspPath;
import com.drobot.web.controller.command.RequestParameter;
import com.drobot.web.exception.CommandException;
import com.drobot.web.exception.ConnectionPoolException;
import com.drobot.web.model.connection.ConnectionPool;
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

@WebServlet(urlPatterns = "/mainController")
public class Controller extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(Controller.class);
    private static final String NULL_PAGE_MSG = "Null page";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CommandProvider provider = new CommandProvider();
        Optional<ActionCommand> optionalCommand = provider.defineCommand(request);
        String page = null;
        RequestDispatcher dispatcher;
        try {
            if (optionalCommand.isPresent()) {
                ActionCommand command = optionalCommand.get();
                page = command.execute(request);
            }
            if (page == null) {
                page = JspPath.LOGIN_PAGE;
                request.setAttribute(RequestParameter.NULL_PAGE, NULL_PAGE_MSG);
            }
            dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } catch (CommandException e) {
            LOGGER.log(Level.ERROR, "Redirecting to the error page", e);
            // TODO: 05.09.2020 err page
        }
    }

    @Override
    public void destroy() {
        try {
            ConnectionPool.INSTANCE.destroyPool();
        } catch (ConnectionPoolException e) {
            LOGGER.log(Level.ERROR, e);
        }
        super.destroy();
    }
}
