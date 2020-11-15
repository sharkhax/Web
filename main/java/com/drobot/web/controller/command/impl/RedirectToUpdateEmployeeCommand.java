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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

@CommandAccessLevel(AccessType.ADMIN)
public class RedirectToUpdateEmployeeCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(RedirectToUpdateEmployeeCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        String stringEmployeeId = request.getParameter(RequestParameter.EMPLOYEE_ID);
        int employeeId;
        try {
            employeeId = Integer.parseInt(stringEmployeeId);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, "Incorrect employee ID value");
            return null;
        }
        try {
            EmployeeService employeeService = EmployeeServiceImpl.INSTANCE;
            if (employeeService.exists(employeeId)) {
                HttpSession session = request.getSession();
                Integer employeeInfoId = (Integer) session.getAttribute(SessionAttribute.EMPLOYEE_INFO_ID);
                if (employeeInfoId == null || employeeId != employeeInfoId) {
                    Optional<Employee> optional = employeeService.findById(employeeId);
                    Employee employee = optional.orElseThrow();
                    Map<String, String> fields = employeeService.packEmployeeIntoMap(employee);
                    session.setAttribute(SessionAttribute.EMPLOYEE_DATA_FIELDS, fields);
                    session.setAttribute(SessionAttribute.EMPLOYEE_INFO_ID, employeeId);
                    LOGGER.log(Level.DEBUG, "Employee data fields have been refilled");
                }
                StringBuilder sb = new StringBuilder(UrlPattern.UPDATING_EMPLOYEE);
                int indexOfAsterisk = sb.indexOf(UrlPattern.ASTERISK);
                sb.replace(indexOfAsterisk, indexOfAsterisk + 1, stringEmployeeId);
                page = sb.toString();
                LOGGER.log(Level.DEBUG, "Flag \"EMPLOYEE_EXISTS\" has been set to true");
                session.setAttribute(SessionAttribute.EMPLOYEE_EXISTS, true);
                LOGGER.log(Level.INFO, "Redirecting to updating employee page");
            } else {
                LOGGER.log(Level.DEBUG, "Employee with id " + stringEmployeeId + " doesn't exist, returning null");
                page = null;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
