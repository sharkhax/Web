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
import com.drobot.web.model.service.EmployeeService;
import com.drobot.web.model.service.impl.EmployeeServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Action command for returning an employee from vacation.
 *
 * @author Vladislav Drobot
 */
@CommandAccessLevel(AccessType.ADMIN)
public class ReturnFromVacationCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(FireEmployeeCommand.class);

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
        try {
            EmployeeService employeeService = EmployeeServiceImpl.INSTANCE;
            if (employeeService.returnFromVacation(employeeId)) {
                HttpSession session = request.getSession();
                Map<String, String> employeeFields =
                        (Map<String, String>) session.getAttribute(SessionAttribute.EMPLOYEE_DATA_FIELDS);
                employeeFields.replace(RequestParameter.EMPLOYEE_STATUS, Entity.Status.ACTIVE.toString());
                session.setAttribute(SessionAttribute.EMPLOYEE_DATA_FIELDS, employeeFields);
                LOGGER.log(Level.INFO, "Employee has been returned from vacation");
            } else {
                LOGGER.log(Level.ERROR, "Employee hasn't been returned from vacation");
            }
            StringBuilder sb = new StringBuilder(UrlPattern.EMPLOYEE_INFO);
            page = sb.deleteCharAt(sb.length() - 1).append(employeeId).toString();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
