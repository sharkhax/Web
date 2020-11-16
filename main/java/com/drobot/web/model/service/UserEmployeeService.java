package com.drobot.web.model.service;

import com.drobot.web.exception.ServiceException;

import java.util.Map;

/**
 * Interface provides actions on user and employee together.
 *
 * @author Vladislav Drobot
 */
public interface UserEmployeeService {

    /**
     * Register a new user, creates referenced employee if given fields are valid and don't exist.
     *
     * @param fields         Map object with user's and employee's fields with RequestParameter's constants
     *                       as keys inside.
     * @param existingFields empty Map object for existing user's or employee's fields. They will be put there.
     * @return true if registration was completed successfully, false otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean register(Map<String, String> fields, Map<String, String> existingFields) throws ServiceException;

    /**
     * Loads user's data, including its referenced employee's, if a given user ID is valid.
     *
     * @param userId user's ID int value.
     * @return Map object with user's and employee's fields with RequestParameter's constant keys inside.
     * @throws ServiceException if an error occurs while processing.
     */
    Map<String, String> loadUserData(int userId) throws ServiceException;
}
