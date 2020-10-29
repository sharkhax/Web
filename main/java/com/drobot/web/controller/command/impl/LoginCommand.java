package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.UrlPattern;
import com.drobot.web.controller.command.*;
import com.drobot.web.exception.CommandException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.entity.User;
import com.drobot.web.model.service.UserService;
import com.drobot.web.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CommandAccessLevel({AccessType.GUEST})
public class LoginCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);
    private final UserService userService = UserServiceImpl.INSTANCE;

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(RequestParameter.LOGIN);
        String password = request.getParameter(RequestParameter.PASSWORD);
        Optional<User> optional;
        try {
            optional = userService.signIn(login, password);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        HttpSession session = request.getSession();
        String page;
        if (optional.isPresent()) {
            User user = optional.get();
            if (user.getStatus() != Entity.Status.BLOCKED) {
                User.Role role = user.getRole();
                switch (role) {
                    case ADMIN -> {
                        session.setAttribute(RequestParameter.USER_ROLE,
                                RequestParameter.ADMIN_ROLE);
                        LOGGER.log(Level.DEBUG, "Logged in as " + role.toString());
                    }
                    case DOCTOR -> {
                        session.setAttribute(RequestParameter.USER_ROLE,
                                RequestParameter.DOCTOR_ROLE);
                        LOGGER.log(Level.DEBUG, "Logged in as " + role.toString());
                    }
                    case ASSISTANT -> {
                        session.setAttribute(RequestParameter.USER_ROLE,
                                RequestParameter.ASSISTANT_ROLE);
                        LOGGER.log(Level.DEBUG, "Logged in as " + role.toString());
                    }
                    default -> {
                        LOGGER.log(Level.ERROR, "Can't log in: role" + role.toString() + " is unsupported");
                        throw new CommandException("Unsupported role");
                    }
                }
                page = UrlPattern.MAIN_PAGE;
                session.setAttribute(RequestParameter.CURRENT_PAGE, UrlPattern.MAIN_PAGE);
                session.setAttribute(RequestParameter.WRONG_LOGIN_PASSWORD, null);
                session.setAttribute(RequestParameter.USER_BLOCKED, null);
                Map<String, String> loginInfo = new HashMap<>();
                loginInfo.put(RequestParameter.LOGIN, login);
                int userId = user.getId();
                loginInfo.put(RequestParameter.USER_ID, String.valueOf(userId));
                session.setAttribute(RequestParameter.LOGIN_INFO, loginInfo);
            } else {
                session.setAttribute(RequestParameter.USER_BLOCKED, true);
                page = UrlPattern.LOGIN_PAGE;
                LOGGER.log(Level.DEBUG, "User is blocked");
            }
        } else {
            session.setAttribute(RequestParameter.WRONG_LOGIN_PASSWORD, true);
            page = UrlPattern.LOGIN_PAGE;
        }
        return page;
    }
}
