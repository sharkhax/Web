package com.drobot.web.model.dao;

import com.drobot.web.exception.DaoException;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.entity.PatientRecord;
import com.drobot.web.model.entity.SpecifiedRecord;
import com.drobot.web.model.entity.Treatment;

import java.util.List;
import java.util.Optional;

public interface RecordDao extends Dao<PatientRecord> {

    Optional<PatientRecord> findLast(int patientId) throws DaoException;

    List<SpecifiedRecord> findByPatientId(int patientId, int start, int end, String sortBy, boolean reverse)
            throws DaoException;

    boolean addAndUpdatePatient(PatientRecord record, Entity.Status newPatientStatus) throws DaoException;

    Optional<Treatment> findTreatment(int recordId) throws DaoException;

    boolean setExecutorAndPatientStatus(int recordId, int executorId, int patientId, Entity.Status newPatientStatus)
            throws DaoException;

    int count(int patientId) throws DaoException;
}
