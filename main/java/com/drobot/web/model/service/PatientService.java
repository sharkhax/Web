package com.drobot.web.model.service;

import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.Patient;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PatientService {

    boolean add(Map<String, String> fields, Map<String, String> existingFields) throws ServiceException;

    List<Patient> findAll(int start, int end, String sortBy, boolean reverse) throws ServiceException;

    int count() throws ServiceException;

    Optional<Patient> findById(int patientId) throws ServiceException;
}
