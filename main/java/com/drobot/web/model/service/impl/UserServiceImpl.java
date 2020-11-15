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
import com.drobot.web.model.util.Encrypter;
import com.drobot.web.model.validator.UserValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum UserServiceImpl implements UserService {

    INSTANCE;

    private final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private final UserDao userDao = UserDaoImpl.INSTANCE;

    @Override
    public boolean add(String login, String email, String password, User.Role role) throws ServiceException {
        boolean result = false;
        try {
            if (UserValidator.isLoginValid(login)
                    && UserValidator.isEmailValid(email)
                    && UserValidator.isPasswordValid(password)) {
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
    public List<User> findAll(String sortBy, boolean reverse) throws ServiceException {
        List<User> result;
        try {
            if (checkSortingTag(sortBy)) {
                result = userDao.findAll(sortBy, reverse);
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
    public List<User> findAll(int start, int end, String sortBy, boolean reverse) throws ServiceException {
        List<User> result;
        try {
            if (start >= 0 && end > 0) {
                if (checkSortingTag(sortBy)) {
                    result = userDao.findAll(start, end, sortBy, reverse);
                } else {
                    result = List.of();
                    LOGGER.log(Level.ERROR, "Unsupported sorting tag");
                }
            } else {
                result = List.of();
                LOGGER.log(Level.ERROR, "Incorrect start or end values");
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<User> findAll(int start, int end) throws ServiceException {
        return findAll(start, end, ColumnName.USER_ID, false);
    }


    @Override
    public Optional<User> findById(int userId) throws ServiceException {
        Optional<User> result;
        try {
            if (userId > 0) {
                result = userDao.findById(userId);
            } else {
                LOGGER.log(Level.ERROR, "Incorrect user id: " + userId);
                result = Optional.empty();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Optional<User> findByLogin(String login) throws ServiceException {
        Optional<User> result;
        if (UserValidator.isLoginValid(login)) {
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
        try {
            if (UserValidator.isEmailValid(email)) {
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
    public boolean existsLogin(String login) throws ServiceException {
        boolean result = false;
        try {
            if (UserValidator.isLoginValid(login)) {
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
        try {
            if (UserValidator.isEmailValid(email)) {
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
// FIXME: 10.11.2020
    /*@Override
    public boolean updateLogin(int userId, String newLogin) throws ServiceException {
        boolean result = false;
        try {
            if (UserValidator.isLoginValid(newLogin)) {
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
        try {
            if (UserValidator.isEmailValid(newEmail)) {
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
    public boolean updateStatus(int userId, Entity.Status newStatus) throws ServiceException {
        boolean result = false;
        try {
            Optional<User> optional = userDao.findById(userId);
            if (optional.isPresent()) {
                User user = optional.get();
                if (user.getStatus() != newStatus) {
                    user.setStatus(newStatus);
                    result = userDao.update(user);
                } else {
                    LOGGER.log(Level.DEBUG, "Such user status is already set");
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }*/

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
    public Map<String, String> packUserIntoMap(User user) {
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
