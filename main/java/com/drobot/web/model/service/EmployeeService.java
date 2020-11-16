package com.drobot.web.model.service;

import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.Employee;

import java.util.Optional;

/**
 * Interface provides actions on employee.
 *
 * @author Vladislav Drobot
 */
public interface EmployeeService extends BaseService<Employee> {

    /**
     * Fires an employee with given ID, if given parameters are valid, and blocks its user's.
     *
     * @param employeeId employee's ID int value.
     * @param userId     user's ID int value.
     * @return true if the employee has been fired successfully, false otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean fireEmployee(int employeeId, int userId) throws ServiceException;

    /**
     * Restores an employee with given ID, if it is valid.
     *
     * @param employeeId employee's ID int value.
     * @return true if the employee has been restored successfully, false otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean restoreEmployee(int employeeId) throws ServiceException;

    /**
     * Sends an employee with given ID to vacation, if it is valid.
     *
     * @param employeeId employee's ID int value.
     * @return true if the employee has been sent successfully, false otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean sendToVacation(int employeeId) throws ServiceException;

    /**
     * Returns an employee with given ID from vacation, if it is valid.
     *
     * @param employeeId employee's ID int value.
     * @return true if the employee has been returned successfully, false otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean returnFromVacation(int employeeId) throws ServiceException;

    /**
     * Finds an employee by a given user's ID, if it is valid.
     *
     * @param userId user's ID int value.
     * @return Not empty Optional Employee object if it was found, Optional.empty() otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    Optional<Employee> findByUserId(int userId) throws ServiceException;
}
