package com.drobot.web.model.service;

import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean add(String login, String email, String password, User.Role role) throws ServiceException;

    boolean remove(int userId) throws ServiceException;

    List<User> findAll(String sortBy) throws ServiceException;

    List<User> findAll(int start, int length, String sortBy) throws ServiceException;

    List<User> findAll(int start, int length) throws ServiceException;

    Optional<User> findById(int userId) throws ServiceException;

    Optional<User> findByLogin(String login) throws ServiceException;

    Optional<User> findByEmail(String email) throws ServiceException;

    List<User> findByRole(User.Role role, String sortBy) throws ServiceException;

    List<User> findByStatus(boolean isActive, String sortBy) throws ServiceException;

    boolean existsLogin(String login) throws ServiceException;

    boolean existsEmail(String email) throws ServiceException;

    Optional<User> signIn(String login, String password) throws ServiceException;

    boolean updateLogin(int userId, String newLogin) throws ServiceException;

    boolean updateEmail(int userId, String newEmail) throws ServiceException;

    boolean updatePassword(int userId, String newPassword) throws ServiceException;

    boolean updateRole(int userId, User.Role newRole) throws ServiceException;

    boolean updateStatus(int userId, Entity.Status newStatus) throws ServiceException;

    int count() throws ServiceException;
}
