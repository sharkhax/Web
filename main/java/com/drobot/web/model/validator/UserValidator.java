package com.drobot.web.model.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User validator class used to check users' fields values.
 *
 * @author Vladislav Drobot
 */
public class UserValidator {

    private static final Logger LOGGER = LogManager.getLogger(UserValidator.class);
    private static final String PASSWORD_REGEX = "[\\w~!-]{5,16}";
    private static final String LOGIN_REGEX1 = "[\\w-.]{5,16}";
    private static final String LOGIN_REGEX2 = "[a-zA-Z\\-._]+";

    private UserValidator() {
    }

    /**
     * Checks if user's password is valid.
     *
     * @param password String representation of non-encrypted password.
     * @return true if value is valid, false otherwise.
     */
    public static boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        boolean result = matcher.matches();
        String log = result ? "Password is valid" : "Password isn't valid";
        LOGGER.log(Level.DEBUG, log);
        return result;
    }

    /**
     * Checks if user's login is valid.
     *
     * @param login String representation of user's login.
     * @return true if value is valid, false otherwise.
     */
    public static boolean isLoginValid(String login) {
        boolean result = false;
        Pattern pattern = Pattern.compile(LOGIN_REGEX1);
        Matcher matcher = pattern.matcher(login);
        if (matcher.matches()) {
            pattern = Pattern.compile(LOGIN_REGEX2);
            matcher = pattern.matcher(login);
            result = matcher.find();
        }
        String log = result ? "Login is valid" : "Login isn't valid";
        LOGGER.log(Level.DEBUG, log);
        return result;
    }

    /**
     * Checks if user's email is valid.
     *
     * @param email String representation of user's email.
     * @return true if value is valid, false otherwise.
     */
    public static boolean isEmailValid(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        boolean result = validator.isValid(email);
        String log = result ? "Email is valid" : "Email isn't valid";
        LOGGER.log(Level.DEBUG, log);
        return result;
    }
}
