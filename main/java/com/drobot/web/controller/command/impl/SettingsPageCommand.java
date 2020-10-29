package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.UrlPattern;
import com.drobot.web.controller.command.AccessType;
import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandAccessLevel;
import com.drobot.web.exception.CommandException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.service.UserEmployeeService;
import com.drobot.web.model.service.impl.UserEmployeeServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@CommandAccessLevel({AccessType.ADMIN, AccessType.DOCTOR, AccessType.ASSISTANT})
public class SettingsPageCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(SettingsPageCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page = UrlPattern.PERSONAL_SETTINGS;
        UserEmployeeService userEmployeeService = UserEmployeeServiceImpl.INSTANCE;
        HttpSession session = request.getSession();
        Map<String, String> loginInfo = (Map<String, String>) session.getAttribute(RequestParameter.LOGIN_INFO);
        if (loginInfo == null) {
            throw new CommandException("Login info is null");
        }
        int userId = Integer.parseInt(loginInfo.get(RequestParameter.USER_ID));
        try {
            Map<String, String> fields = userEmployeeService.loadUserData(userId);
            session.setAttribute(RequestParameter.USER_DATA_FIELDS, fields);
            LOGGER.log(Level.DEBUG, "User data has been loaded");
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
