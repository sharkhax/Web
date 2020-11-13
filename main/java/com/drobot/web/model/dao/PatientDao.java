package com.drobot.web.model.dao;

import com.drobot.web.exception.DaoException;
import com.drobot.web.model.entity.Patient;

public interface PatientDao extends Dao<Patient> {

    boolean exists(String name, String surname) throws DaoException;

    boolean update(Patient patient) throws DaoException;
}
