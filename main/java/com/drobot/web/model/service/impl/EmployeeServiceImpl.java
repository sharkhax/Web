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
    public List<Employee> findAll(String sortBy) throws ServiceException {
        return null;
    }

    @Override
    public List<Employee> findAll(int start, int length, String sortBy) throws ServiceException {
        List<Employee> result;
        try {
            if (start >= 0 && length > 0) {
                if (checkSortingTag(sortBy)) {
                    result = employeeDao.findAll(start, length, sortBy);
                } else {
                    result = List.of();
                    LOGGER.log(Level.ERROR, "Incorrect sorting tag");
                }
            } else {
                result = List.of();
                LOGGER.log(Level.ERROR, "Incorrect start or length values");
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }


    @Override
    public Optional<Employee> findById(int employeeId) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public boolean update(int employeeId, Map<String, String> fields) throws ServiceException {
        return false;
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
                || sortBy.equals(ColumnName.EMPLOYEE_STATUS);
    }
}
