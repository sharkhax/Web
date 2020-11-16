package com.drobot.web.model.dao.impl;

import com.drobot.web.exception.ConnectionPoolException;
import com.drobot.web.exception.DaoException;
import com.drobot.web.model.dao.RecordDao;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.entity.PatientRecord;
import com.drobot.web.model.entity.SpecifiedRecord;
import com.drobot.web.model.entity.Treatment;
import com.drobot.web.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * RecordDao implementation.
 *
 * @author Vladislav Drobot
 */
public enum RecordDaoImpl implements RecordDao {

    /**
     * Represents a singleton pattern realization.
     */
    INSTANCE;

    private final Logger LOGGER = LogManager.getLogger(RecordDaoImpl.class);
    private final String ADD_STATEMENT = "INSERT INTO hospital.patient_records(patient_id_fk, " +
            "attending_doctor_id, curing_id, diagnosis) VALUES(?, ?, ?, ?);";
    private final String UPDATE_PATIENT_STATUS = "UPDATE hospital.patients SET patient_status = ? WHERE patient_id = ?";
    private final String FIND_TREATMENT = "SELECT treatment_name FROM hospital.patient_records " +
            "INNER JOIN hospital.treatments ON curing_id = treatment_id WHERE record_id = ?";
    private final String FIND_BY_ID_STATEMENT = new StringBuilder(
            "SELECT record_id, patient_id_fk, attending_doctor_id, treatment_name, executor_id, diagnosis ")
            .append("FROM hospital.patient_records INNER JOIN hospital.treatments ON curing_id = treatment_id ")
            .append("WHERE record_id = ?;").toString();
    private final String UPDATE_EXECUTOR_STATEMENT =
            "UPDATE hospital.patient_records SET executor_id = ? WHERE record_id = ?;";
    private final String FIND_LAST_BY_PATIENT_ID_STATEMENT =
            "SELECT record_id, patient_id_fk, attending_doctor_id, curing_id, executor_id, diagnosis " +
                    "FROM hospital.patient_records WHERE patient_id_fk = ? ORDER BY record_id DESC LIMIT 1;";
    private final String FIND_BY_PATIENT_ID_STATEMENT = new StringBuilder(
            "SELECT record_id, doctor.employee_name AS doctor_name, doctor.employee_surname, treatment_name, ")
            .append("executor.employee_name AS executor_name, executor.employee_surname, diagnosis ")
            .append("FROM hospital.patient_records ")
            .append("INNER JOIN hospital.employees AS doctor ON attending_doctor_id = doctor.employee_id ")
            .append("LEFT JOIN hospital.employees AS executor ON executor_id = executor.employee_id ")
            .append("INNER JOIN hospital.treatments AS curing_id ON treatment_id = curing_id ")
            .append("WHERE patient_id_fk = ? ORDER BY * LIMIT ?, ?;")
            .toString();
    private final String COUNT_BY_PATIENT_ID_STATEMENT =
            "SELECT COUNT(*) AS label FROM hospital.patient_records WHERE patient_id_fk = ?;";

    @Override
    public List<PatientRecord> findAll(int start, int end, String sortBy, boolean reverse) throws DaoException {
        LOGGER.log(Level.ERROR, "The method is unsupported.");
        throw new UnsupportedOperationException("The method is unsupported.");
    }

    @Override
    public Optional<PatientRecord> findById(int recordId) throws DaoException {
        return findBySomeId(recordId, FIND_BY_ID_STATEMENT);

    }

    @Override
    public boolean exists(int recordId) throws DaoException {
        LOGGER.log(Level.ERROR, "The method is unsupported.");
        throw new UnsupportedOperationException("The method is unsupported.");
    }

    @Override
    public int count() throws DaoException {
        LOGGER.log(Level.ERROR, "The method is unsupported.");
        throw new UnsupportedOperationException("The method is unsupported.");
    }

    @Override
    public Optional<PatientRecord> findLast(int patientId) throws DaoException {
        return findBySomeId(patientId, FIND_LAST_BY_PATIENT_ID_STATEMENT);
    }

