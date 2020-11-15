package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.UrlPattern;
import com.drobot.web.controller.command.AccessType;
import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandAccessLevel;
import com.drobot.web.exception.CommandException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.model.entity.User;
import com.drobot.web.model.service.impl.EmployeeServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CommandAccessLevel(AccessType.ADMIN)
public class UpdateEmployeeCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(UpdateUserCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        String stringEmployeeId = request.getParameter(RequestParameter.EMPLOYEE_ID);
        int employeeId;
        try {
            employeeId = Integer.parseInt(stringEmployeeId);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, "Incorrect employee id value");
            return null;
        }
        HttpSession session = request.getSession();
        Map<String, String> currentFields =
                (Map<String, String>) session.getAttribute(SessionAttribute.EMPLOYEE_DATA_FIELDS);
        String newName = request.getParameter(RequestParameter.EMPLOYEE_NAME);
        String newSurname = request.getParameter(RequestParameter.EMPLOYEE_SURNAME);
        String newAge = request.getParameter(RequestParameter.EMPLOYEE_AGE);
        String newGender = request.getParameter(RequestParameter.EMPLOYEE_GENDER);
        String newPosition = request.getParameter(RequestParameter.EMPLOYEE_POSITION);
        String newHireDate = request.getParameter(RequestParameter.HIRE_DATE);
        Map<String, String> newFields = new HashMap<>();
        Map<String, String> emptyFields = new HashMap<>();
        if (newName.isEmpty()) {
            emptyFields.put(RequestParameter.EMPLOYEE_NAME, "true");
        } else {
            newFields.put(RequestParameter.EMPLOYEE_NAME, newName);
        }
        if (newSurname.isEmpty()) {
            emptyFields.put(RequestParameter.EMPLOYEE_SURNAME, "true");
        } else {
            newFields.put(RequestParameter.EMPLOYEE_SURNAME, newSurname);
        }
        if (newAge.isEmpty()) {
            emptyFields.put(RequestParameter.EMPLOYEE_AGE, "true");
        } else {
            newFields.put(RequestParameter.EMPLOYEE_AGE, newAge);
        }
        if (newGender.isEmpty()) {
            emptyFields.put(RequestParameter.EMPLOYEE_GENDER, "true");
        } else {
            newFields.put(RequestParameter.EMPLOYEE_GENDER, newGender);
        }
        if (newPosition.isEmpty()) {
            emptyFields.put(RequestParameter.EMPLOYEE_POSITION, "true");
        } else {
            newFields.put(RequestParameter.EMPLOYEE_POSITION, newPosition);
        }
        if (newHireDate.isEmpty()) {
            emptyFields.put(RequestParameter.HIRE_DATE, "true");
        } else {
            newFields.put(RequestParameter.HIRE_DATE, newHireDate);
        }
        if (newFields.isEmpty()) {
            StringBuilder sb = new StringBuilder(UrlPattern.EMPLOYEE_INFO);
            page = sb.deleteCharAt(sb.length() - 1).append(employeeId).toString();
            LOGGER.log(Level.DEBUG, "New fields are absent");
        } else {
            EmployeeServiceImpl service = EmployeeServiceImpl.INSTANCE;
            Map<String, String> existingFields = new HashMap<>();
            try {
                if (service.update(newFields, existingFields, currentFields)) {
                    Optional<Employee> optionalEmployee = service.findById(employeeId);
                    Employee employee = optionalEmployee.orElseThrow();
                    Map<String, String> fields = service.packEmployeeIntoMap(employee);
                    session.setAttribute(SessionAttribute.EMPLOYEE_DATA_FIELDS, fields);
                    LOGGER.log(Level.DEBUG, "Employee fields have been replaced");
                    StringBuilder sb = new StringBuilder(UrlPattern.EMPLOYEE_INFO);
                    page = sb.deleteCharAt(sb.length() - 1).append(employeeId).toString();
                    LOGGER.log(Level.INFO, "User has been updated successfully");
                } else {
                    StringBuilder sb = new StringBuilder(UrlPattern.UPDATING_EMPLOYEE);
                    int indexOfAsterisk = sb.indexOf(UrlPattern.ASTERISK);
                    page = sb.replace(indexOfAsterisk, indexOfAsterisk + 1, String.valueOf(employeeId)).toString();
                    session.setAttribute(SessionAttribute.EMPLOYEE_DATA_EMPTY_FIELDS, emptyFields);
                    session.setAttribute(SessionAttribute.EMPLOYEE_DATA_NEW_FIELDS, newFields);
                    session.setAttribute(SessionAttribute.EMPLOYEE_DATA_EXISTING_FIELDS, existingFields);
                    session.setAttribute(SessionAttribute.VALIDATED, true);
                    session.setAttribute(SessionAttribute.EMPLOYEE_EXISTS, true);
                    LOGGER.log(Level.DEBUG, "Flag \"EMPLOYEE_EXISTS\" is set to true");
                    LOGGER.log(Level.INFO, "User has not been updated");
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }
        return page;
    }
}
