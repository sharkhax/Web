package com.drobot.web.model.service;

import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.User;

import java.util.Optional;

/**
 * Interface provides actions on user.
 *
 * @author Vladislav Drobot
 */
public interface UserService extends BaseService<User> {

    /**
     * Checks if the input parameters are valid, and then, if they are, gives a permission to user to sign in.
     *
     * @param login    String object of user's login.
     * @param password String object of user's unencrypted password.
     * @return Not empty Optional User object if permission is granted, Optional.empty() otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    Optional<User> signIn(String login, String password) throws ServiceException;

    /**
     * Checks if the input parameters are valid, and then, if they are, updated user's password in a datasource.
     *
     * @param userId      user's ID int value.
     * @param newPassword String object of new user's unencrypted password.
     * @return true if it was updated, false otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean updatePassword(int userId, String newPassword) throws ServiceException;

    /**
     * Checks if the input parameter is valid, and then, if it is, blocks user.
     *
     * @param userId user's ID int value.
     * @return true if user has been blocked, false otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean blockUser(int userId) throws ServiceException;

    /**
     * Checks if the input parameter is valid, and then, if it is, unblocks user if employee's status
     * is not 'archive'.
     *
     * @param userId     user's ID int value.
     * @param employeeId employee's ID int value.
     * @return true if user has been unblocked, false otherwise.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean unblockUser(int userId, int employeeId) throws ServiceException;
}
