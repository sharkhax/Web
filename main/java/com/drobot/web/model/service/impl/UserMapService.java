package com.drobot.web.model.service.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.service.MapService;
import com.drobot.web.model.validator.UserValidator;

import java.util.Map;

/**
 * MapService implementation with public validation methods.
 *
 * @author Vladislav Drobot
 */
public enum UserMapService implements MapService {

    /**
     * Represents a singleton pattern realization.
     */
    INSTANCE;

    @Override
    public boolean isMapValid(Map<String, String> fields) {
        boolean result = checkLogin(fields) & checkPassword(fields)
                & checkEmail(fields) & checkRole(fields);
        return result;
    }

    /**
     * Checks if user's login is valid.
     *
     * @param fields Map object with entity's fields with RequestParameter constants as keys.
     * @return true if it is valid, false otherwise.
     */
    public boolean checkLogin(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(RequestParameter.LOGIN)) {
            result = true;
            String login = fields.get(RequestParameter.LOGIN);
            if (!UserValidator.isLoginValid(login)) {
                result = false;
                fields.put(RequestParameter.LOGIN, "");
            }
        }
        return result;
    }

    /**
     * Checks if user's password is valid.
     *
     * @param fields Map object with entity's fields with RequestParameter constants as keys.
     * @return true if it is valid, false otherwise.
     */
    public boolean checkPassword(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(RequestParameter.PASSWORD)) {
            result = true;
            String password = fields.get(RequestParameter.PASSWORD);
            if (!UserValidator.isPasswordValid(password)) {
                result = false;
                fields.put(RequestParameter.PASSWORD, "");
            }
        }
        return result;
    }

    /**
     * Checks if user's email is valid.
     *
     * @param fields Map object with entity's fields with RequestParameter constants as keys.
     * @return true if it is valid, false otherwise.
     */
    public boolean checkEmail(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(RequestParameter.EMAIL)) {
            result = true;
            String email = fields.get(RequestParameter.EMAIL);
            if (!UserValidator.isEmailValid(email)) {
                result = false;
                fields.put(RequestParameter.EMAIL, "");
            }
        }
        return result;
    }

    /**
     * Checks if user's role is valid.
     *
     * @param fields Map object with entity's fields with RequestParameter constants as keys.
     * @return true if it is valid, false otherwise.
     */
    public boolean checkRole(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(RequestParameter.EMPLOYEE_POSITION)) {
            result = true;
            String position = fields.get(RequestParameter.EMPLOYEE_POSITION);
            if (!position.equalsIgnoreCase(RequestParameter.DOCTOR)
                    && !position.equalsIgnoreCase(RequestParameter.ASSISTANT)) {
                result = false;
                fields.put(RequestParameter.EMPLOYEE_POSITION, "");
            }
        }
        return result;
    }

    /**
     * Checks if user's status is valid.
     *
     * @param fields Map object with entity's fields with RequestParameter constants as keys.
     * @return true if it is valid, false otherwise.
     */
    public boolean checkStatus(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(RequestParameter.USER_STATUS)) {
            String status = fields.get(RequestParameter.USER_STATUS);
            try {
                Entity.Status.valueOf(status);
                result = true;
            } catch (IllegalArgumentException e) {
                result = false;
            }
        }
        return result;
    }
}
