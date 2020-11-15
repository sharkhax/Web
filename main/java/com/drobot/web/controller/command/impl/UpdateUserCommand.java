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
public class UpdateUserCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(UpdateUserCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        HttpSession session = request.getSession();
        Map<String, String> currentFields =
                (Map<String, String>) session.getAttribute(SessionAttribute.USER_DATA_FIELDS);
        int userId = Integer.parseInt(currentFields.get(RequestParameter.USER_ID));
        String newLogin = request.getParameter(RequestParameter.LOGIN);
        String newEmail = request.getParameter(RequestParameter.EMAIL);
        Map<String, String> newFields = new HashMap<>();
        Map<String, String> emptyFields = new HashMap<>();
        if (newLogin.isEmpty()) {
            emptyFields.put(RequestParameter.LOGIN, "true");
        } else {
            newFields.put(RequestParameter.LOGIN, newLogin);
        }
        if (newEmail.isEmpty()) {
            emptyFields.put(RequestParameter.EMAIL, "true");
        } else {
            newFields.put(RequestParameter.EMAIL, newEmail);
        }
        if (newFields.isEmpty()) {
            StringBuilder sb = new StringBuilder(UrlPattern.USER_INFO);
            page = sb.deleteCharAt(sb.length() - 1).append(userId).toString();
            LOGGER.log(Level.DEBUG, "New fields are absent");
        } else {
            UserService userService = UserServiceImpl.INSTANCE;
            Map<String, String> existingFields = new HashMap<>();
            try {
                if (userService.update(newFields, existingFields, currentFields)) {
                    Optional<User> optionalUser = userService.findById(userId);
                    User user = optionalUser.orElseThrow();
                    Map<String, String> fields = userService.packUserIntoMap(user);
                    session.setAttribute(SessionAttribute.USER_DATA_FIELDS, fields);
                    LOGGER.log(Level.DEBUG, "User fields have been replaced");
                    StringBuilder sb = new StringBuilder(UrlPattern.USER_INFO);
                    page = sb.deleteCharAt(sb.length() - 1).append(userId).toString();
                    LOGGER.log(Level.INFO, "User has been updated successfully");
                } else {
                    StringBuilder sb = new StringBuilder(UrlPattern.UPDATING_USER);
                    int indexOfAsterisk = sb.indexOf(UrlPattern.ASTERISK);
                    page = sb.replace(indexOfAsterisk, indexOfAsterisk + 1, String.valueOf(userId)).toString();
                    session.setAttribute(SessionAttribute.USER_DATA_EMPTY_FIELDS, emptyFields);
                    session.setAttribute(SessionAttribute.USER_DATA_NEW_FIELDS, newFields);
                    session.setAttribute(SessionAttribute.USER_DATA_EXISTING_FIELDS, existingFields);
                    session.setAttribute(SessionAttribute.VALIDATED, true);
                    session.setAttribute(SessionAttribute.USER_EXISTS, true);
                    LOGGER.log(Level.DEBUG, "Flag \"USER_EXISTS\" is set to true");
                    LOGGER.log(Level.INFO, "User has not been updated");
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }
        return page;
    }
}
