package com.drobot.web.model.service;

import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.PatientRecord;
import com.drobot.web.model.entity.SpecifiedRecord;
import com.drobot.web.model.entity.Treatment;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface provides actions on patient record.
 *
 * @author Vladislav Drobot
 */
public interface RecordService {

    /**
     * Adds a patient record into a datasource, if given fields are valid.
     *
     * @param fields Map object with record's fields with RequestParameter's constants as keys inside.
     * @return true if record has been added successfully, false otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean add(Map<String, String> fields) throws ServiceException;

    /**
     * Finds SpecifiedRecord objects in a datasource in given borders and sorted by a given tag,
     * if the input parameters are valid.
     *
     * @param patientId patient's ID int value.
     * @param start     int value of which row it should start the finding.
     * @param end       int value of which row it should end the finding.
     * @param sortBy    String representation of sorting tag.
     * @param reverse   boolean flag if the list should be reserved.
     * @return List of specified record if the data has been found, empty List object otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    List<SpecifiedRecord> findByPatientId(int patientId, int start, int end, String sortBy, boolean reverse)
            throws ServiceException;

    /**
     * Finds a data in a datasource for creation a new record, if the input parameters are valid.
     *
     * @param patientId patient's ID int value.
     * @param userId    user's ID int value.
     * @return Map object with record's fields with RequestParameter's constants as keys inside.
     * @throws ServiceException if an error occurs while processing.
     */
    Map<String, String> findDataForNewRecord(int patientId, int userId) throws ServiceException;

    /**
     * Find a treatment int a datasource by a given record ID, if it is valid.
     *
     * @param recordId record's ID int value.
     * @return Not empty Optional Treatment object if it was found, Optional.empty() otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    Optional<Treatment> findTreatment(int recordId) throws ServiceException;

    /**
     * Executes a procedure for a patient with a given record ID, if the input parameters are valid.
     *
     * @param recordId       record's ID int value.
     * @param executorUserId executor user's ID int value.
     * @return true if the procedure has been executed, false otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean executeProcedure(int recordId, int executorUserId) throws ServiceException;

    /**
     * Executes a surgery for a patient with a given record ID, if the input parameters are valid
     * and executor has an access to that.
     *
     * @param recordId       record's ID int value.
     * @param executorUserId executor user's ID int value.
     * @return true if the surgery has been executed, false otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean executeSurgery(int recordId, int executorUserId) throws ServiceException;

    /**
     * Counts a total number of records in a datasource by a given patient ID.
     *
     * @param patientId patient's ID int value.
     * @return int value of the total number of objects.
     * @throws ServiceException if an error occurs while processing.
     */
    int count(int patientId) throws ServiceException;

    /**
     * Checks if the input parameter is valid, and then, if it is, finds the record in a database by a given ID.
     *
     * @param recordId record's ID int value.
     * @return Not empty Optional User object if it was found, Optional.empty() otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    Optional<PatientRecord> findById(int recordId) throws ServiceException;

    /**
     * Packs a given PatientRecord object into a Map with RequestParameter's constant keys.
     *
     * @param record PatientRecord object to be packed.
     * @return Map object with user's fields with RequestParameter's constants as keys inside.
     */
    Map<String, String> packRecordIntoMap(PatientRecord record);
}
