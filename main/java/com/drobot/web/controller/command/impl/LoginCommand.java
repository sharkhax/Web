package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.JspPath;
import com.drobot.web.controller.command.RequestParameter;
import com.drobot.web.exception.CommandException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.User;
import com.drobot.web.model.service.UserService;
import com.drobot.web.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LoginCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);
    private static final String WRONG_LOGIN_PASSWORD_MSG = "Incorrect login or password";
    private final UserService userService = new UserServiceImpl();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(RequestParameter.LOGIN);
        String password = request.getParameter(RequestParameter.PASSWORD);
        Optional<User.Role> optional;
        try {
            optional = userService.defineRole(login, password);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        String page;
        if (optional.isPresent()) {
            User.Role role = optional.get();
            switch (role) {
                case ADMIN -> {
                    page = JspPath.ADMIN_MAIN_PAGE;
                    LOGGER.log(Level.DEBUG, "Logged in as " + role.toString());
                }
                case DOCTOR -> {
                    page = JspPath.DOCTOR_MAIN_PAGE;
                    LOGGER.log(Level.DEBUG, "Logged in as " + role.toString());
                }
                case ASSISTANT -> {
                    page = JspPath.ASSISTANT_MAIN_PAGE;
                    LOGGER.log(Level.DEBUG, "Logged in as " + role.toString());
                }
                default -> {
                    LOGGER.log(Level.ERROR, "Can't log in: role" + role.toString() + " is unsupported");
                    throw new CommandException("Unsupported role");
                }
            }
        } else {
            request.setAttribute(RequestParameter.WRONG_LOGIN_PASSWORD, WRONG_LOGIN_PASSWORD_MSG);
            page = JspPath.LOGIN_PAGE;
        }
        return page;
    }
}
