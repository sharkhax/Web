package com.drobot.web.model.service.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.exception.DaoException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.dao.ColumnName;
import com.drobot.web.model.dao.EmployeeDao;
import com.drobot.web.model.dao.impl.EmployeeDaoImpl;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.model.service.EmployeeMapService;
import com.drobot.web.model.service.EmployeeService;
import com.drobot.web.model.util.DateConverter;
import com.drobot.web.model.validator.EmployeeValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public enum EmployeeServiceImpl implements EmployeeService {

    INSTANCE;

    private final Logger LOGGER = LogManager.getLogger(EmployeeServiceImpl.class);
    private final EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public Optional<Employee> create(Map<String, String> fields) {
        Optional<Employee> result;
        if (checkFieldsToCreate(fields)) {
            String name = fields.get(RequestParameter.NAME);
            String surname = fields.get(RequestParameter.SURNAME);
            String stringAge = fields.get(RequestParameter.AGE);
            int age = Integer.parseInt(stringAge);
            String stringGender = fields.get(RequestParameter.GENDER);
            char gender = stringGender.charAt(0);
            String stringPosition = fields.get(RequestParameter.POSITION);
            Employee.Position position = Employee.Position.valueOf(stringPosition.toUpperCase());
            String stringDate = fields.get(RequestParameter.HIRE_DATE);
            long hireDateDays = LocalDate.parse(stringDate).getLong(ChronoField.EPOCH_DAY);
            long hireDateMillis = DateConverter.daysToMillis(hireDateDays);
            Employee employee = new Employee(name, surname, age, gender, position, hireDateMillis);
            result = Optional.of(employee);
        } else {
            result = Optional.empty();
            LOGGER.log(Level.DEBUG, "Some fields are invalid or absent");
        }
        return result;
    }

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
    public Optional<Employee> findById(int employeeId) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public boolean update(int employeeId, Map<String, String> fields) throws ServiceException {
        return false;
    }

    private boolean checkFieldsToCreate(Map<String, String> fields) {
        EmployeeMapService mapService = EmployeeMapService.INSTANCE;
        boolean result = mapService.checkName(fields) & mapService.checkSurname(fields)
                & mapService.checkAge(fields) & mapService.checkGender(fields)
                & mapService.checkHireDate(fields) & mapService.checkPosition(fields);
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
                || sortBy.equals(ColumnName.EMPLOYEE_STATUS);
    }
}
