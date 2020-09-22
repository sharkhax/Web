package com.drobot.web.controller;

import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandProvider;
import com.drobot.web.controller.command.JspPath;
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

@WebServlet(urlPatterns = "/mainController")
public class Controller extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

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
        Optional<ActionCommand> optionalCommand = CommandProvider.defineCommand(request);
        String page;
        RequestDispatcher dispatcher;
        try {
            ActionCommand command = optionalCommand.orElseThrow();
            page = command.execute(request);
            dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } catch (CommandException e) {
            LOGGER.log(Level.ERROR, "Redirecting to the error page", e);
            dispatcher = getServletContext().getRequestDispatcher(JspPath.ERROR);
            dispatcher.forward(request, response);
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
