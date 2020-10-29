package com.drobot.web.model.service.impl;

import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.service.PatientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public enum PatientServiceImpl implements PatientService {

    INSTANCE;

    private final Logger LOGGER = LogManager.getLogger(PatientServiceImpl.class);


    @Override
    public boolean add(Map<String, String> fields, Map<String, String> existingFields) throws ServiceException {
        return false;
    }
}
