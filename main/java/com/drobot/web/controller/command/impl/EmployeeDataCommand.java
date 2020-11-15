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
            employeeId = Integer.parseInt(stringEmployeeId);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, "Incorrect employee id value, returning null");
            return null;
        }
        EmployeeService employeeService = EmployeeServiceImpl.INSTANCE;
        try {
            Optional<Employee> optionalEmployee = employeeService.findById(employeeId);
            if (optionalEmployee.isPresent()) {
                Employee employee = optionalEmployee.get();
                Map<String, String> fields = employeeService.packEmployeeIntoMap(employee);
                HttpSession session = request.getSession();
                session.setAttribute(SessionAttribute.EMPLOYEE_DATA_FIELDS, fields);
                session.setAttribute(SessionAttribute.EMPLOYEE_INFO_ID, employeeId);
                StringBuilder sb = new StringBuilder(UrlPattern.EMPLOYEE_INFO);
                page = sb.deleteCharAt(sb.length() - 1).append(employeeId).toString();
                LOGGER.log(Level.INFO, "Employee data has been got");
            } else {
                LOGGER.log(Level.INFO, "No employee found");
                page = null;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
