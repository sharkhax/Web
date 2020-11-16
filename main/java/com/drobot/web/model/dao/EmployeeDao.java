package com.drobot.web.model.dao;

import com.drobot.web.exception.DaoException;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.model.entity.Entity;

import java.util.Optional;

/**
 * Interface used for interactions with employee table.
 *
 * @author Vladislav Drobot
 */
public interface EmployeeDao extends Dao<Employee> {

    /**
     * Checks if there is the employee with given name and surname.
     *
     * @param name    String representation of employee's name.
     * @param surname String representation of employee's surname.
     * @return true if employee exists, false otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    boolean exists(String name, String surname) throws DaoException;

    /**
     * Finds the employee with a given user ID.
     *
     * @param userId user's ID int value.
     * @return Not empty Optional Employee object if it has been found, Optional.empty() otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    Optional<Employee> findByUserId(int userId) throws DaoException;

    /**
     * Finds employee's status.
     *
     * @param employeeId employee's ID int value.
     * @return Not empty Optional Entity.Status object if status has been found, Optional.empty() otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    Optional<Entity.Status> findStatus(int employeeId) throws DaoException;

    /**
     * Updates employee's status.
     *
     * @param employeeId employee's ID int value.
     * @param newStatus  Entity.Status object of new status.
     * @return true if status has been updated successfully, false otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    boolean updateStatus(int employeeId, Entity.Status newStatus) throws DaoException;

    /**
     * Sets a new dismiss date.
     *
     * @param employeeId        employee's ID int value.
     * @param dismissDateMillis a new dismiss date long value in milliseconds.
     * @return true if dismiss date has been updated successfully, false otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    boolean setDismissDate(int employeeId, long dismissDateMillis) throws DaoException;
}
