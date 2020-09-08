package com.drobot.web.model.dao;

import com.drobot.web.model.entity.User;
import com.drobot.web.exception.DaoException;

public interface UserDao extends Dao<User> {
    boolean exists(User user) throws DaoException;

    boolean existsEmail(String email) throws DaoException;

    boolean existsLogin(String login) throws DaoException;
}
