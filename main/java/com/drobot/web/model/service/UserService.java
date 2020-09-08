package com.drobot.web.model.service;

import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean add(String login, String email, String password, User.Role role) throws ServiceException;

    boolean remove(int userId) throws ServiceException;

    List<User> findBy(String value, String column) throws ServiceException;

    boolean existsLogin(String login) throws ServiceException;

    boolean existsEmail(String email) throws ServiceException;

    boolean checkPassword(String login, String password) throws ServiceException;

    Optional<User.Role> defineRole(String login, String password) throws ServiceException;

    boolean updateLogin(int userId, String newLogin) throws ServiceException;

    boolean updateEmail(int userId, String newEmail) throws ServiceException;

    boolean updatePassword(int userId, String newEmail) throws ServiceException;

    boolean updateRole(int userId, User.Role newRole) throws ServiceException;

    boolean updateStatus(int userId, int newStatus) throws ServiceException;
}
