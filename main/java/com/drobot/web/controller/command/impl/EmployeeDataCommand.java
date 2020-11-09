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
import com.drobot.web.model.service.EmployeeService;
import com.drobot.web.model.service.impl.EmployeeServiceImpl;
import com.drobot.web.model.util.DateConverter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CommandAccessLevel(AccessType.ADMIN)
public class EmployeeDataCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(EmployeeDataCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        String stringEmployeeId = request.getParameter(RequestParameter.EMPLOYEE_INFO_ID);
        int employeeId;
        try {
            employeeId = Integer.parseInt(stringEmployeeId != null ? stringEmployeeId : "");
        } catch (NumberFormatException e) {
            page = UrlPattern.EMPLOYEE_LIST;
            LOGGER.log(Level.ERROR, "Incorrect employee id value, redirected to employee list");
            return page;
        }
        EmployeeService employeeService = EmployeeServiceImpl.INSTANCE;
        try {
            Optional<Employee> optionalEmployee = employeeService.findById(employeeId);
            if (optionalEmployee.isPresent()) {
                Employee employee = optionalEmployee.get();
                Map<String, String> fields = new HashMap<>();
                fields.put(RequestParameter.EMPLOYEE_ID, String.valueOf(employeeId));
                fields.put(RequestParameter.EMPLOYEE_NAME, employee.getName());
                fields.put(RequestParameter.EMPLOYEE_SURNAME, employee.getSurname());
                fields.put(RequestParameter.EMPLOYEE_AGE, String.valueOf(employee.getAge()));
                fields.put(RequestParameter.EMPLOYEE_GENDER, String.valueOf(employee.getGender()));
                fields.put(RequestParameter.EMPLOYEE_POSITION, employee.getPosition().toString());
                long hireDateMillis = employee.getHireDateMillis();
                String hireDate = DateConverter.millisToLocalDate(hireDateMillis).toString();
                long dismissDateMillis = employee.getDismissDateMillis();
                String dismissDate;
                if (dismissDateMillis == 0L) {
                    dismissDate = "-";
                } else {
                    dismissDate = DateConverter.millisToLocalDate(dismissDateMillis).toString();
                }
                fields.put(RequestParameter.HIRE_DATE, hireDate);
                fields.put(RequestParameter.DISMISS_DATE, dismissDate);
                fields.put(RequestParameter.EMPLOYEE_STATUS, employee.getStatus().toString());
                fields.put(RequestParameter.USER_ID, String.valueOf(employee.getUserId()));
                HttpSession session = request.getSession();
                session.setAttribute(SessionAttribute.EMPLOYEE_DATA_FIELDS, fields);
                session.setAttribute(SessionAttribute.EMPLOYEE_INFO_ID, employeeId);
                LOGGER.log(Level.DEBUG, "Employee data has been got");
                StringBuilder sb = new StringBuilder(UrlPattern.EMPLOYEE_INFO);
                page = sb.deleteCharAt(sb.length() - 1).append(employeeId).toString();
            } else {
                LOGGER.log(Level.DEBUG, "No employee found, redirected to employee list");
                page = UrlPattern.EMPLOYEE_LIST;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
