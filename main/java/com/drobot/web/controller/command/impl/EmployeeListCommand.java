package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.UrlPattern;
import com.drobot.web.controller.command.AccessType;
import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandAccessLevel;
import com.drobot.web.exception.CommandException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.dao.ColumnName;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.model.service.EmployeeService;
import com.drobot.web.model.service.impl.EmployeeServiceImpl;
import com.drobot.web.tag.EmployeeListTag;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Action command for loading employee list.
 *
 * @author Vladislav Drobot
 */
@CommandAccessLevel(AccessType.ADMIN)
public class EmployeeListCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(EmployeeListCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page = UrlPattern.EMPLOYEE_LIST;
        HttpSession session = request.getSession();
        Integer currentPage = (Integer) session.getAttribute(SessionAttribute.EMPLOYEE_LIST_CURRENT_PAGE);
        String requestedPage = request.getParameter(RequestParameter.LIST_PAGE);
        if (requestedPage == null && currentPage == null) {
            currentPage = 1;
        } else if (requestedPage != null) {
            currentPage = Integer.parseInt(requestedPage);
        }
        session.setAttribute(SessionAttribute.EMPLOYEE_LIST_CURRENT_PAGE, currentPage);
        int start = (currentPage - 1) * EmployeeListTag.ROWS_NUMBER;
        int end = EmployeeListTag.ROWS_NUMBER + start;
        String sortBy = (String) session.getAttribute(SessionAttribute.EMPLOYEE_LIST_SORT_BY);
        String requestedSortBy = request.getParameter(RequestParameter.SORT_BY);
        Boolean reverseSorting = (Boolean) session.getAttribute(SessionAttribute.EMPLOYEE_LIST_REVERSE_SORTING);
        if (reverseSorting == null) {
            reverseSorting = false;
        }
        if (requestedSortBy == null && sortBy == null) {
            sortBy = ColumnName.EMPLOYEE_ID;
        } else if (requestedSortBy != null) {
            if (requestedSortBy.equals(sortBy)) {
                reverseSorting = !reverseSorting;
            } else {
                reverseSorting = false;
            }
            sortBy = requestedSortBy;
        }
        session.setAttribute(SessionAttribute.EMPLOYEE_LIST_SORT_BY, sortBy);
        session.setAttribute(SessionAttribute.EMPLOYEE_LIST_REVERSE_SORTING, reverseSorting);
        EmployeeService employeeService = EmployeeServiceImpl.INSTANCE;
        try {
            List<Employee> employeeList = employeeService.findAll(start, end, sortBy, reverseSorting);
            session.setAttribute(SessionAttribute.EMPLOYEE_LIST, employeeList);
            int employeesNumber = employeeService.count();
            session.setAttribute(SessionAttribute.EMPLOYEES_NUMBER, employeesNumber);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        LOGGER.log(Level.INFO, "Employee list has been got");
        return page;
    }
}
