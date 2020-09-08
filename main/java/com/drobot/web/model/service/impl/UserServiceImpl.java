package com.drobot.web.model.service.impl;

import com.drobot.web.exception.DaoException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.dao.ColumnName;
import com.drobot.web.model.dao.UserDao;
import com.drobot.web.model.dao.impl.UserDaoImpl;
import com.drobot.web.model.entity.User;
import com.drobot.web.model.service.UserService;
import com.drobot.web.model.util.Encrypter;
import com.drobot.web.model.validator.UserValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private final UserValidator userValidator = new UserValidator();
    private final UserDao dao = new UserDaoImpl();

    @Override
    public boolean add(String login, String email, String password, User.Role role) throws ServiceException {
        boolean result = false;
        try {
            if (userValidator.isLoginValid(login)
                    && userValidator.isEmailValid(email)
                    && userValidator.isPasswordValid(password)
                    && !dao.existsLogin(login)
                    && !dao.existsEmail(email)) {
                String encryptedPassword = Encrypter.encrypt(password);
                User user = new User(login, email, encryptedPassword, role);
                result = dao.add(user);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean remove(int userId) throws ServiceException {
        boolean result;
        try {
            result = dao.remove(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<User> findBy(String value, String column) throws ServiceException {
        List<User> result = new ArrayList<>();
        try {
            if (column.equals(ColumnName.USER_LOGIN)
                    || column.equals(ColumnName.USER_EMAIL)
                    || column.equals(ColumnName.USER_ROLE)
                    || column.equals(ColumnName.USER_STATUS)) {
                result = dao.findBy(value, column);
            } else {
                LOGGER.log(Level.ERROR, "Column is not supported to find by");
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean existsLogin(String login) throws ServiceException {
        boolean result = false;
        try {
            if (userValidator.isLoginValid(login)) {
                result = dao.existsLogin(login);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean existsEmail(String email) throws ServiceException {
        boolean result = false;
        try {
            if (userValidator.isEmailValid(email)) {
                result = dao.existsEmail(email);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean checkPassword(String login, String password) throws ServiceException {
        boolean result = false;
        try {
            if (userValidator.isLoginValid(login)
                    && userValidator.isPasswordValid(password)) {
                List<User> userList = dao.findBy(login, ColumnName.USER_LOGIN);
                if (!userList.isEmpty()) {
                    User user = userList.get(0);
                    String actualPassword = user.getEncPassword();
                    result = Encrypter.check(password, actualPassword);
                    if (result) {
                        LOGGER.log(Level.DEBUG, "Password is correct");
                    } else {
                        LOGGER.log(Level.DEBUG, "Password is incorrect");
                    }
                } else {
                    LOGGER.log(Level.DEBUG, "Login is incorrect");
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Optional<User.Role> defineRole(String login, String password) throws ServiceException {
        Optional<User.Role> result = Optional.empty();
        try {
            if (userValidator.isLoginValid(login)
                    && userValidator.isPasswordValid(password)) {
                List<User> userList = dao.findBy(login, ColumnName.USER_LOGIN);
                if (!userList.isEmpty()) {
                    User user = userList.get(0);
                    String actualPassword = user.getEncPassword();
                    if (Encrypter.check(password, actualPassword)) {
                        LOGGER.log(Level.DEBUG, "Password is correct");
                        User.Role role = user.getRole();
                        result = Optional.of(role);
                        LOGGER.log(Level.DEBUG, "Role has been defined");
                    }
                } else {
                    LOGGER.log(Level.DEBUG, "Login is incorrect");
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean updateLogin(int userId, String newLogin) throws ServiceException {
        boolean result = false;
        try {
            if (userValidator.isLoginValid(newLogin)) {
                Optional<User> optional = dao.findById(userId);
                if (optional.isPresent()) {
                    User user = optional.get();
                    user.setLogin(newLogin);
                    result = dao.update(user);
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean updateEmail(int userId, String newEmail) throws ServiceException {
        boolean result = false;
        try {
            if (userValidator.isEmailValid(newEmail)) {
                Optional<User> optional = dao.findById(userId);
                if (optional.isPresent()) {
                    User user = optional.get();
                    user.setEmail(newEmail);
                    result = dao.update(user);
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean updatePassword(int userId, String newPassword) throws ServiceException {
        boolean result = false;
        try {
            if (userValidator.isPasswordValid(newPassword)) {
                Optional<User> optional = dao.findById(userId);
                if (optional.isPresent()) {
                    String encryptedPassword = Encrypter.encrypt(newPassword);
                    User user = optional.get();
                    user.setEncPassword(encryptedPassword);
                    result = dao.update(user);
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean updateRole(int userId, User.Role newRole) throws ServiceException {
        boolean result = false;
        try {
            Optional<User> optional = dao.findById(userId);
            if (optional.isPresent()) {
                User user = optional.get();
                user.setRole(newRole);
                result = dao.update(user);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean updateStatus(int userId, int newStatus) throws ServiceException {
        boolean result = false;
        try {
            Optional<User> optional = dao.findById(userId);
            if (optional.isPresent()) {
                User user = optional.get();
                user.setStatus(newStatus);
                result = dao.update(user);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }
}
