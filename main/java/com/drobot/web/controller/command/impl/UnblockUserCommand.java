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
public class UnblockUserCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(UnblockUserCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        HttpSession session = request.getSession();
        Map<String, String> userFields = (Map<String, String>) session.getAttribute(SessionAttribute.USER_DATA_FIELDS);
        int userId = Integer.parseInt(userFields.get(RequestParameter.USER_ID));
        int employeeId = Integer.parseInt(userFields.get(RequestParameter.EMPLOYEE_ID));
        UserService userService = UserServiceImpl.INSTANCE;
        try {
            if (userService.unblockUser(userId, employeeId)) {
                userFields.replace(RequestParameter.USER_STATUS, Entity.Status.ACTIVE.toString());
                session.setAttribute(SessionAttribute.USER_DATA_FIELDS, userFields);
                LOGGER.log(Level.DEBUG, "User data fields have been updated");
                LOGGER.log(Level.INFO, "User has been unblocked successfully");
            } else {
                LOGGER.log(Level.ERROR, "User has not been unblocked");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        StringBuilder sb = new StringBuilder(UrlPattern.USER_INFO);
        page = sb.deleteCharAt(sb.length() - 1).append(userId).toString();
        return page;
    }
}
