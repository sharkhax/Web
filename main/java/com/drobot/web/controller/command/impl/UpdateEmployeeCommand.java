package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.command.AccessType;
import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandAccessLevel;
import com.drobot.web.controller.RequestParameter;
import com.drobot.web.exception.CommandException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.dao.ColumnName;
import com.drobot.web.model.service.EmployeeService;
import com.drobot.web.model.service.impl.EmployeeServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@CommandAccessLevel({AccessType.ADMIN, AccessType.DOCTOR, AccessType.ASSISTANT})
public class UpdateEmployeeCommand implements ActionCommand {

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page = null;
        Map<String, String> fields = new HashMap<>();
        String stringEmployeeId = null;
        int employeeId = 0;// TODO: 25.09.2020
        String name = request.getParameter(RequestParameter.EMPLOYEE_NAME);
        fields.put(ColumnName.EMPLOYEE_NAME, name);
        String surname = request.getParameter(RequestParameter.EMPLOYEE_SURNAME);
        fields.put(ColumnName.EMPLOYEE_SURNAME, surname);
        String age = request.getParameter(RequestParameter.EMPLOYEE_AGE);
        fields.put(ColumnName.EMPLOYEE_AGE, age);
        String gender = request.getParameter(RequestParameter.EMPLOYEE_GENDER);
        fields.put(ColumnName.EMPLOYEE_GENDER, gender);
        String position = request.getParameter(RequestParameter.EMPLOYEE_POSITION);
        fields.put(ColumnName.EMPLOYEE_POSITION, position);
        String hireDate = request.getParameter(RequestParameter.EMPLOYEE_HIRE_DATE);
        fields.put(ColumnName.EMPLOYEE_HIRE_DATE, hireDate);
        String accountId = request.getParameter(RequestParameter.EMPLOYEE_ACCOUNT_ID);
        fields.put(ColumnName.EMPLOYEE_ACCOUNT_ID, accountId);
        EmployeeService employeeService = new EmployeeServiceImpl();
        try {
            if (employeeService.update(employeeId, fields)) {
                // TODO: 25.09.2020 success
            } else {
                // TODO: 25.09.2020 same page with fields
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
