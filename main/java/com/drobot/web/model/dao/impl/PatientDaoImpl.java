package com.drobot.web.model.dao.impl;

import com.drobot.web.exception.ConnectionPoolException;
import com.drobot.web.exception.DaoException;
import com.drobot.web.model.dao.PatientDao;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.entity.Patient;
import com.drobot.web.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum PatientDaoImpl implements PatientDao {

    INSTANCE;

    private final Logger LOGGER = LogManager.getLogger(PatientDaoImpl.class);
    private final String EXISTS_STATEMENT =
            "SELECT COUNT(*) as label FROM hospital.patients WHERE patient_name = ? AND patient_surname = ?;";
    private final String ADD_STATEMENT = "INSERT INTO hospital.patients(patient_name, patient_surname, " +
            "patient_age, patient_gender, diagnosis) VALUES(?, ?, ?, ?, ?)";
    private final String FIND_ALL_LIMIT_STATEMENT = new StringBuilder(
            "SELECT patient_id, patient_name, patient_surname, patient_age, patient_gender, diagnosis, status_name, ")
            .append("record_id FROM hospital.patients INNER JOIN hospital.statuses ON patient_status = status_id ")
            .append("LEFT JOIN hospital.patient_records ON patient_id = patient_id_fk ")
            .append("ORDER BY  LIMIT ?, ?;").toString();
    private final String COUNT_STATEMENT = "SELECT COUNT(*) as label FROM hospital.patients;";
    private final String FIND_BY_ID_STATEMENT = new StringBuilder(
            "SELECT patient_id, patient_name, patient_surname, patient_age, patient_gender, diagnosis, status_name, ")
            .append("record_id FROM hospital.patients INNER JOIN hospital.statuses ON patient_status = status_id ")
            .append("LEFT JOIN hospital.patient_records ON patient_id = patient_id_fk WHERE patient_id = ?;")
            .toString();
    private final String EXISTS_ID_STATEMENT = "SELECT COUNT(*) as label FROM hospital.patients WHERE patient_id = ?;";
    private final String UPDATE_STATEMENT =
            "UPDATE hospital.patients SET patient_name = ?, patient_surname = ?, patient_age = ?, " +
                    "patient_gender = ?, diagnosis = ? WHERE patient_id = ?;";

    @Override
    public boolean exists(String name, String surname) throws DaoException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(EXISTS_STATEMENT);
            statement.setString(1, name);
            statement.setString(2, surname);
            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next() && resultSet.getInt(1) != 0;
            String log = result ? "Patient with such name exists" : "Patient with such name doesn't exist";
            LOGGER.log(Level.DEBUG, log);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean update(Patient patient) throws DaoException {
        boolean result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UPDATE_STATEMENT);
            statement.setString(1, patient.getName());
            statement.setString(2, patient.getSurname());
            statement.setString(3, String.valueOf(patient.getAge()));
            statement.setString(4, String.valueOf(patient.getGender()));
            statement.setString(5, patient.getDiagnosis());
            statement.setString(6, String.valueOf(patient.getId()));
            statement.execute();
            LOGGER.log(Level.DEBUG, "Patient has been updated successfully");
            result = true;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean exists(int patientId) throws DaoException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(EXISTS_ID_STATEMENT);
            statement.setInt(1, patientId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1) != 0;
                String log = result ? "Patient with id " + patientId + " has been found"
                        : "Patient with id " + patientId + " hasn't been found";
                LOGGER.log(Level.DEBUG, log);
            } else {
                LOGGER.log(Level.ERROR, "Result set is empty");
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean add(Patient patient) throws DaoException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(ADD_STATEMENT);
            String name = patient.getName();
            String surname = patient.getSurname();
            int age = patient.getAge();
            char gender = patient.getGender();
            String diagnosis = patient.getDiagnosis();
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setInt(3, age);
            statement.setString(4, String.valueOf(gender));
            statement.setString(5, diagnosis);
            statement.execute();
            LOGGER.log(Level.DEBUG, "Patient has been added");
            result = true;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public List<Patient> findAll(String sortBy, boolean reverse) throws DaoException {
        return null;
    }

    @Override
    public List<Patient> findAll(int start, int end, String sortBy, boolean reverse) throws DaoException {
        List<Patient> result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            if (reverse) {
                sortBy = sortBy + SPACE + DESC;
            }
            String sql = new StringBuilder(FIND_ALL_LIMIT_STATEMENT).insert(269, sortBy).toString();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, start);
            statement.setInt(2, end);
            ResultSet resultSet = statement.executeQuery();
            result = createPatientListFromResultSet(resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public Optional<Patient> findById(int patientId) throws DaoException {
        Optional<Patient> result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(FIND_BY_ID_STATEMENT);
            statement.setInt(1, patientId);
            ResultSet resultSet = statement.executeQuery();
            List<Patient> patientList = createPatientListFromResultSet(resultSet);
            if (!patientList.isEmpty()) {
                result = Optional.of(patientList.get(0));
            } else {
                LOGGER.log(Level.DEBUG, "Patient with id " + patientId + " has not been found");
                result = Optional.empty();
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public int count() throws DaoException {
        int result;
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(COUNT_STATEMENT);
            resultSet.next();
            result = resultSet.getInt(1);
            LOGGER.log(Level.DEBUG, "Patients have been counted");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    private List<Patient> createPatientListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Patient> result = new ArrayList<>();
        if (resultSet != null && resultSet.next()) {
            do {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String surname = resultSet.getString(3);
                int age = resultSet.getInt(4);
                char gender = resultSet.getString(5).charAt(0);
                String diagnosis = resultSet.getString(6);
                String stringStatus = resultSet.getString(7);
                Entity.Status status = Entity.Status.valueOf(stringStatus);
                int recordId = resultSet.getInt(8);
                Patient patient = new Patient(id, name, surname, age, gender, diagnosis, status, recordId);
                result.add(patient);
            } while (resultSet.next());
            LOGGER.log(Level.DEBUG, result.size() + " patients have been found");
        } else {
            LOGGER.log(Level.WARN, "Result set is null or empty");
        }
        return result;
    }


}
