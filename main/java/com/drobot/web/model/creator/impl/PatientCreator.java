package com.drobot.web.model.creator.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.model.creator.Creator;
import com.drobot.web.model.entity.Patient;
import com.drobot.web.model.service.impl.PatientMapService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

public class PatientCreator implements Creator<Patient> {

    private static final Logger LOGGER = LogManager.getLogger(PatientCreator.class);

    @Override
    public Optional<Patient> create(Map<String, String> fields) {
        Optional<Patient> result;
        PatientMapService mapService = PatientMapService.INSTANCE;
        if (mapService.isMapValid(fields)) {
            String name = fields.get(RequestParameter.PATIENT_NAME);
            String surname = fields.get(RequestParameter.PATIENT_SURNAME);
            int age = Integer.parseInt(fields.get(RequestParameter.PATIENT_AGE));
            char gender = fields.get(RequestParameter.PATIENT_GENDER).charAt(0);
            String diagnosis = fields.get(RequestParameter.DIAGNOSIS);
            Patient patient = new Patient(name, surname, age, gender, diagnosis);
            result = Optional.of(patient);
        } else {
            LOGGER.log(Level.DEBUG, "Some fields are absent or invalid");
            result = Optional.empty();
        }
        return result;
    }
}
