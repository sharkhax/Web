package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.UrlPattern;
import com.drobot.web.controller.command.AccessType;
import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandAccessLevel;
import com.drobot.web.exception.CommandException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.service.UserService;
import com.drobot.web.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Action command for blocking user's account.
 *
 * @author Vladislav Drobot
 */
@CommandAccessLevel(AccessType.ADMIN)
public class BlockUserCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(BlockUserCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        String stringUserId = request.getParameter(RequestParameter.USER_ID);
        int userId;
        try {
            userId = Integer.parseInt(stringUserId);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, "Incorrect user id value");
            return null;
        }
        UserService userService = UserServiceImpl.INSTANCE;
        try {
            if (userService.blockUser(userId)) {
                HttpSession session = request.getSession();
                Map<String, String> fields =
                        (Map<String, String>) session.getAttribute(SessionAttribute.USER_DATA_FIELDS);
                fields.replace(RequestParameter.USER_STATUS, Entity.Status.BLOCKED.toString());
                session.setAttribute(SessionAttribute.USER_DATA_FIELDS, fields);
                LOGGER.log(Level.DEBUG, "User data fields have been updated");
                LOGGER.log(Level.INFO, "User has been blocked successfully");
            } else {
                LOGGER.log(Level.ERROR, "User has not been blocked");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        StringBuilder sb = new StringBuilder(UrlPattern.USER_INFO);
        page = sb.deleteCharAt(sb.length() - 1).append(userId).toString();
        return page;
    }
}
