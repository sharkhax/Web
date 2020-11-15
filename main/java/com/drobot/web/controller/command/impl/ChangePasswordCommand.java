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

/**
 * Action command for changing user's password.
 *
 * @author Vladislav Drobot
 */
@CommandAccessLevel({AccessType.ADMIN, AccessType.ASSISTANT, AccessType.DOCTOR})
public class ChangePasswordCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ChangePasswordCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        String currentPassword = request.getParameter(RequestParameter.CURRENT_PASSWORD);
        String newPassword = request.getParameter(RequestParameter.NEW_PASSWORD);
        if (currentPassword == null || newPassword == null) {
            LOGGER.log(Level.ERROR, "One of password values is null");
            return null;
        }
        Map<String, String> fields;
        HttpSession session = request.getSession();
        Map<String, String> loginInfo = (Map<String, String>) session.getAttribute(SessionAttribute.LOGIN_INFO);
        int userId = Integer.parseInt(loginInfo.get(SessionAttribute.USER_ID));
        String login = loginInfo.get(RequestParameter.LOGIN);
        UserService userService = UserServiceImpl.INSTANCE;
        try {
            Optional<User> optionalUser = userService.signIn(login, currentPassword);
            if (optionalUser.isPresent()) {
                if (!currentPassword.equals(newPassword) && userService.updatePassword(userId, newPassword)) {
                    page = UrlPattern.PERSONAL_SETTINGS;
                    fields = null;
                    LOGGER.log(Level.INFO, "Password has been changed");
                } else {
                    LOGGER.log(Level.INFO, "New password is invalid");
                    session.setAttribute(SessionAttribute.VALIDATED, true);
                    fields = new HashMap<>();
                    fields.put(RequestParameter.CURRENT_PASSWORD, currentPassword);
                    page = UrlPattern.CHANGING_PASSWORD;
                }
            } else {
                LOGGER.log(Level.INFO, "Current password is incorrect");
                session.setAttribute(SessionAttribute.VALIDATED, true);
                fields = new HashMap<>();
                fields.put(RequestParameter.NEW_PASSWORD, newPassword);
                page = UrlPattern.CHANGING_PASSWORD;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        session.setAttribute(SessionAttribute.CHANGING_PASSWORD_FIELDS, fields);
        return page;
    }
}
