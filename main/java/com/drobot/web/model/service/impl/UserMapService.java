package com.drobot.web.model.service.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.model.service.MapService;
import com.drobot.web.model.validator.UserValidator;

import java.util.Map;

public enum UserMapService implements MapService {

    INSTANCE;

    @Override
    public boolean isMapValid(Map<String, String> fields) {
        boolean result = checkLogin(fields) & checkPassword(fields)
                & checkEmail(fields) & checkRole(fields);
        return result;
    }

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

    public boolean checkRole(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(RequestParameter.POSITION)) {
            result = true;
            String position = fields.get(RequestParameter.POSITION);
            if (!position.equalsIgnoreCase(RequestParameter.DOCTOR)
                    && !position.equalsIgnoreCase(RequestParameter.ASSISTANT)) {
                result = false;
                fields.put(RequestParameter.POSITION, "");
            }
        }
        return result;
    }
}
