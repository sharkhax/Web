package com.drobot.web.model.service;

import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.PatientRecord;

import java.util.List;
import java.util.Map;

public interface RecordService {

    boolean add(Map<String, String> fields) throws ServiceException;
    List<PatientRecord> findAll(int start, int length, String sortBy) throws ServiceException;
}
