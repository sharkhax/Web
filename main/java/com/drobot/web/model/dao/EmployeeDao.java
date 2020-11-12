package com.drobot.web.model.dao;

import com.drobot.web.exception.DaoException;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.model.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao extends Dao<Employee> {

    boolean exists(String name, String surname) throws DaoException;

    List<Employee> findByName(String name, String surname, String sortBy) throws DaoException;

    List<Employee> findByAge(int age, String sortBy) throws DaoException;

    List<Employee> findByGender(char gender, String sortBy) throws DaoException;

    List<Employee> findByPosition(Employee.Position position, String sortBy) throws DaoException;

    List<Employee> findByHireDateInterval(long firstDateMillis, long secondDateMillis) throws DaoException;

    List<Employee> findByStatus(Entity.Status status, String sortBy) throws DaoException;

    Optional<Employee> findByUserId(int userId) throws DaoException;

    Optional<Entity.Status> findStatus(int employeeId) throws DaoException;

    boolean updateStatus(int employeeId, Entity.Status newStatus) throws DaoException;

    boolean setDismissDate(int employeeId, long dismissDateMillis) throws DaoException;
}
