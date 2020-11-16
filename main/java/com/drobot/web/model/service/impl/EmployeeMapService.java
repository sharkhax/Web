package com.drobot.web.model.service.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.service.MapService;
import com.drobot.web.util.DateConverter;
import com.drobot.web.model.validator.EmployeeValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

/**
 * MapService implementation with public validation methods.
 *
 * @author Vladislav Drobot
 */
public enum EmployeeMapService implements MapService {

    /**
     * Represents a singleton pattern realization.
     */
    INSTANCE;

    private final Logger LOGGER = LogManager.getLogger(EmployeeMapService.class);

    @Override
    public boolean isMapValid(Map<String, String> fields) {
        boolean result = checkName(fields) & checkSurname(fields)
                & checkAge(fields) & checkGender(fields)
                & checkHireDate(fields) & checkPosition(fields);
        return result;
    }

    /**
     * Checks if employee's name is valid.
     *
     * @param fields Map object with entity's fields with RequestParameter constants as keys.
     * @return true if it is valid, false otherwise.
     */
    public boolean checkName(Map<String, String> fields) {
        return checkNameOrSurname(fields, RequestParameter.EMPLOYEE_NAME);
    }

    /**
     * Checks if employee's surname is valid.
     *
     * @param fields Map object with entity's fields with RequestParameter constants as keys.
     * @return true if it is valid, false otherwise.
     */
    public boolean checkSurname(Map<String, String> fields) {
        return checkNameOrSurname(fields, RequestParameter.EMPLOYEE_SURNAME);
    }

    /**
     * Checks if employee's age is valid.
     *
     * @param fields Map object with entity's fields with RequestParameter constants as keys.
     * @return true if it is valid, false otherwise.
     */
    public boolean checkAge(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(RequestParameter.EMPLOYEE_AGE)) {
            result = true;
            String stringAge = fields.get(RequestParameter.EMPLOYEE_AGE);
            if (!EmployeeValidator.isAgeValid(stringAge)) {
                result = false;
                fields.put(RequestParameter.EMPLOYEE_AGE, "");
            }
        }
        return result;
    }

    /**
     * Checks if employee's gender is valid.
     *
     * @param fields Map object with entity's fields with RequestParameter constants as keys.
     * @return true if it is valid, false otherwise.
     */
    public boolean checkGender(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(RequestParameter.EMPLOYEE_GENDER)) {
            result = true;
            String stringGender = fields.get(RequestParameter.EMPLOYEE_GENDER);
            if (!EmployeeValidator.isGenderValid(stringGender)) {
                result = false;
                fields.put(RequestParameter.EMPLOYEE_GENDER, "");
            }
        }
        return result;
    }

    /**
     * Checks if employee's position is valid.
     *
     * @param fields Map object with entity's fields with RequestParameter constants as keys.
     * @return true if it is valid, false otherwise.
     */
    public boolean checkPosition(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(RequestParameter.EMPLOYEE_POSITION)) {
            result = true;
            String stringPosition = fields.get(RequestParameter.EMPLOYEE_POSITION);
            try {
                Employee.Position.valueOf(stringPosition.toUpperCase());
            } catch (IllegalArgumentException e) {
                result = false;
                fields.put(RequestParameter.EMPLOYEE_POSITION, "");
                LOGGER.log(Level.DEBUG, "Position " + stringPosition + " is invalid");
            }
        }
        return result;
    }

    /**
     * Checks if employee's hire date is valid.
     *
     * @param fields Map object with entity's fields with RequestParameter constants as keys.
     * @return true if it is valid, false otherwise.
     */
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

    /**
     * Checks if employee's dismiss date is valid.
     *
     * @param fields Map object with entity's fields with RequestParameter constants as keys.
     * @return true if it is valid, false otherwise.
     */
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

    /**
     * Checks if employee's status is valid.
     *
     * @param fields Map object with entity's fields with RequestParameter constants as keys.
     * @return true if it is valid, false otherwise.
     */
    public boolean checkStatus(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(RequestParameter.EMPLOYEE_STATUS)) {
            result = true;
            String stringStatus = fields.get(RequestParameter.EMPLOYEE_STATUS);
            try {
                Entity.Status.valueOf(stringStatus);
            } catch (IllegalArgumentException e) {
                result = false;
                fields.put(RequestParameter.EMPLOYEE_STATUS, "");
            }
        }
        return result;
    }

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
