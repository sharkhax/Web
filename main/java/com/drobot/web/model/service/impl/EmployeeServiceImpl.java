package com.drobot.web.model.service.impl;

import com.drobot.web.exception.DaoException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.dao.ColumnName;
import com.drobot.web.model.dao.EmployeeDao;
import com.drobot.web.model.dao.impl.EmployeeDaoImpl;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.model.service.EmployeeService;
import com.drobot.web.model.validator.EmployeeValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public enum EmployeeServiceImpl implements EmployeeService {

    INSTANCE;

    private final Logger LOGGER = LogManager.getLogger(EmployeeServiceImpl.class);
    private final EmployeeDao employeeDao = EmployeeDaoImpl.INSTANCE;

    @Override
    public boolean add(Map<String, String> fields) throws ServiceException {
        return false;
    }

    @Override
    public boolean remove(int employeeId) throws ServiceException {
        return false;
    }

    @Override
    public boolean exists(String name, String surname) throws ServiceException {
        boolean result = false;
        try {
            if (EmployeeValidator.isNameValid(name) && EmployeeValidator.isNameValid(surname)) {
                result = employeeDao.exists(name, surname);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<Employee> findAll(String sortBy, boolean reverse) throws ServiceException {
        return null;
    }

    @Override
    public List<Employee> findAll(int start, int end, String sortBy, boolean reverse) throws ServiceException {
        List<Employee> result;
        try {
            if (start >= 0 && end > start) {
                if (checkSortingTag(sortBy)) {
                    result = employeeDao.findAll(start, end, sortBy, reverse);
                } else {
                    result = List.of();
                    LOGGER.log(Level.ERROR, "Incorrect sorting tag");
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
    public Optional<Employee> findById(int employeeId) throws ServiceException {
        Optional<Employee> result;
        try {
            if (employeeId > 0) {
                result = employeeDao.findById(employeeId);
            } else {
                result = Optional.empty();
                LOGGER.log(Level.ERROR, "Incorrect employee id: " + employeeId);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean update(int employeeId, Map<String, String> fields) throws ServiceException {
        return false;
    }

    @Override
    public int count() throws ServiceException {
        int result;
        try {
            EmployeeDao userDao = EmployeeDaoImpl.INSTANCE;
            result = userDao.count();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }


    private boolean checkSortingTag(String sortBy) {
        return sortBy.equals(ColumnName.EMPLOYEE_ID)
                || sortBy.equals(ColumnName.EMPLOYEE_NAME)
                || sortBy.equals(ColumnName.EMPLOYEE_SURNAME)
                || sortBy.equals(ColumnName.EMPLOYEE_AGE)
                || sortBy.equals(ColumnName.EMPLOYEE_GENDER)
                || sortBy.equals(ColumnName.EMPLOYEE_POSITION)
                || sortBy.equals(ColumnName.EMPLOYEE_HIRE_DATE)
                || sortBy.equals(ColumnName.EMPLOYEE_DISMISS_DATE)
                || sortBy.equals(ColumnName.EMPLOYEE_STATUS)
                || sortBy.equals(ColumnName.INTER_USER_ID);
    }
}