    @Override
    public List<SpecifiedRecord> findByPatientId(int patientId, int start, int end, String sortBy, boolean reverse)
            throws DaoException {
        List<SpecifiedRecord> result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            StringBuilder sqlBuilder = new StringBuilder(FIND_BY_PATIENT_ID_STATEMENT);
            if (reverse) {
                sortBy = sortBy + SPACE + DESC;
            }
            int indexOfAsterisk = sqlBuilder.lastIndexOf(ASTERISK);
            String sql = sqlBuilder.replace(indexOfAsterisk, indexOfAsterisk + 1, sortBy).toString();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, patientId);
            statement.setInt(2, start);
            statement.setInt(3, end);
            ResultSet resultSet = statement.executeQuery();
            result = createSpecifiedRecordListFromResultSet(resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            rollback(connection);
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean addAndUpdatePatient(PatientRecord record, Entity.Status newPatientStatus)
            throws DaoException {
        boolean result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ADD_STATEMENT);
            int patientId = record.getPatientId();
            statement.setInt(1, patientId);
            statement.setInt(2, record.getDoctorId());
            statement.setInt(3, record.getTreatment().getTreatmentId());
            statement.setString(4, record.getDiagnosis());
            statement.execute();
            LOGGER.log(Level.DEBUG, "Record has been added successfully");
            statement = connection.prepareStatement(UPDATE_PATIENT_STATUS);
            statement.setInt(1, newPatientStatus.getStatusId());
            statement.setInt(2, patientId);
            statement.execute();
            LOGGER.log(Level.DEBUG, "Patient has been updated successfully, transaction complete");
            connection.commit();
            result = true;
        } catch (SQLException | ConnectionPoolException e) {
            rollback(connection);
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public Optional<Treatment> findTreatment(int recordId) throws DaoException {
        Optional<Treatment> result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(FIND_TREATMENT);
            statement.setInt(1, recordId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String stringTreatment = resultSet.getString(1);
                result = Optional.of(Treatment.valueOf(stringTreatment));
                LOGGER.log(Level.DEBUG, "Treatment has been found");
            } else {
                LOGGER.log(Level.DEBUG, "Record or its treatment haven't been found");
                result = Optional.empty();
            }
        } catch (SQLException | ConnectionPoolException e) {
            rollback(connection);
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean setExecutorAndPatientStatus(int recordId, int executorId, int patientId,
                                               Entity.Status newPatientStatus) throws DaoException {
        boolean result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_EXECUTOR_STATEMENT);
            statement.setInt(1, executorId);
            statement.setInt(2, recordId);
            statement.execute();
            LOGGER.log(Level.DEBUG, "Executor has been updated");
            statement = connection.prepareStatement(UPDATE_PATIENT_STATUS);
            statement.setInt(1, newPatientStatus.getStatusId());
            statement.setInt(2, patientId);
            statement.execute();
            LOGGER.log(Level.DEBUG, "Status has been updated");
            connection.commit();
            LOGGER.log(Level.DEBUG, "Transaction complete");
            result = true;
        } catch (SQLException | ConnectionPoolException e) {
            rollback(connection);
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public int count(int patientId) throws DaoException {
        int result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(COUNT_BY_PATIENT_ID_STATEMENT);
            statement.setInt(1, patientId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            result = resultSet.getInt(1);
            LOGGER.log(Level.DEBUG, "Users have been counted");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    private List<PatientRecord> createRecordListFromResultSet(ResultSet resultSet) throws SQLException {
        List<PatientRecord> result = new ArrayList<>();
        if (resultSet != null && resultSet.next()) {
            do {
                int recordId = resultSet.getInt(1);
                int patientId = resultSet.getInt(2);
                int doctorId = resultSet.getInt(3);
                String stringTreatment = resultSet.getString(4);
                int executorId = resultSet.getInt(5);
                String diagnosis = resultSet.getString(6);
                Treatment treatment = Treatment.valueOf(stringTreatment);
                PatientRecord patientRecord =
                        new PatientRecord(recordId, patientId, doctorId, treatment, executorId, diagnosis);
                result.add(patientRecord);
            } while (resultSet.next());
            LOGGER.log(Level.DEBUG, result.size() + " records have been found");
        } else {
            LOGGER.log(Level.DEBUG, "ResultSet is null or empty");
        }
        return result;
    }

    private List<SpecifiedRecord> createSpecifiedRecordListFromResultSet(ResultSet resultSet) throws SQLException {
        List<SpecifiedRecord> result = new ArrayList<>();
        if (resultSet != null && resultSet.next()) {
            do {
                int recordId = resultSet.getInt(1);
                String doctorName = resultSet.getString(2);
                String doctorSurname = resultSet.getString(3);
                String stringTreatment = resultSet.getString(4);
                String executorName = resultSet.getString(5);
                String executorSurname = resultSet.getString(6);
                String diagnosis = resultSet.getString(7);
                Treatment treatment = Treatment.valueOf(stringTreatment);
                SpecifiedRecord record = new SpecifiedRecord(
                        recordId, doctorName, doctorSurname, treatment, executorName, executorSurname, diagnosis);
                result.add(record);
            } while (resultSet.next());
            LOGGER.log(Level.DEBUG, result.size() + " records have been found");
        } else {
            LOGGER.log(Level.DEBUG, "ResultSet is null or empty");
        }
        return result;
    }

    private Optional<PatientRecord> findBySomeId(int value, String sql) throws DaoException {
        Optional<PatientRecord> result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, value);
            ResultSet resultSet = statement.executeQuery();
            List<PatientRecord> list = createRecordListFromResultSet(resultSet);
            if (list.isEmpty()) {
                result = Optional.empty();
            } else {
                result = Optional.of(list.get(0));
            }
        } catch (SQLException | ConnectionPoolException e) {
            rollback(connection);
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }
}
