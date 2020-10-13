package com.drobot.web.model.validator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeValidator {

    private static final Logger LOGGER = LogManager.getLogger(EmployeeValidator.class);
    private static final String NAME_REGEX = "";
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 99;
    private static final char[] AVAILABLE_GENDERS = {'M', 'F'};
    private static final String MIN_HIRE_DATE = "2020-07-01";
    private static final String MIN_DISMISS_DATE = "2020-07-02";

    public boolean isNameValid(String name) {
        boolean result = false;
        if (name != null) {
            Pattern pattern = Pattern.compile(NAME_REGEX);
            Matcher matcher = pattern.matcher(name);
            result = matcher.matches();
            String log = result ? "Name " + name + " is valid" : "Name " + name + " is not valid";
            LOGGER.log(Level.DEBUG, log);
        } else {
            LOGGER.log(Level.DEBUG, "Name is not valid, it's null");
        }
        return result;
    }

    public boolean isAgeValid(int age) {
        boolean result = age >= MIN_AGE && age <= MAX_AGE;
        String log = result ? "Age " + age + " is valid" : "Age " + age + " is not valid";
        LOGGER.log(Level.DEBUG, log);
        return result;
    }

    public boolean isAgeValid(String stringAge) {
        boolean result = false;
        try {
            int age = Integer.parseInt(stringAge);
            result = isAgeValid(age);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.DEBUG, "String age is not valid");
        }
        return result;
    }

    public boolean isGenderValid(char gender) {
        gender = Character.toUpperCase(gender);
        boolean result = false;
        for (char actualGender : AVAILABLE_GENDERS) {
            if (gender == actualGender) {
                result = true;
                break;
            }
        }
        String log = result ? "Gender is valid" : "Gender is not valid";
        LOGGER.log(Level.DEBUG, log);
        return result;
    }

    public boolean isGenderValid(String stringGender) {
        boolean result = false;
        if (stringGender != null && stringGender.length() == 1) {
            result = isGenderValid(stringGender.charAt(0));
        } else {
            LOGGER.log(Level.DEBUG, "Gender is not valid");
        }
        return result;
    }

    public boolean isHireDateValid(long hireDateMillis) {
        boolean result = isDateValid(hireDateMillis, MIN_HIRE_DATE);
        String log = result ? "Hire date is valid" : "Hire date is not valid";
        LOGGER.log(Level.DEBUG, log);
        return result;
    }

    public boolean isHireDateValid(String stringHireDate) {
        boolean result = false;
        try {
            long hireDateMillis = Integer.parseInt(stringHireDate);
            result = isHireDateValid(hireDateMillis);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.DEBUG, "Hire date is not valid");
        }
        return result;
    }

    public boolean isDismissDateValid(long dismissDateMillis, long hireDateMillis) {
        boolean result = isDateValid(dismissDateMillis, MIN_DISMISS_DATE)
                && dismissDateMillis > hireDateMillis;
        String log = result ? "Dismiss date is valid" : "Dismiss date is not valid";
        LOGGER.log(Level.DEBUG, log);
        return result;
    }

    public boolean isDismissDateValid(String stringDismissDate, long hireDateMillis) {
        boolean result = false;
        try {
            long dismissDateMillis = Integer.parseInt(stringDismissDate);
            result = isDismissDateValid(dismissDateMillis, hireDateMillis);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.DEBUG, "Dismiss date is not valid");
        }
        return result;
    }

    private boolean isDateValid(long dateMillis, String minDateString) {
        boolean result;
        LocalDate minDate = LocalDate.parse(minDateString);
        long minDateMillis = minDate.getLong(ChronoField.INSTANT_SECONDS);
        result = dateMillis > minDateMillis
                && dateMillis < Instant.now().getLong(ChronoField.INSTANT_SECONDS);
        return result;
    }
}
