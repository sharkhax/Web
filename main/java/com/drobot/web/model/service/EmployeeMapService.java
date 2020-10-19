package com.drobot.web.model.service;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.model.util.DateConverter;
import com.drobot.web.model.validator.EmployeeValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

public enum EmployeeMapService {

    INSTANCE;

    private final Logger LOGGER = LogManager.getLogger(EmployeeMapService.class);

    public boolean checkName(Map<String, String> fields) {
        return checkNameOrSurname(fields, RequestParameter.NAME);
    }

    public boolean checkSurname(Map<String, String> fields) {
        return checkNameOrSurname(fields, RequestParameter.SURNAME);
    }
    
    public boolean checkAge(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(RequestParameter.AGE)) {
            result = true;
            String stringAge = fields.get(RequestParameter.AGE);
            if (!EmployeeValidator.isAgeValid(stringAge)) {
                result = false;
                fields.put(RequestParameter.AGE, "");
            }
        }
        return result;
    }
    
    public boolean checkGender(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(RequestParameter.GENDER)) {
            result = true;
            String stringGender = fields.get(RequestParameter.GENDER);
            if (!EmployeeValidator.isGenderValid(stringGender)) {
                result = false;
                fields.put(RequestParameter.GENDER, "");
            }
        }
        return result;
    }
    
    public boolean checkPosition(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(RequestParameter.POSITION)) {
            result = true;
            String stringPosition = fields.get(RequestParameter.POSITION);
            try {
                Employee.Position.valueOf(stringPosition.toUpperCase());
            } catch (IllegalArgumentException e) {
                result = false;
                fields.put(RequestParameter.POSITION, "");
                LOGGER.log(Level.DEBUG, "Position " + stringPosition + " is invalid");
            }
        }
        return result;
    }
    
    public boolean checkHireDate(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(RequestParameter.HIRE_DATE)) {
            result = true;
            String stringHireDate = fields.get(RequestParameter.HIRE_DATE);
            try {
                long hireDateDays = LocalDate.parse(stringHireDate).toEpochDay();
                long hireDateMillis = DateConverter.daysToMillis(hireDateDays);
                if (!EmployeeValidator.isHireDateValid(hireDateMillis)) {
                    result = false;
                    fields.put(RequestParameter.HIRE_DATE, "");
                }
            } catch (DateTimeParseException e) {
                LOGGER.log(Level.DEBUG, "Hire date is invalid");
                result = false;
            }
        }
        return result;
    }

    public boolean checkDismissDate(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(RequestParameter.DISMISS_DATE)
                && fields.containsKey(RequestParameter.HIRE_DATE)) {
            result = true;
            String stringDismissDate = fields.get(RequestParameter.DISMISS_DATE);
            String stringHireDate = fields.get(RequestParameter.HIRE_DATE);
            try {
                long hireDateDays = LocalDate.parse(stringHireDate).toEpochDay();
                long hireDateMillis = DateConverter.daysToMillis(hireDateDays);
                if (EmployeeValidator.isHireDateValid(stringHireDate)) {
                    long dismissDateDays = LocalDate.parse(stringDismissDate).toEpochDay();
                    long dismissDateMillis = DateConverter.daysToMillis(dismissDateDays);
                    if (!EmployeeValidator.isDismissDateValid(dismissDateMillis, hireDateMillis)) {
                        result = false;
                        fields.put(RequestParameter.DISMISS_DATE, "");
                    }
                } else {
                    result = false;
                    LOGGER.log(Level.DEBUG, "Can't check dismiss date: hire date is null or invalid");
                }
            } catch (DateTimeParseException e) {
                LOGGER.log(Level.DEBUG, "Hire or dismiss dates is invalid");
                result = false;
            }
        }
        return result;
    }

    // TODO: 18.10.2020
    /*public boolean checkStatus(Map<String, String> fields) {
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
    }*/
    
    private boolean checkNameOrSurname(Map<String, String> fields, String fieldName) {
        boolean result = false;
        if (fields != null && fields.containsKey(fieldName)) {
            result = true;
            String name = fields.get(fieldName);
            if (!EmployeeValidator.isNameValid(name)) {
                result = false;
                fields.put(fieldName, "");
            }
        }
        return result;
    }
}
