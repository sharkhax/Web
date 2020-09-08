package com.drobot.web.model.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    private static final Logger LOGGER = LogManager.getLogger(UserValidator.class);
    private static final String PASSWORD_REGEX = "[\\w_~!-]{5,16}";
    private static final String LOGIN_REGEX1 = "[\\w-.]{5,16}";
    private static final String LOGIN_REGEX2 = "[a-zA-Z\\-._]+";

    public boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        boolean result = matcher.matches();
        if (result) {
            LOGGER.log(Level.DEBUG, "Password is valid");
        } else {
            LOGGER.log(Level.DEBUG, "Password isn't valid");
        }
        return result;
    }

    public boolean isLoginValid(String login) {
        boolean result = false;
        Pattern pattern = Pattern.compile(LOGIN_REGEX1);
        Matcher matcher = pattern.matcher(login);
        if (matcher.matches()) {
            pattern = Pattern.compile(LOGIN_REGEX2);
            matcher = pattern.matcher(login);
            result = matcher.find();
        }
        if (result) {
            LOGGER.log(Level.DEBUG, "Login is valid");
        } else {
            LOGGER.log(Level.DEBUG, "Login isn't valid");
        }
        return result;
    }

    public boolean isEmailValid(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        boolean result = validator.isValid(email);
        if (result) {
            LOGGER.log(Level.DEBUG, "Email is valid");
        } else {
            LOGGER.log(Level.DEBUG, "Email isn't valid");
        }
        return result;
    }
}
