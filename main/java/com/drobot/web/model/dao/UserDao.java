package com.drobot.web.model.dao;

import com.drobot.web.model.entity.LoginInfo;
import com.drobot.web.model.entity.User;
import com.drobot.web.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao<User> {
    boolean exists(int userId) throws DaoException;

    boolean existsEmail(String email) throws DaoException;

    boolean existsLogin(String login) throws DaoException;

    Optional<User> findByLogin(String login) throws DaoException;

    Optional<User> findByEmail(String email) throws DaoException;

    List<User> findByRole(User.Role role, String sortBy) throws DaoException;

    List<User> findByStatus(byte status, String sortBy) throws DaoException;

    Optional<LoginInfo> checkPassword(String login, String encPassword) throws DaoException;

    boolean updatePassword(int userId, String newEncPassword) throws DaoException;
}
