package com.drobot.web.model.service;

import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interface provides actions on user.
 */
public interface UserService {

    /**
     * Adds user to the datasource.
     * @param login String representation of user's login.
     * @param email String representation of user's email.
     * @param password String representation of user's non-encrypted password.
     * @param role User.Role object of user's role.
     * @return True if user has been added, false otherwise.
     * @throws ServiceException If an exception was thrown while adding user.
     */
    boolean add(String login, String email, String password, User.Role role) throws ServiceException;

    /**
     * Returns list of all users from datasource.
     * @param sortBy String representation of list sorting tag.
     * @param reverse Boolean flag to reverse the list.
     * @return List object containing User objects.
     * @throws ServiceException If an exception was thrown while finding users.
     */
    List<User> findAll(String sortBy, boolean reverse) throws ServiceException;

    List<User> findAll(int start, int end, String sortBy, boolean reverse) throws ServiceException;

    List<User> findAll(int start, int end) throws ServiceException;

    Optional<User> findById(int userId) throws ServiceException;

    Optional<User> findByLogin(String login) throws ServiceException;

    Optional<User> findByEmail(String email) throws ServiceException;

    List<User> findByRole(User.Role role, String sortBy) throws ServiceException;

    List<User> findByStatus(boolean isActive, String sortBy) throws ServiceException;

    boolean exists(int userId) throws ServiceException;

    boolean existsLogin(String login) throws ServiceException;

    boolean existsEmail(String email) throws ServiceException;

    Optional<User> signIn(String login, String password) throws ServiceException;

    boolean updatePassword(int userId, String newPassword) throws ServiceException;
// FIXME: 10.11.2020
    /*boolean updateLogin(int userId, String newLogin) throws ServiceException;

    boolean updateEmail(int userId, String newEmail) throws ServiceException;

    boolean updateRole(int userId, User.Role newRole) throws ServiceException;

    boolean updateStatus(int userId, Entity.Status newStatus) throws ServiceException;*/

    int count() throws ServiceException;

    Map<String, String> packUserIntoMap(User user);

    boolean update(Map<String, String> newFields, Map<String, String> existingFields,
                   Map<String, String> currentFields) throws ServiceException;

    boolean blockUser(int userId) throws ServiceException;

    boolean unblockUser(int userId, int employeeId) throws ServiceException;

    Optional<Entity.Status> findStatus(int userId) throws ServiceException;
}
