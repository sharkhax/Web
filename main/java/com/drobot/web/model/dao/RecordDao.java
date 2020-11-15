package com.drobot.web.model.dao;

import com.drobot.web.exception.DaoException;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.entity.PatientRecord;
import com.drobot.web.model.entity.Treatment;

import java.util.Optional;

public interface RecordDao extends Dao<PatientRecord> {

    Optional<PatientRecord> findByPatientId(int patientId) throws DaoException;

    boolean addAndUpdatePatient(PatientRecord record, Entity.Status newPatientStatus) throws DaoException;

    Optional<Treatment> findTreatment(int recordId) throws DaoException;

    boolean setExecutorAndPatientStatus(int recordId, int executorId, int patientId, Entity.Status newPatientStatus)
            throws DaoException;
}
