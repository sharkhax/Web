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

@CommandAccessLevel(AccessType.ADMIN)
public class RestoreEmployeeCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(RestoreEmployeeCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        int employeeId = Integer.parseInt(request.getParameter(RequestParameter.EMPLOYEE_ID));
        EmployeeService service = EmployeeServiceImpl.INSTANCE;
        try {
            if (service.restoreEmployee(employeeId)) {
                LOGGER.log(Level.DEBUG, "Employee has been restored successfully");
                HttpSession session = request.getSession();
                Map<String, String> employeeFields =
                        (Map<String, String>) session.getAttribute(SessionAttribute.EMPLOYEE_DATA_FIELDS);
                employeeFields.replace(RequestParameter.EMPLOYEE_STATUS, Entity.Status.ACTIVE.toString());
                session.setAttribute(SessionAttribute.EMPLOYEE_DATA_FIELDS, employeeFields);
            } else {
                LOGGER.log(Level.ERROR, "Employee has not restored fired");
            }
            StringBuilder sb = new StringBuilder(UrlPattern.EMPLOYEE_INFO);
            page = sb.deleteCharAt(sb.length() - 1).append(employeeId).toString();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}