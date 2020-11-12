package com.drobot.web.model.dao;

import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.entity.User;
import com.drobot.web.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao<User> {
    boolean add(User entity, String encPassword) throws DaoException;

    boolean existsEmail(String email) throws DaoException;

    boolean existsLogin(String login) throws DaoException;

    Optional<User> findByLogin(String login) throws DaoException;

    Optional<User> findByEmail(String email) throws DaoException;

    List<User> findByRole(User.Role role, String sortBy) throws DaoException;

    List<User> findByStatus(boolean isActive, String sortBy) throws DaoException;

    Optional<User> checkPassword(String login, String encPassword) throws DaoException;

    boolean updatePassword(int userId, String newEncPassword) throws DaoException;

    boolean updateStatus(int userId, Entity.Status newStatus) throws DaoException;

    boolean update(int userId, String newLogin, String newEmail) throws DaoException;

    Optional<Entity.Status> findStatus(int userId) throws DaoException;
}
