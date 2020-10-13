package com.drobot.web.model.service.impl;

import com.drobot.web.exception.DaoException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.dao.ColumnName;
import com.drobot.web.model.dao.EmployeeDao;
import com.drobot.web.model.dao.UserDao;
import com.drobot.web.model.dao.impl.EmployeeDaoImpl;
import com.drobot.web.model.dao.impl.UserDaoImpl;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.service.EmployeeMapService;
import com.drobot.web.model.service.EmployeeService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LogManager.getLogger(EmployeeServiceImpl.class);
    private final EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public boolean add(Map<String, String> fields) throws ServiceException {
        boolean result = false;
        try {
            if (checkFieldsToAdd(fields)) {
                String stringUserAccountId = fields.get(ColumnName.EMPLOYEE_ACCOUNT_ID);
                int userAccountId = Integer.parseInt(stringUserAccountId);
                UserDao userDao = new UserDaoImpl();
                if (userDao.exists(userAccountId)) {
                    String name = fields.get(ColumnName.EMPLOYEE_NAME);
                    String surname = fields.get(ColumnName.EMPLOYEE_SURNAME);
                    String stringAge = fields.get(ColumnName.EMPLOYEE_AGE);
                    int age = Integer.parseInt(stringAge);
                    String stringGender = fields.get(ColumnName.EMPLOYEE_GENDER);
                    char gender = stringGender.charAt(0);
                    String stringPosition = fields.get(ColumnName.EMPLOYEE_POSITION);
                    Employee.Position position = Employee.Position.valueOf(stringPosition);
                    String stringHireDate = fields.get(ColumnName.EMPLOYEE_HIRE_DATE);
                    long hireDateMillis = Long.parseLong(stringHireDate);
                    Employee employee =
                            new Employee(name, surname, age, gender, position, hireDateMillis, userAccountId);
                    result = employeeDao.add(employee);
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean remove(int employeeId) throws ServiceException {
        boolean result;
        try {
            result = employeeDao.remove(employeeId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<Employee> findAll(String sortBy) throws ServiceException {
        List<Employee> result;
        try {
            if (checkSortingTag(sortBy)) {
                result = employeeDao.findAll(sortBy);
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
    public Optional<Employee> findById(int employeeId) throws ServiceException {
        Optional<Employee> result;
        try {
            result = employeeDao.findById(employeeId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean update(int employeeId, Map<String, String> fields) throws ServiceException {
        boolean result = false;
        try {
            if (checkFieldsToUpdate(fields)) {
                String name = fields.get(ColumnName.EMPLOYEE_NAME);
                String surname = fields.get(ColumnName.EMPLOYEE_SURNAME);
                String stringAge = fields.get(ColumnName.EMPLOYEE_AGE);
                int age = Integer.parseInt(stringAge);
                String stringGender = fields.get(ColumnName.EMPLOYEE_GENDER);
                char gender = stringGender.charAt(0);
                String stringPosition = fields.get(ColumnName.EMPLOYEE_POSITION);
                Employee.Position position = Employee.Position.valueOf(stringPosition);
                String stringHireDate = fields.get(ColumnName.EMPLOYEE_HIRE_DATE);
                long hireDateMillis = Long.parseLong(stringHireDate);
                String stringDismissDate = fields.get(ColumnName.EMPLOYEE_DISMISS_DATE);
                long dismissDateMillis = Long.parseLong(stringDismissDate);
                String stringStatus = fields.get(ColumnName.EMPLOYEE_STATUS);
                int statusId = Integer.parseInt(stringStatus);
                Entity.Status status = Entity.Status.defineStatus(statusId);
                String stringAccountId = fields.get(ColumnName.EMPLOYEE_ACCOUNT_ID);
                int accountId = Integer.parseInt(stringAccountId);
                Employee employee = new Employee(name, surname, age, gender, position,
                        hireDateMillis, dismissDateMillis, status, accountId);
                result = employeeDao.update(employee);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    private boolean checkFieldsToAdd(Map<String, String> fields) {
        EmployeeMapService mapService = EmployeeMapService.INSTANCE;
        boolean result = mapService.checkName(fields) & mapService.checkSurname(fields)
                & mapService.checkAge(fields) & mapService.checkGender(fields)
                & mapService.checkHireDate(fields) & mapService.checkPosition(fields)
                & mapService.checkAccountId(fields);
        return result;
    }

    private boolean checkFieldsToUpdate(Map<String, String> fields) {
        EmployeeMapService mapService = EmployeeMapService.INSTANCE;
        boolean result = mapService.checkName(fields) & mapService.checkSurname(fields)
                & mapService.checkAge(fields) & mapService.checkGender(fields)
                & mapService.checkPosition(fields) & mapService.checkHireDate(fields)
                & mapService.checkDismissDate(fields) & mapService.checkStatus(fields)
                & mapService.checkAccountId(fields);
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
                || sortBy.equals(ColumnName.EMPLOYEE_ACCOUNT_ID);
    }
}
