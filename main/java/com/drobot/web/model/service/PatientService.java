package com.drobot.web.model.service;

import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.entity.Patient;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PatientService {

    boolean add(Map<String, String> fields, Map<String, String> existingFields) throws ServiceException;

    List<Patient> findAll(int start, int end, String sortBy, boolean reverse) throws ServiceException;

    int count() throws ServiceException;

    Optional<Patient> findById(int patientId) throws ServiceException;

    boolean exists(int patientId) throws ServiceException;

    Map<String, String> packPatientIntoMap(Patient patient);

    boolean update(Map<String, String> newFields, Map<String, String> existingFields,
                   Map<String, String> currentFields) throws ServiceException;

    Optional<Entity.Status> findStatus(int patientId) throws ServiceException;

    boolean discharge(int patientId) throws ServiceException;
}
