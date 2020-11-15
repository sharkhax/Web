package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.SessionAttribute;
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
import java.util.HashMap;
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
        String name = request.getParameter(RequestParameter.EMPLOYEE_NAME);
        String surname = request.getParameter(RequestParameter.EMPLOYEE_SURNAME);
        String age = request.getParameter(RequestParameter.EMPLOYEE_AGE);
        String position = request.getParameter(RequestParameter.EMPLOYEE_POSITION);
        String gender = request.getParameter(RequestParameter.EMPLOYEE_GENDER);
        String hireDate = request.getParameter(RequestParameter.HIRE_DATE);
        fields.put(RequestParameter.LOGIN, login);
        fields.put(RequestParameter.PASSWORD, password);
        fields.put(RequestParameter.EMAIL, email);
        fields.put(RequestParameter.EMPLOYEE_NAME, name);
        fields.put(RequestParameter.EMPLOYEE_SURNAME, surname);
        fields.put(RequestParameter.EMPLOYEE_AGE, age);
        fields.put(RequestParameter.EMPLOYEE_POSITION, position);
        fields.put(RequestParameter.EMPLOYEE_GENDER, gender);
        fields.put(RequestParameter.HIRE_DATE, hireDate);
        UserEmployeeService userEmployeeService = UserEmployeeServiceImpl.INSTANCE;
        String page;
        Map<String, String> existingFields = new HashMap<>();
        try {
            HttpSession session = request.getSession();
            if (userEmployeeService.register(fields, existingFields)) {
                session.setAttribute(SessionAttribute.USER_REGISTRATION_FIELDS, null);
                session.setAttribute(SessionAttribute.USER_REGISTRATION_EXISTING_FIELDS, null);
                page = UrlPattern.USER_REGISTRATION_SUCCESS;
                LOGGER.log(Level.INFO, "Registered successfully");
            } else {
                session.setAttribute(SessionAttribute.USER_REGISTRATION_FIELDS, fields);
                session.setAttribute(SessionAttribute.USER_REGISTRATION_EXISTING_FIELDS, existingFields);
                page = UrlPattern.USER_REGISTRATION_FAIL;
                LOGGER.log(Level.INFO, "Registration failed");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
