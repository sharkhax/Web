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
import java.util.HashMap;
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
            page = UrlPattern.USER_LIST;
            LOGGER.log(Level.ERROR, "Incorrect user id value, redirected to user list");
            return page;
        }
        UserService userService = UserServiceImpl.INSTANCE;
        try {
            Optional<User> optionalUser = userService.findById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Map<String, String> fields = new HashMap<>();
                fields.put(RequestParameter.USER_ID, String.valueOf(userId));
                fields.put(RequestParameter.LOGIN, user.getLogin());
                fields.put(RequestParameter.EMAIL, user.getEmail());
                fields.put(RequestParameter.USER_ROLE, user.getRole().toString());
                fields.put(RequestParameter.USER_STATUS, user.getStatus().toString());
                fields.put(RequestParameter.EMPLOYEE_ID, String.valueOf(user.getEmployeeId()));
                HttpSession session = request.getSession();
                session.setAttribute(SessionAttribute.USER_DATA_FIELDS, fields);
                session.setAttribute(SessionAttribute.USER_INFO_ID, userId);
                LOGGER.log(Level.DEBUG, "User data has been got");
                StringBuilder sb = new StringBuilder(UrlPattern.USER_INFO);
                page = sb.deleteCharAt(sb.length() - 1).append(userId).toString();
            } else {
                LOGGER.log(Level.DEBUG, "No user found, redirected to user list");
                page = UrlPattern.USER_LIST;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}