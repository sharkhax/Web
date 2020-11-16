package com.drobot.web.model.service.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.model.service.MapService;
import com.drobot.web.model.validator.PatientValidator;

import java.util.Map;

/**
 * MapService implementation with public validation methods.
 *
 * @author Vladislav Drobot
 */
public enum PatientMapService implements MapService {

    /**
     * Represents a singleton pattern realization.
     */
    INSTANCE;

    @Override
    public boolean isMapValid(Map<String, String> fields) {
        boolean result = checkName(fields) & checkSurname(fields)
                & checkGender(fields) & checkAge(fields);
        return result;
    }

    /**
     * Checks if patient's name is valid.
     *
     * @param fields Map object with entity's fields with RequestParameter constants as keys.
     * @return true if it is valid, false otherwise.
     */
    public boolean checkName(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(RequestParameter.PATIENT_NAME)) {
            String name = fields.get(RequestParameter.PATIENT_NAME);
            if (PatientValidator.isNameValid(name)) {
                result = true;
            } else {
                fields.put(RequestParameter.PATIENT_NAME, "");
                result = false;
            }
        }
        return result;
    }

    /**
     * Checks if patient's surname is valid.
     *
     * @param fields Map object with entity's fields with RequestParameter constants as keys.
     * @return true if it is valid, false otherwise.
     */
    public boolean checkSurname(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(RequestParameter.PATIENT_SURNAME)) {
            String surname = fields.get(RequestParameter.PATIENT_SURNAME);
            if (PatientValidator.isNameValid(surname)) {
                result = true;
            } else {
                fields.put(RequestParameter.PATIENT_SURNAME, "");
                result = false;
            }
        }
        return result;
    }

    /**
     * Checks if patient's gender is valid.
     *
     * @param fields Map object with entity's fields with RequestParameter constants as keys.
     * @return true if it is valid, false otherwise.
     */
    public boolean checkGender(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(RequestParameter.PATIENT_GENDER)) {
            String gender = fields.get(RequestParameter.PATIENT_GENDER);
            if (PatientValidator.isGenderValid(gender)) {
                result = true;
            } else {
                result = false;
                fields.put(RequestParameter.PATIENT_GENDER, "");
            }
        }
        return result;
    }

    /**
     * Checks if patient's age is valid.
     *
     * @param fields Map object with entity's fields with RequestParameter constants as keys.
     * @return true if it is valid, false otherwise.
     */
    public boolean checkAge(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(RequestParameter.PATIENT_AGE)) {
            String age = fields.get(RequestParameter.PATIENT_AGE);
            if (PatientValidator.isAgeValid(age)) {
                result = true;
            } else {
                result = false;
                fields.put(RequestParameter.PATIENT_AGE, "");
            }
        }
        return result;
    }

    /**
     * Checks if patient's diagnosis is valid.
     *
     * @param fields Map object with entity's fields with RequestParameter constants as keys.
     * @return true if it is valid, false otherwise.
     */
    public boolean checkDiagnosis(Map<String, String> fields) {
        boolean result = false;
        if (fields != null && fields.containsKey(RequestParameter.PATIENT_DIAGNOSIS)) {
            String diagnosis = fields.get(RequestParameter.PATIENT_DIAGNOSIS);
            if (PatientValidator.isDiagnosisValid(diagnosis)) {
                result = true;
            } else {
                result = false;
                fields.put(RequestParameter.PATIENT_DIAGNOSIS, "");
            }
        }
        return result;
    }
}
