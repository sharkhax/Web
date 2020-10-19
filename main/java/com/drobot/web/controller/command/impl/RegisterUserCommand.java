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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CommandAccessLevel({AccessType.ADMIN})
public class RegisterUserCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(RegisterUserCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        Map<String, String> fields = new HashMap<>();
        String login = request.getParameter(RequestParameter.LOGIN);
        String password = request.getParameter(RequestParameter.PASSWORD);
        String email = request.getParameter(RequestParameter.EMAIL);
        String name = request.getParameter(RequestParameter.NAME);
        String surname = request.getParameter(RequestParameter.SURNAME);
        String age = request.getParameter(RequestParameter.AGE);
        String position = request.getParameter(RequestParameter.POSITION);
        String gender = request.getParameter(RequestParameter.GENDER);
        String hireDate = request.getParameter(RequestParameter.HIRE_DATE);
        fields.put(RequestParameter.LOGIN, login);
        fields.put(RequestParameter.PASSWORD, password);
        fields.put(RequestParameter.EMAIL, email);
        fields.put(RequestParameter.NAME, name);
        fields.put(RequestParameter.SURNAME, surname);
        fields.put(RequestParameter.AGE, age);
        fields.put(RequestParameter.POSITION, position);
        fields.put(RequestParameter.GENDER, gender);
        fields.put(RequestParameter.HIRE_DATE, hireDate);
        LOGGER.log(Level.DEBUG, "Fields map has been created");
        UserEmployeeService userEmployeeService = UserEmployeeServiceImpl.INSTANCE;
        String page;
        Map<String, String> existingFields = new HashMap<>();
        try {
            HttpSession session = request.getSession();
            if (userEmployeeService.register(fields, existingFields)) {
                session.setAttribute(RequestParameter.USER_REGISTRATION_FIELDS, null);
                session.setAttribute(RequestParameter.USER_REGISTRATION_EXISTING_FIELDS, null);
                page = UrlPattern.USER_REGISTRATION_SUCCESS;
                LOGGER.log(Level.DEBUG, "Registered successfully");
            } else {
                session.setAttribute(RequestParameter.USER_REGISTRATION_FIELDS, fields);
                session.setAttribute(RequestParameter.USER_REGISTRATION_EXISTING_FIELDS, existingFields);
                page = UrlPattern.USER_REGISTRATION_FAIL;
                LOGGER.log(Level.DEBUG, "Registration failed");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
