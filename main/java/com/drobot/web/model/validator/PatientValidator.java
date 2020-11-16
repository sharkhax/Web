package com.drobot.web.model.validator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Patient validator class used to check patients' fields values.
 *
 * @author Vladislav Drobot
 */
public class PatientValidator {

    private static final Logger LOGGER = LogManager.getLogger(PatientValidator.class);
    private static final String NAME_REGEX = "[a-zA-Z-']{2,45}";
    private static final String DIAGNOSIS_REGEX = "[a-zA-Z-' ]{2,45}";
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 99;
    private static final char[] AVAILABLE_GENDERS = {'M', 'F'};

    private PatientValidator() {
    }

    /**
     * Checks if patient's name is valid.
     *
     * @param name String representation of patient's name.
     * @return true if value is valid, false otherwise.
     */
    public static boolean isNameValid(String name) {
        boolean result = false;
        if (name != null) {
            Pattern pattern = Pattern.compile(NAME_REGEX);
            Matcher matcher = pattern.matcher(name);
            result = matcher.matches();
            String log = result ? "Name is valid" : "Name is not valid";
            LOGGER.log(Level.DEBUG, log);
        } else {
            LOGGER.log(Level.DEBUG, "Name is not valid, it's null");
        }
        return result;
    }

    /**
     * Checks if patient's age is valid.
     *
     * @param stringAge String representation of patient's age.
     * @return true if value is valid, false otherwise.
     */
    public static boolean isAgeValid(String stringAge) {
        boolean result = false;
        try {
            int age = Integer.parseInt(stringAge);
            result = isAgeValid(age);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.DEBUG, "Age is not valid");
        }
        return result;
    }

    /**
     * Checks if patient's age is valid.
     *
     * @param age int values of patient's age.
     * @return true if value is valid, false otherwise.
     */
    public static boolean isAgeValid(int age) {
        boolean result = age >= MIN_AGE && age <= MAX_AGE;
        String log = result ? "Age is valid" : "Age is not valid";
        LOGGER.log(Level.DEBUG, log);
        return result;
    }

    /**
     * Checks if patient's gender is valid.
     *
     * @param gender char values of patient's gender.
     * @return true if value is valid, false otherwise.
     */
    public static boolean isGenderValid(char gender) {
        boolean result = false;
        gender = Character.toUpperCase(gender);
        for (char current : AVAILABLE_GENDERS) {
            if (gender == current) {
                result = true;
                break;
            }
        }
        String log = result ? "Gender is valid" : "Gender is not valid";
        LOGGER.log(Level.DEBUG, log);
        return result;
    }

    /**
     * Checks if patient's gender is valid.
     *
     * @param gender String representation of patient's gender.
     * @return true if value is valid, false otherwise.
     */
    public static boolean isGenderValid(String gender) {
        boolean result = false;
        if (gender.length() == 1) {
            result = isGenderValid(gender.charAt(0));
        } else {
            LOGGER.log(Level.DEBUG, "Gender is not valid");
        }
        return result;
    }

    /**
     * Checks if patient's diagnosis is valid.
     *
     * @param diagnosis String representation of patient's diagnosis.
     * @return true if value is valid, false otherwise.
     */
    public static boolean isDiagnosisValid(String diagnosis) {
        boolean result = false;
        if (diagnosis != null) {
            Pattern pattern = Pattern.compile(DIAGNOSIS_REGEX);
            Matcher matcher = pattern.matcher(diagnosis);
            result = matcher.matches();
            String log = result ? "Diagnosis is valid" : "Diagnosis is not valid";
            LOGGER.log(Level.DEBUG, log);
        } else {
            LOGGER.log(Level.DEBUG, "Diagnosis is not valid, it's null");
        }
        return result;
    }
}
