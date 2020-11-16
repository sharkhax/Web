package com.drobot.web.model.service;

import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.Patient;

import java.util.Map;

/**
 * Interface provides actions on patient.
 *
 * @author Vladislav Drobot
 */
public interface PatientService extends BaseService<Patient> {

    /**
     * Adds a patient into a datasource, if given fields are valid.
     *
     * @param fields         Map object with patient's fields with RequestParameter's constants as keys inside.
     * @param existingFields empty Map object for existing patient's fields. They will be put there.
     * @return true if patient has been added successfully, false otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean add(Map<String, String> fields, Map<String, String> existingFields) throws ServiceException;

    /**
     * Discharges the patient with a given ID, if it is valid.
     *
     * @param patientId patient's ID int value.
     * @return true if patient has been discharged successfully, false otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean discharge(int patientId) throws ServiceException;
}
