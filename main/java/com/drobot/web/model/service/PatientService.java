package com.drobot.web.model.service;

import com.drobot.web.exception.ServiceException;

import java.util.Map;

public interface PatientService {
    boolean add(Map<String, String> fields, Map<String, String> existingFields) throws ServiceException;
}
