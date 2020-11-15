package com.drobot.web.model.service.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.exception.DaoException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.creator.Creator;
import com.drobot.web.model.creator.impl.PatientCreator;
import com.drobot.web.model.dao.ColumnName;
import com.drobot.web.model.dao.PatientDao;
import com.drobot.web.model.dao.impl.PatientDaoImpl;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.entity.Patient;
import com.drobot.web.model.service.PatientService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum PatientServiceImpl implements PatientService {

    INSTANCE;

    private final Logger LOGGER = LogManager.getLogger(PatientServiceImpl.class);
    private final PatientDao patientDao = PatientDaoImpl.INSTANCE;

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
    public List<Patient> findAll(int start, int end, String sortBy, boolean reverse) throws ServiceException {
        List<Patient> result;
        try {
            if (start >= 0 && end > start) {
                if (checkSortingTag(sortBy)) {
                    PatientDao patientDao = PatientDaoImpl.INSTANCE;
                    result = patientDao.findAll(start, end, sortBy, reverse);
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

    @Override
    public Optional<Patient> findById(int patientId) throws ServiceException {
        Optional<Patient> optionalPatient;
        PatientDao patientDao = PatientDaoImpl.INSTANCE;
        try {
            if (patientId > 0) {
                optionalPatient = patientDao.findById(patientId);
            } else {
                optionalPatient = Optional.empty();
                LOGGER.log(Level.ERROR, "Invalid patient id: " + patientId);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return optionalPatient;
    }

    @Override
    public boolean exists(int patientId) throws ServiceException {
        boolean result = false;
        try {
            if (patientId > 0) {
                result = patientDao.exists(patientId);
            } else {
                LOGGER.log(Level.ERROR, "Invalid patient id: " + patientId);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Map<String, String> packPatientIntoMap(Patient patient) {
        Map<String, String> fields = new HashMap<>();
        if (patient != null) {
            int patientId = patient.getId();
            String stringPatientId = patientId != 0 ? String.valueOf(patientId) : "";
            fields.put(RequestParameter.PATIENT_ID, stringPatientId);
            fields.put(RequestParameter.PATIENT_NAME, patient.getName());
            fields.put(RequestParameter.PATIENT_SURNAME, patient.getSurname());
            fields.put(RequestParameter.PATIENT_AGE, String.valueOf(patient.getAge()));
            fields.put(RequestParameter.PATIENT_GENDER, String.valueOf(patient.getGender()));
            String diagnosis = patient.getDiagnosis();
            fields.put(RequestParameter.PATIENT_DIAGNOSIS, diagnosis == null ? "" : diagnosis);
            fields.put(RequestParameter.PATIENT_STATUS, patient.getStatus().toString());
            String stringRecordId;
            int recordId = patient.getRecordId();
            if (recordId == 0) {
                stringRecordId = "-";
            } else {
                stringRecordId = String.valueOf(recordId);
            }
            fields.put(RequestParameter.LAST_RECORD_ID, stringRecordId);
        }
        return fields;
    }

    @Override
    public boolean update(Map<String, String> newFields, Map<String, String> existingFields,
                          Map<String, String> currentFields) throws ServiceException {
        boolean result = false;
        PatientMapService mapService = PatientMapService.INSTANCE;
        int patientId = Integer.parseInt(currentFields.get(RequestParameter.PATIENT_ID));
        String name = null;
        String surname = null;
        int age = 0;
        char gender = ' ';
        boolean isValid = true;
        boolean noMatches = true;
        boolean isNameChanged = false;
        try {
            if (newFields.containsKey(RequestParameter.PATIENT_NAME)) {
                if (mapService.checkName(newFields)) {
                    name = newFields.get(RequestParameter.PATIENT_NAME);
                    isNameChanged = true;
                } else {
                    isValid = false;
                }
            } else {
                name = currentFields.get(RequestParameter.PATIENT_NAME);
            }
            if (newFields.containsKey(RequestParameter.PATIENT_SURNAME)) {
                if (mapService.checkSurname(newFields)) {
                    surname = newFields.get(RequestParameter.PATIENT_SURNAME);
                    isNameChanged = true;
                } else {
                    isValid = false;
                }
            } else {
                surname = currentFields.get(RequestParameter.PATIENT_SURNAME);
            }
            if (isNameChanged && patientDao.exists(name, surname)) {
                noMatches = false;
                existingFields.put(RequestParameter.PATIENT_NAME, name);
                existingFields.put(RequestParameter.PATIENT_SURNAME, surname);
                name = null;
                surname = null;
            }
            if (newFields.containsKey(RequestParameter.PATIENT_AGE)) {
                if (mapService.checkAge(newFields)) {
                    age = Integer.parseInt(newFields.get(RequestParameter.PATIENT_AGE));
                } else {
                    isValid = false;
                }
            } else {
                age = Integer.parseInt(currentFields.get(RequestParameter.PATIENT_AGE));
            }
            if (newFields.containsKey(RequestParameter.PATIENT_GENDER)) {
                if (mapService.checkGender(newFields)) {
                    gender = newFields.get(RequestParameter.PATIENT_GENDER).charAt(0);
                } else {
                    isValid = false;
                }
            } else {
                gender = currentFields.get(RequestParameter.PATIENT_GENDER).charAt(0);
            }
            if (isValid && noMatches) {
                Patient patient = new Patient(patientId, name, surname, age, gender);
                result = patientDao.update(patient);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Optional<Entity.Status> findStatus(int patientId) throws ServiceException {
        Optional<Entity.Status> result;
        try {
            if (patientId > 0) {
                result = patientDao.findStatus(patientId);
            } else {
                LOGGER.log(Level.DEBUG, "Invalid patient id value");
                result = Optional.empty();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean discharge(int patientId) throws ServiceException {
        boolean result = false;
        Optional<Entity.Status> optionalStatus = findStatus(patientId);
        if (optionalStatus.isPresent()) {
            Entity.Status status = optionalStatus.get();
            if (status == Entity.Status.WAITING_FOR_DECISION) {
                Entity.Status newStatus = Entity.Status.ARCHIVE;
                try {
                    result = patientDao.updateStatus(patientId, newStatus);
                } catch (DaoException e) {
                    throw new ServiceException(e);
                }
            } else {
                LOGGER.log(Level.DEBUG, "Incorrect current status");
            }
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
                || sortBy.equals(ColumnName.PATIENT_STATUS)
                || sortBy.equals(ColumnName.RECORD_ID);
    }
}
