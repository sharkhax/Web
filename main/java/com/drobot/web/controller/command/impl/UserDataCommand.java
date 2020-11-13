package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.UrlPattern;
import com.drobot.web.controller.command.AccessType;
import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandAccessLevel;
import com.drobot.web.exception.CommandException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.User;
import com.drobot.web.model.service.UserService;
import com.drobot.web.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

@CommandAccessLevel(AccessType.ADMIN)
public class UserDataCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(UserDataCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        String stringUserId = request.getParameter(RequestParameter.USER_INFO_ID);
        int userId;
        try {
            userId = Integer.parseInt(stringUserId != null ? stringUserId : "");
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, "Incorrect user id value, returning null");
            return null;
        }
        UserService userService = UserServiceImpl.INSTANCE;
        try {
            Optional<User> optionalUser = userService.findById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Map<String, String> fields = userService.packUserIntoMap(user);
                HttpSession session = request.getSession();
                session.setAttribute(SessionAttribute.USER_DATA_FIELDS, fields);
                session.setAttribute(SessionAttribute.USER_INFO_ID, userId);
                LOGGER.log(Level.DEBUG, "User data has been got");
                StringBuilder sb = new StringBuilder(UrlPattern.USER_INFO);
                page = sb.deleteCharAt(sb.length() - 1).append(userId).toString();
            } else {
                LOGGER.log(Level.DEBUG, "No user found");
                page = null;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
