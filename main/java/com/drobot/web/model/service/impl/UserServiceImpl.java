package com.drobot.web.model.service.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.exception.DaoException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.dao.ColumnName;
import com.drobot.web.model.dao.EmployeeDao;
import com.drobot.web.model.dao.UserDao;
import com.drobot.web.model.dao.impl.EmployeeDaoImpl;
import com.drobot.web.model.dao.impl.UserDaoImpl;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.entity.User;
import com.drobot.web.model.service.UserService;
import com.drobot.web.util.Encrypter;
import com.drobot.web.model.validator.UserValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * UserService implementation.
 *
 * @author Vladislav Drobot
 */
public enum UserServiceImpl implements UserService {

    /**
     * Represents a singleton pattern realization.
     */
    INSTANCE;

    private final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private final UserDao userDao = UserDaoImpl.INSTANCE;

    @Override
    public List<User> findAll(int start, int end, String sortBy, boolean reverse) throws ServiceException {
        List<User> result;
        try {
            if (start >= 0 && end > 0) {
                if (checkSortingTag(sortBy)) {
                    result = userDao.findAll(start, end, sortBy, reverse);
                } else {
                    result = List.of();
                    LOGGER.log(Level.DEBUG, "Unsupported sorting tag");
                }
            } else {
                result = List.of();
                LOGGER.log(Level.DEBUG, "Incorrect start or end values");
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Optional<User> findById(int userId) throws ServiceException {
        Optional<User> result;
        try {
            if (userId > 0) {
                result = userDao.findById(userId);
            } else {
                LOGGER.log(Level.DEBUG, "Incorrect user id: " + userId);
                result = Optional.empty();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean exists(int userId) throws ServiceException {
        boolean result = false;
        try {
            if (userId > 0) {
                result = userDao.exists(userId);
            } else {
                LOGGER.log(Level.DEBUG, "User id is not valid");
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Optional<User> signIn(String login, String password) throws ServiceException {
        Optional<User> result;
        try {
            if (UserValidator.isLoginValid(login)
                    && UserValidator.isPasswordValid(password)) {
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
    public boolean updatePassword(int userId, String newPassword) throws ServiceException {
        boolean result = false;
        try {
            if (UserValidator.isPasswordValid(newPassword)) {
                if (userDao.exists(userId)) {
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
    public int count() throws ServiceException {
        int result;
        try {
            UserDao userDao = UserDaoImpl.INSTANCE;
            result = userDao.count();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Map<String, String> packIntoMap(User user) {
        Map<String, String> result = new HashMap<>();
        if (user != null) {
            int userId = user.getId();
            String stringUserId = userId != 0 ? String.valueOf(userId) : "";
            result.put(RequestParameter.USER_ID, stringUserId);
            result.put(RequestParameter.LOGIN, user.getLogin());
            result.put(RequestParameter.EMAIL, user.getEmail());
            result.put(RequestParameter.USER_STATUS, user.getStatus().toString());
            result.put(RequestParameter.USER_ROLE, user.getRole().toString());
            int employeeId = user.getEmployeeId();
            String stringEmployeeId = employeeId != 0 ? String.valueOf(employeeId) : "";
            result.put(RequestParameter.EMPLOYEE_ID, stringEmployeeId);
        }
        return result;
    }

    @Override
    public boolean update(Map<String, String> newFields, Map<String, String> existingFields,
                          Map<String, String> currentFields) throws ServiceException {
        boolean result = false;
        UserMapService mapService = UserMapService.INSTANCE;
        int userId = Integer.parseInt(currentFields.get(RequestParameter.USER_ID));
        String login = null;
        String email = null;
        boolean isValid = true;
        boolean noMatches = true;
        try {
            if (newFields.containsKey(RequestParameter.LOGIN)) {
                if (mapService.checkLogin(newFields)) {
                    login = newFields.get(RequestParameter.LOGIN);
                    if (userDao.existsLogin(login)) {
                        existingFields.put(RequestParameter.LOGIN, login);
                        noMatches = false;
                    }
                } else {
                    isValid = false;
                }
            } else {
                login = currentFields.get(RequestParameter.LOGIN);
            }
            if (newFields.containsKey(RequestParameter.EMAIL)) {
                if (mapService.checkEmail(newFields)) {
                    email = newFields.get(RequestParameter.EMAIL);
                    if (userDao.existsEmail(email)) {
                        existingFields.put(RequestParameter.EMAIL, email);
                        noMatches = false;
                    }
                } else {
                    isValid = false;
                }
            } else {
                email = currentFields.get(RequestParameter.EMAIL);
            }
            if (isValid && noMatches) {
                result = userDao.update(userId, login, email);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean blockUser(int userId) throws ServiceException {
        boolean result;
        try {
            result = userDao.updateStatus(userId, Entity.Status.BLOCKED);
            if (result) {
                LOGGER.log(Level.DEBUG, "User " + userId + " has been blocked");
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean unblockUser(int userId, int employeeId) throws ServiceException {
        boolean result = false;
        EmployeeDao employeeDao = EmployeeDaoImpl.INSTANCE;
        try {
            Optional<Entity.Status> optionalStatus = employeeDao.findStatus(employeeId);
            if (optionalStatus.isPresent()) {
                Entity.Status employeeStatus = optionalStatus.get();
                if (employeeStatus != Entity.Status.ARCHIVE) {
                    result = userDao.updateStatus(userId, Entity.Status.ACTIVE);
                    LOGGER.log(Level.DEBUG, "User " + userId + " has been unblocked");
                } else {
                    LOGGER.log(Level.DEBUG, "Employee's status is ARCHIVE, user cannot be unblocked");
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Optional<Entity.Status> findStatus(int userId) throws ServiceException {
        Optional<Entity.Status> result;
        try {
            if (userId > 0) {
                result = userDao.findStatus(userId);
            } else {
                result = Optional.empty();
                LOGGER.log(Level.DEBUG, "Incorrect user id value");
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    private boolean checkSortingTag(String sortBy) {
        boolean result = false;
        if (sortBy != null) {
            result = sortBy.equals(ColumnName.USER_ID)
                    || sortBy.equals(ColumnName.USER_ROLE)
                    || sortBy.equals(ColumnName.USER_STATUS)
                    || sortBy.equals(ColumnName.USER_LOGIN)
                    || sortBy.equals(ColumnName.USER_EMAIL)
                    || sortBy.equals(ColumnName.INTER_EMPLOYEE_ID);
        } else {
            LOGGER.log(Level.DEBUG, "Sorting tag is null");
        }
        return result;
    }
}
