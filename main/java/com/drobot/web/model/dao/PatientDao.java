package com.drobot.web.model.dao;

import com.drobot.web.exception.DaoException;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.entity.Patient;

import java.util.Optional;

/**
 * Interface used for interactions with patient table.
 *
 * @author Vladislav Drobot
 */
public interface PatientDao extends Dao<Patient> {

    /**
     * Adds a patient to the table.
     *
     * @param patient Patient object to be added.
     * @return true if patient has been added successfully, false otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    boolean add(Patient patient) throws DaoException;

    /**
     * Checks if there is the patient with given name and surname.
     *
     * @param name    String representation of patient's name.
     * @param surname String representation of patient's surname.
     * @return true if patient exists, false otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    boolean exists(String name, String surname) throws DaoException;

    /**
     * Updates a patient.
     *
     * @param patient Patient object with fields for updating.
     * @return true if patient has been updated successfully, false otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    boolean update(Patient patient) throws DaoException;

    /**
     * Finds patient's status.
     *
     * @param patientId patient's ID int value.
     * @return Not empty Optional Entity.Status object if status has been found, Optional.empty() otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    Optional<Entity.Status> findStatus(int patientId) throws DaoException;

    /**
     * Updates patient's status.
     *
     * @param patientId patient's ID inv value.
     * @param newStatus Entity.Status object of new status.
     * @return true if status has been updated successfully, false otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    boolean updateStatus(int patientId, Entity.Status newStatus) throws DaoException;
}
