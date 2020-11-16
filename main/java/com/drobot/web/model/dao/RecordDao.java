package com.drobot.web.model.dao;

import com.drobot.web.exception.DaoException;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.entity.PatientRecord;
import com.drobot.web.model.entity.SpecifiedRecord;
import com.drobot.web.model.entity.Treatment;

import java.util.List;
import java.util.Optional;

/**
 * Interface used for transactions with patient and record tables or just interactions with record table.
 *
 * @author Vladislav Drobot
 */
public interface RecordDao extends Dao<PatientRecord> {

    /**
     * Finds the last row of patient's record.
     *
     * @param patientId patient's ID int value.
     * @return Not empty Optional PatientRecord object if the last record was found, Optional.empty() otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    Optional<PatientRecord> findLast(int patientId) throws DaoException;

    /**
     * Finds a specified record data by patient's ID.
     *
     * @param patientId patient's ID int value.
     * @param start     int value of which row it should start the finding.
     * @param end       int value of which row it should finish the finding.
     * @param sortBy    String representation of sorting tag.
     * @param reverse   boolean flag if the list should be reserved.
     * @return List of SpecifiedRecord objects if the data has been found, empty List object otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    List<SpecifiedRecord> findByPatientId(int patientId, int start, int end, String sortBy, boolean reverse)
            throws DaoException;

    /**
     * Adds a new record and updates patient's status.
     *
     * @param record           PatientRecord object which fields should be used for updating.
     * @param newPatientStatus Entity.Status object for updating patient's status.
     * @return true if the transaction has been completed successfully, false otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    boolean addAndUpdatePatient(PatientRecord record, Entity.Status newPatientStatus) throws DaoException;

    /**
     * Finds a treatment from record table.
     *
     * @param recordId record's ID int value.
     * @return Not empty Optional Treatment object if it has been found, Optional.empty() otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    Optional<Treatment> findTreatment(int recordId) throws DaoException;

    /**
     * Sets executor ID and updates patient's status.
     *
     * @param recordId         record's ID int value.
     * @param executorId       executor's ID int value to be set.
     * @param patientId        patient's ID int value.
     * @param newPatientStatus Entity.Status object of new patient's status.
     * @return true if the transaction has been completed successfully, false otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    boolean setExecutorAndPatientStatus(int recordId, int executorId, int patientId, Entity.Status newPatientStatus)
            throws DaoException;

    /**
     * Counts a number of rows in the record table with a given patient's ID.
     *
     * @param patientId patient's ID int value.
     * @return int value of the rows number.
     * @throws DaoException if the database throws SQLException.
     */
    int count(int patientId) throws DaoException;
}
