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

@CommandAccessLevel(AccessType.ADMIN)
public class BlockUserCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(BlockUserCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        int userId = Integer.parseInt(request.getParameter(RequestParameter.USER_ID));
        UserService userService = UserServiceImpl.INSTANCE;
        try {
            if (userService.blockUser(userId)) {
                LOGGER.log(Level.DEBUG, "User has been blocked successfully");
                HttpSession session = request.getSession();
                Map<String, String> fields =
                        (Map<String, String>) session.getAttribute(SessionAttribute.USER_DATA_FIELDS);
                fields.replace(RequestParameter.USER_STATUS, Entity.Status.BLOCKED.toString());
                session.setAttribute(SessionAttribute.USER_DATA_FIELDS, fields);
                LOGGER.log(Level.DEBUG, "User data fields have been updated");
                StringBuilder sb = new StringBuilder(UrlPattern.USER_INFO);
                page = sb.deleteCharAt(sb.length() - 1).append(userId).toString();
            } else {
                LOGGER.log(Level.FATAL, "User has not been blocked");
                throw new CommandException("User has not been blocked");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
