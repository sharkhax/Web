package com.drobot.web.model.service.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.exception.DaoException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.creator.Creator;
import com.drobot.web.model.creator.impl.PatientCreator;
import com.drobot.web.model.dao.ColumnName;
import com.drobot.web.model.dao.PatientDao;
import com.drobot.web.model.dao.impl.PatientDaoImpl;
import com.drobot.web.model.entity.Patient;
import com.drobot.web.model.service.PatientService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum PatientServiceImpl implements PatientService {

    INSTANCE;

    private final Logger LOGGER = LogManager.getLogger(PatientServiceImpl.class);

    @Override
    public boolean add(Map<String, String> fields, Map<String, String> existingFields) throws ServiceException {
        boolean result = false;
        Creator<Patient> patientCreator = new PatientCreator();
        Optional<Patient> optionalPatient = patientCreator.create(fields);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            PatientDao patientDao = PatientDaoImpl.INSTANCE;
            String name = patient.getName();
            String surname = patient.getSurname();
            boolean noMatches = true;
            try {
                if (patientDao.exists(name, surname)) {
                    existingFields.put(RequestParameter.PATIENT_NAME, name);
                    existingFields.put(RequestParameter.PATIENT_SURNAME, surname);
                    noMatches = false;
                }
                if (noMatches) {
                    result = patientDao.add(patient);
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return result;
    }

    @Override
    public List<Patient> findAll(int start, int end, String sortBy) throws ServiceException {
        List<Patient> result;
        try {
            if (start >= 0 && end > start) {
                if (checkSortingTag(sortBy)) {
                    PatientDao patientDao = PatientDaoImpl.INSTANCE;
                    result = patientDao.findAll(start, end, sortBy);
                } else {
                    result = List.of();
                    LOGGER.log(Level.ERROR, "Invalid sorting tag");
                }
            } else {
                result = List.of();
                LOGGER.log(Level.ERROR, "Invalid start or end values");
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public int count() throws ServiceException {
        int result;
        try {
            PatientDao userDao = PatientDaoImpl.INSTANCE;
            result = userDao.count();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }


    private boolean checkSortingTag(String sortBy) {
        return sortBy.equals(ColumnName.PATIENT_ID)
                || sortBy.equals(ColumnName.PATIENT_NAME)
                || sortBy.equals(ColumnName.PATIENT_SURNAME)
                || sortBy.equals(ColumnName.PATIENT_AGE)
                || sortBy.equals(ColumnName.PATIENT_GENDER)
                || sortBy.equals(ColumnName.PATIENT_DIAGNOSIS)
                || sortBy.equals(ColumnName.PATIENT_STATUS);
    }
}
