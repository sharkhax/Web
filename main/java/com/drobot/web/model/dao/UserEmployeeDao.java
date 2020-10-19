package com.drobot.web.model.dao;

import com.drobot.web.exception.DaoException;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.model.entity.User;

public interface UserEmployeeDao extends CloseableDao {
    boolean register(User user, String password, Employee employee) throws DaoException;

    boolean loadUserEmployeeData(int userId, User user, Employee employee) throws DaoException;
}
