package com.drobot.web.model.dao;

import com.drobot.web.exception.DaoException;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.entity.Patient;

import java.util.Optional;

public interface PatientDao extends Dao<Patient> {

    boolean exists(String name, String surname) throws DaoException;

    boolean update(Patient patient) throws DaoException;

    Optional<Entity.Status> findStatus(int patientId) throws DaoException;

    boolean updateStatus(int patientId, Entity.Status newStatus) throws DaoException;
}
