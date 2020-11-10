package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.UrlPattern;
import com.drobot.web.controller.command.AccessType;
import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandAccessLevel;
import com.drobot.web.exception.CommandException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.service.UserService;
import com.drobot.web.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@CommandAccessLevel(AccessType.ADMIN)
public class RedirectToUpdatingPasswordCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(RedirectToUpdatingPasswordCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        String stringUserId = request.getParameter(RequestParameter.USER_ID);
        int userId;
        try {
            userId = Integer.parseInt(stringUserId != null ? stringUserId : "");
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, "Incorrect user ID value");
            return null;
        }
        try {
            UserService userService = UserServiceImpl.INSTANCE;
            if (userService.exists(userId)) {
                StringBuilder sb = new StringBuilder(UrlPattern.ADMIN_CHANGING_PASSWORD);
                int indexOfAsterisk = sb.indexOf(UrlPattern.ASTERISK);
                sb.replace(indexOfAsterisk, indexOfAsterisk + 1, stringUserId);
                page = sb.toString();
                HttpSession session = request.getSession();
                LOGGER.log(Level.DEBUG, "Flag \"USER_EXISTS\" has been set to true");
                session.setAttribute(SessionAttribute.USER_EXISTS, true);
            } else {
                LOGGER.log(Level.DEBUG, "User with id " + userId + " doesn't exist, returning null");
                page = null;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
