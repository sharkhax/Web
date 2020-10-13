package com.drobot.web.model.service;

import com.drobot.web.model.dao.ColumnName;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.validator.EmployeeValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public enum EmployeeMapService {

    INSTANCE;

    private static final Logger LOGGER = LogManager.getLogger(EmployeeMapService.class);

    public boolean checkName(Map<String, String> fields) {
        return checkNameOrSurname(fields, ColumnName.EMPLOYEE_NAME);
    }

    public boolean checkSurname(Map<String, String> fields) {
        return checkNameOrSurname(fields, ColumnName.EMPLOYEE_SURNAME);
    }
    
    public boolean checkAge(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(ColumnName.EMPLOYEE_AGE)) {
            result = true;
            String stringAge = fields.get(ColumnName.EMPLOYEE_AGE);
            EmployeeValidator employeeValidator = new EmployeeValidator();
            if (!employeeValidator.isAgeValid(stringAge)) {
                result = false;
                fields.put(ColumnName.EMPLOYEE_AGE, "");
            }
        }
        return result;
    }
    
    public boolean checkGender(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(ColumnName.EMPLOYEE_GENDER)) {
            result = true;
            String stringGender = fields.get(ColumnName.EMPLOYEE_GENDER);
            EmployeeValidator employeeValidator = new EmployeeValidator();
            if (!employeeValidator.isGenderValid(stringGender)) {
                result = false;
                fields.put(ColumnName.EMPLOYEE_GENDER, "");
            }
        }
        return result;
    }
    
    public boolean checkPosition(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(ColumnName.EMPLOYEE_POSITION)) {
            result = true;
            String stringPosition = fields.get(ColumnName.EMPLOYEE_POSITION);
            try {
                Employee.Position.valueOf(stringPosition);
            } catch (IllegalArgumentException e) {
                result = false;
                fields.put(ColumnName.EMPLOYEE_POSITION, "");
            }
        }
        return result;
    }
    
    public boolean checkHireDate(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(ColumnName.EMPLOYEE_HIRE_DATE)) {
            result = true;
            String stringHireDate = fields.get(ColumnName.EMPLOYEE_HIRE_DATE);
            EmployeeValidator employeeValidator = new EmployeeValidator();
            if (!employeeValidator.isHireDateValid(stringHireDate)) {
                result = false;
                fields.put(ColumnName.EMPLOYEE_HIRE_DATE, "");
            }
        }
        return result;
    }

    public boolean checkDismissDate(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(ColumnName.EMPLOYEE_DISMISS_DATE)
                && fields.containsKey(ColumnName.EMPLOYEE_HIRE_DATE)) {
            result = true;
            String stringDismissDate = fields.get(ColumnName.EMPLOYEE_DISMISS_DATE);
            String stringHireDate = fields.get(ColumnName.EMPLOYEE_HIRE_DATE);
            EmployeeValidator employeeValidator = new EmployeeValidator();
            if (employeeValidator.isHireDateValid(stringHireDate)) {
                long hireDateMillis = Long.parseLong(stringHireDate);
                if (!employeeValidator.isDismissDateValid(stringDismissDate, hireDateMillis)) {
                    result = false;
                    fields.put(ColumnName.EMPLOYEE_DISMISS_DATE, "");
                }
            } else {
                result = false;
                LOGGER.log(Level.DEBUG, "Can't check dismiss date: hire date is null or invalid");
            }
        }
        return result;
    }

    public boolean checkAccountId(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(ColumnName.EMPLOYEE_ACCOUNT_ID)) {
            result = true;
            String stringUserAccountId = fields.get(ColumnName.EMPLOYEE_ACCOUNT_ID);
            try {
                Integer.parseInt(stringUserAccountId);
            } catch (NumberFormatException e) {
                result = false;
                fields.put(ColumnName.EMPLOYEE_ACCOUNT_ID, "");
            }
        }
        return result;
    }

    public boolean checkStatus(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(ColumnName.EMPLOYEE_STATUS)) {
            result = true;
            String stringStatus = fields.get(ColumnName.EMPLOYEE_STATUS);
            try {
                int statusId = Integer.parseInt(stringStatus);
                Entity.Status.defineStatus(statusId);
            } catch (IllegalArgumentException e) {
                result = false;
                fields.put(ColumnName.EMPLOYEE_STATUS, "");
            }
        }
        return result;
    }
    
    private boolean checkNameOrSurname(Map<String, String> fields, String fieldName) {
        boolean result = false;
        if (fields != null && fields.containsKey(fieldName)) {
            result = true;
            String name = fields.get(fieldName);
            EmployeeValidator employeeValidator = new EmployeeValidator();
            if (!employeeValidator.isNameValid(name)) {
                result = false;
                fields.put(fieldName, "");
            }
        }
        return result;
    }
}
