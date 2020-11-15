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

/**
 * Action command for updating user's password by admin.
 *
 * @author Vladislav Drobot
 */
@CommandAccessLevel(AccessType.ADMIN)
public class UpdateUserPasswordCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(UpdateUserPasswordCommand.class);
    private static final String ASTERISK = "*";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        String newPassword = request.getParameter(RequestParameter.NEW_PASSWORD);
        String stringUserId = request.getParameter(RequestParameter.USER_ID);
        int userId;
        try {
            userId = Integer.parseInt(stringUserId);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, "Incorrect user id value");
            return null;
        }
        try {
            UserService userService = UserServiceImpl.INSTANCE;
            StringBuilder sb;
            if (userService.updatePassword(userId, newPassword)) {
                sb = new StringBuilder(UrlPattern.USER_INFO);
                page = sb.deleteCharAt(sb.length() - 1).append(userId).toString();
                LOGGER.log(Level.INFO, "Password has been changed");
            } else {
                LOGGER.log(Level.INFO, "New password is invalid");
                HttpSession session = request.getSession();
                session.setAttribute(SessionAttribute.VALIDATED, true);
                sb = new StringBuilder(UrlPattern.ADMIN_CHANGING_PASSWORD);
                int indexOfAsterisk = sb.indexOf(ASTERISK);
                page = sb.replace(indexOfAsterisk, indexOfAsterisk + 1, stringUserId).toString();
                LOGGER.log(Level.DEBUG, "Flag \"USER_EXISTS\" has been set to true");
                session.setAttribute(SessionAttribute.USER_EXISTS, true);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
