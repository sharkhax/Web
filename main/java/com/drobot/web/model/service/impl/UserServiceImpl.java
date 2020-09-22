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

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public boolean add(String login, String email, String password, User.Role role) throws ServiceException {
        boolean result = false;
        UserValidator userValidator = new UserValidator();
        try {
            if (userValidator.isLoginValid(login)
                    && userValidator.isEmailValid(email)
                    && userValidator.isPasswordValid(password)) {
                String encryptedPassword = Encrypter.encrypt(password);
                User user = new User(login, email, role);
                result = userDao.add(user, encryptedPassword);
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
            result = userDao.remove(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Optional<User> findByLogin(String login) throws ServiceException {
        Optional<User> result;
        UserValidator userValidator = new UserValidator();
        if (userValidator.isLoginValid(login)) {
            try {
                result = userDao.findByLogin(login);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            result = Optional.empty();
        }
        return result;
    }

    @Override
    public Optional<User> findByEmail(String email) throws ServiceException {
        Optional<User> result;
        UserValidator userValidator = new UserValidator();
        try {
            if (userValidator.isEmailValid(email)) {
                result = userDao.findByEmail(email);
            } else {
                result = Optional.empty();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<User> findByRole(User.Role role, String sortBy) throws ServiceException {
        List<User> result;
        try {
            if (checkSortingTag(sortBy)) {
                result = userDao.findByRole(role, sortBy);
            } else {
                LOGGER.log(Level.ERROR, "Unsupported sorting tag");
                result = List.of();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<User> findByStatus(boolean isActive, String sortBy) throws ServiceException {
        List<User> result;
        try {
            if (checkSortingTag(sortBy)) {
                result = userDao.findByStatus(isActive, sortBy);
            } else {
                LOGGER.log(Level.ERROR, "Unsupported sorting tag");
                result = List.of();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean existsLogin(String login) throws ServiceException {
        boolean result = false;
        UserValidator userValidator = new UserValidator();
        try {
            if (userValidator.isLoginValid(login)) {
                result = userDao.existsLogin(login);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean existsEmail(String email) throws ServiceException {
        boolean result = false;
        UserValidator userValidator = new UserValidator();
        try {
            if (userValidator.isEmailValid(email)) {
                result = userDao.existsEmail(email);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Optional<User> signIn(String login, String password) throws ServiceException {
        Optional<User> result;
        UserValidator userValidator = new UserValidator();
        try {
            if (userValidator.isLoginValid(login)
                    && userValidator.isPasswordValid(password)) {
                result = userDao.checkPassword(login, password);
            } else {
                result = Optional.empty();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean updateLogin(int userId, String newLogin) throws ServiceException {
        boolean result = false;
        UserValidator userValidator = new UserValidator();
        try {
            if (userValidator.isLoginValid(newLogin)) {
                Optional<User> optional = userDao.findById(userId);
                if (optional.isPresent()) {
                    User user = optional.get();
                    if (!user.getLogin().equals(newLogin)) {
                        user.setLogin(newLogin);
                        result = userDao.update(user);
                    } else {
                        LOGGER.log(Level.DEBUG, "Such user login is already set");
                    }
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
        UserValidator userValidator = new UserValidator();
        try {
            if (userValidator.isEmailValid(newEmail)) {
                Optional<User> optional = userDao.findById(userId);
                if (optional.isPresent()) {
                    User user = optional.get();
                    if (!user.getEmail().equals(newEmail)) {
                        user.setEmail(newEmail);
                        result = userDao.update(user);
                    } else {
                        LOGGER.log(Level.DEBUG, "Such user email is already set");
                    }
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
        UserValidator userValidator = new UserValidator();
        try {
            if (userValidator.isPasswordValid(newPassword)) {
                Optional<User> optional = userDao.findById(userId);
                if (optional.isPresent()) {
                    String encryptedPassword = Encrypter.encrypt(newPassword);
                    result = userDao.updatePassword(userId, encryptedPassword);
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
            Optional<User> optional = userDao.findById(userId);
            if (optional.isPresent()) {
                User user = optional.get();
                if (!user.getRole().equals(newRole)) {
                    user.setRole(newRole);
                    result = userDao.update(user);
                } else {
                    LOGGER.log(Level.DEBUG, "Such user role is already set");
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean updateStatus(int userId, boolean newStatus) throws ServiceException {
        boolean result = false;
        try {
            Optional<User> optional = userDao.findById(userId);
            if (optional.isPresent()) {
                User user = optional.get();
                if (user.isActive() != newStatus) {
                    user.setActive(newStatus);
                    result = userDao.update(user);
                } else {
                    LOGGER.log(Level.DEBUG, "Such user status is already set");
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    private boolean checkSortingTag(String sortBy) {
        return sortBy.equals(ColumnName.USER_ID)
                || sortBy.equals(ColumnName.USER_ROLE)
                || sortBy.equals(ColumnName.USER_STATUS)
                || sortBy.equals(ColumnName.USER_LOGIN)
                || sortBy.equals(ColumnName.USER_EMAIL);
    }
}
