package com.drobot.web.model.service;

import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.PatientRecord;
import com.drobot.web.model.entity.Treatment;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RecordService {

    boolean add(Map<String, String> fields) throws ServiceException;

    List<PatientRecord> findAll(int start, int length, String sortBy) throws ServiceException;

    Map<String, String> findDataForNewRecord(int patientId, int userId) throws ServiceException;

    Optional<Treatment> findTreatment(int recordId) throws ServiceException;

    boolean executeProcedure(int recordId, int executorUserId) throws ServiceException;

    boolean executeSurgery(int recordId, int executorUserId) throws ServiceException;
}
