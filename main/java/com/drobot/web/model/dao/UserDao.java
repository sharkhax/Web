package com.drobot.web.model.dao;

import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.entity.User;
import com.drobot.web.exception.DaoException;

import java.util.Optional;

/**
 * Interface used for interactions with user table.
 *
 * @author Vladislav Drobot
 */
public interface UserDao extends Dao<User> {

    /**
     * Adds user to the table.
     *
     * @param user        User object to be added.
     * @param encPassword String representation of user's encrypted password to be set.
     * @return true if user has been added successfully, false otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    boolean add(User user, String encPassword) throws DaoException;

    /**
     * Checks if there is the user with a given email.
     *
     * @param email String object of the email.
     * @return true if user with the given email exists, false otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    boolean existsEmail(String email) throws DaoException;

    /**
     * Checks if there is the user with a given login.
     *
     * @param login String object of the login.
     * @return true if user with the given login exists, false otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    boolean existsLogin(String login) throws DaoException;

    /**
     * Checks if a given encrypted password is correct for a given user.
     *
     * @param login       String object of user's login.
     * @param encPassword String object of the encrypted password to be checked.
     * @return Not empty Optional User object if the given password is correct, Optional.empty() otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    Optional<User> checkPassword(String login, String encPassword) throws DaoException;

    /**
     * Updated user's password.
     *
     * @param userId         user's ID int value.
     * @param newEncPassword String object of the new encrypted password.
     * @return true if password has been updated successfully, false otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    boolean updatePassword(int userId, String newEncPassword) throws DaoException;

    /**
     * Updated user's status.
     *
     * @param userId    user's ID int value.
     * @param newStatus Entity.Status object of new user's status.
     * @return true if status has been updated successfully, false otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    boolean updateStatus(int userId, Entity.Status newStatus) throws DaoException;

    /**
     * Updates login and email of the given user.
     *
     * @param userId   user's ID int value.
     * @param newLogin String object of the new login.
     * @param newEmail String object of the new email.
     * @return true if login and email have been updated successfully, false otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    boolean update(int userId, String newLogin, String newEmail) throws DaoException;

    /**
     * Finds user's status.
     *
     * @param userId user's ID int value.
     * @return Not empty Optional Entity.Status object if the status was found, Optional.empty() otherwise.
     * @throws DaoException if the database throws SQLException.
     */
    Optional<Entity.Status> findStatus(int userId) throws DaoException;
}
