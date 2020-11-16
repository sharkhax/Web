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

/**
 * Action command for redirecting to updating an user page.
 *
 * @author Vladislav Drobot
 */
@CommandAccessLevel(AccessType.ADMIN)
public class RedirectToUpdateUserCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(RedirectToUpdateUserCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        String stringUserId = request.getParameter(RequestParameter.USER_ID);
        int userId;
        try {
            userId = Integer.parseInt(stringUserId);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, "Incorrect user ID value");
            return null;
        }
        try {
            UserService userService = UserServiceImpl.INSTANCE;
            if (userService.exists(userId)) {
                HttpSession session = request.getSession();
                Integer userInfoId = (Integer) session.getAttribute(SessionAttribute.USER_INFO_ID);
                if (userInfoId == null || userId != userInfoId) {
                    Optional<User> optional = userService.findById(userId);
                    User user = optional.orElseThrow();
                    Map<String, String> fields = userService.packIntoMap(user);
                    session.setAttribute(SessionAttribute.USER_DATA_FIELDS, fields);
                    session.setAttribute(SessionAttribute.USER_INFO_ID, userId);
                    LOGGER.log(Level.DEBUG, "User data fields have been refilled");
                }
                StringBuilder sb = new StringBuilder(UrlPattern.UPDATING_USER);
                int indexOfAsterisk = sb.indexOf(UrlPattern.ASTERISK);
                sb.replace(indexOfAsterisk, indexOfAsterisk + 1, stringUserId);
                page = sb.toString();
                LOGGER.log(Level.DEBUG, "Flag \"USER_EXISTS\" has been set to true");
                session.setAttribute(SessionAttribute.USER_EXISTS, true);
            } else {
                LOGGER.log(Level.DEBUG, "User with id " + stringUserId + " doesn't exist, returning null");
                page = null;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
