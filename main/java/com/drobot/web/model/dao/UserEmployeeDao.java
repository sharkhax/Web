package com.drobot.web.model.dao;

import com.drobot.web.exception.DaoException;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.entity.User;

/**
 * Interface used for transactions with user and employee tables.
 *
 * @author Vladislav Drobot
 */
public interface UserEmployeeDao extends CloseableDao {

    /**
     * Registers a new user and creates an employee referenced to the user.
     *
     * @param user     User object to be created at database.
     * @param password String object of user's password.
     * @param employee Employee object to be created at database.
     * @return true if registration successfully completed, false otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    boolean register(User user, String password, Employee employee) throws DaoException;

    /**
     * Loads a data about user and its referenced employee.
     *
     * @param userId   user's ID int value.
     * @param user     User's object reference to be filled with the data.
     * @param employee Employee's object reference to be filled with the data.
     * @return true if the data has been loaded successfully, false otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    boolean loadUserEmployeeData(int userId, User user, Employee employee) throws DaoException;

    /**
     * Updated employee's fields and its referenced user's role.
     *
     * @param employee    Employee object, containing new fields.
     * @param newUserRole a new User.Role object.
     * @return true if employee and user's role have been updated successfully, false otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    boolean updateEmployee(Employee employee, User.Role newUserRole) throws DaoException;

    /**
     * Updates user's and employee's statuses.
     *
     * @param employeeId        employee's ID int value.
     * @param userId            user's ID int value.
     * @param newEmployeeStatus a new employee's Entity.Status object.
     * @param newUserStatus     a new user's Entity.Status object.
     * @return true if statuses have been updated successfully, false otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    boolean updateStatuses(int employeeId, int userId, Entity.Status newEmployeeStatus, Entity.Status newUserStatus)
            throws DaoException;
}
