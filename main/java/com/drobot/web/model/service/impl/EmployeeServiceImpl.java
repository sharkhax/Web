package com.drobot.web.model.service.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.exception.DaoException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.dao.ColumnName;
import com.drobot.web.model.dao.EmployeeDao;
import com.drobot.web.model.dao.UserDao;
import com.drobot.web.model.dao.UserEmployeeDao;
import com.drobot.web.model.dao.impl.EmployeeDaoImpl;
import com.drobot.web.model.dao.impl.UserDaoImpl;
import com.drobot.web.model.dao.impl.UserEmployeeDaoImpl;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.entity.User;
import com.drobot.web.model.service.EmployeeService;
import com.drobot.web.model.util.DateConverter;
import com.drobot.web.model.validator.EmployeeValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum EmployeeServiceImpl implements EmployeeService {

    INSTANCE;

    private final Logger LOGGER = LogManager.getLogger(EmployeeServiceImpl.class);
    private final EmployeeDao employeeDao = EmployeeDaoImpl.INSTANCE;

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
    public boolean exists(int employeeId) throws ServiceException {
        boolean result = false;
        try {
            if (employeeId > 0) {
                result = employeeDao.exists(employeeId);
            } else {
                LOGGER.log(Level.DEBUG, "Incorrect employee id value");
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
    public boolean update(Map<String, String> newFields, Map<String, String> existingFields,
                          Map<String, String> currentFields) throws ServiceException {
        boolean result = false;
        EmployeeMapService employeeMapService = EmployeeMapService.INSTANCE;
        int employeeId = Integer.parseInt(currentFields.get(RequestParameter.EMPLOYEE_ID));
        String name = null;
        String surname = null;
        int age = 0;
        char gender = ' ';
        Employee.Position position = null;
        long hireDate = 0;
        boolean isValid = true;
        boolean noMatches = true;
        boolean isNameChanged = false;
        try {
            if (newFields.containsKey(RequestParameter.EMPLOYEE_NAME)) {
                if (employeeMapService.checkName(newFields)) {
                    name = newFields.get(RequestParameter.EMPLOYEE_NAME);
                    isNameChanged = true;
                } else {
                    isValid = false;
                }
            } else {
                name = currentFields.get(RequestParameter.EMPLOYEE_NAME);
            }
            if (newFields.containsKey(RequestParameter.EMPLOYEE_SURNAME)) {
                if (employeeMapService.checkSurname(newFields)) {
                    surname = newFields.get(RequestParameter.EMPLOYEE_SURNAME);
                    isNameChanged = true;
                } else {
                    isValid = false;
                }
            } else {
                surname = currentFields.get(RequestParameter.EMPLOYEE_SURNAME);
            }
            if (isNameChanged && employeeDao.exists(name, surname)) {
                noMatches = false;
                existingFields.put(RequestParameter.EMPLOYEE_NAME, name);
                existingFields.put(RequestParameter.EMPLOYEE_SURNAME, surname);
                name = null;
                surname = null;
            }
            if (newFields.containsKey(RequestParameter.EMPLOYEE_AGE)) {
                if (employeeMapService.checkAge(newFields)) {
                    age = Integer.parseInt(newFields.get(RequestParameter.EMPLOYEE_AGE));
                } else {
                    isValid = false;
                }
            } else {
                age = Integer.parseInt(currentFields.get(RequestParameter.EMPLOYEE_AGE));
            }
            if (newFields.containsKey(RequestParameter.EMPLOYEE_GENDER)) {
                if (employeeMapService.checkGender(newFields)) {
                    gender = newFields.get(RequestParameter.EMPLOYEE_GENDER).charAt(0);
                } else {
                    isValid = false;
                }
            } else {
                gender = currentFields.get(RequestParameter.EMPLOYEE_GENDER).charAt(0);
            }
            if (newFields.containsKey(RequestParameter.EMPLOYEE_POSITION)) {
                if (employeeMapService.checkPosition(newFields)) {
                    position = Employee.Position.valueOf(newFields.get(RequestParameter.EMPLOYEE_POSITION));
                } else {
                    isValid = false;
                }
            } else {
                position = Employee.Position.valueOf(currentFields.get(RequestParameter.EMPLOYEE_POSITION));
            }
            if (newFields.containsKey(RequestParameter.HIRE_DATE)) {
                if (employeeMapService.checkHireDate(newFields)) {
                    String stringDate = newFields.get(RequestParameter.HIRE_DATE);
                    hireDate = DateConverter.localDateToMillis(LocalDate.parse(stringDate));
                } else {
                    isValid = false;
                }
            } else {
                String stringDate = currentFields.get(RequestParameter.HIRE_DATE);
                hireDate = DateConverter.localDateToMillis(LocalDate.parse(stringDate));
            }
            if (isValid && noMatches) {
                UserEmployeeDao userEmployeeDao = UserEmployeeDaoImpl.INSTANCE;
                Employee.Builder builder = new Employee.Builder(new Employee());
                int userId = Integer.parseInt(currentFields.get(RequestParameter.USER_ID));
                builder.buildId(employeeId)
                        .buildName(name)
                        .buildSurname(surname)
                        .buildAge(age)
                        .buildGender(gender)
                        .buildPosition(position)
                        .buildHireDateMillis(hireDate)
                        .buildUserId(userId);
                User.Role role;
                switch (position) {
                    case ASSISTANT -> role = User.Role.ASSISTANT;
                    case DOCTOR -> role = User.Role.DOCTOR;
                    default -> throw new EnumConstantNotPresentException(Employee.Position.class, position.name());
                }
                result = userEmployeeDao.updateEmployee(builder.getEmployee(), role);
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
            EmployeeDao userDao = EmployeeDaoImpl.INSTANCE;
            result = userDao.count();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Map<String, String> packEmployeeIntoMap(Employee employee) {
        Map<String, String> result = new HashMap<>();
        if (employee != null) {
            int employeeId = employee.getId();
            String stringEmployeeId = employeeId != 0 ? String.valueOf(employeeId) : "";
            result.put(RequestParameter.EMPLOYEE_ID, stringEmployeeId);
            result.put(RequestParameter.EMPLOYEE_NAME, employee.getName());
            result.put(RequestParameter.EMPLOYEE_SURNAME, employee.getSurname());
            result.put(RequestParameter.EMPLOYEE_AGE, String.valueOf(employee.getAge()));
            result.put(RequestParameter.EMPLOYEE_GENDER, String.valueOf(employee.getGender()));
            result.put(RequestParameter.EMPLOYEE_POSITION, employee.getPosition().toString());
            long hireDateMillis = employee.getHireDateMillis();
            String hireDate = DateConverter.millisToLocalDate(hireDateMillis).toString();
            long dismissDateMillis = employee.getDismissDateMillis();
            String dismissDate;
            if (dismissDateMillis == 0L) {
                dismissDate = "-";
            } else {
                dismissDate = DateConverter.millisToLocalDate(dismissDateMillis).toString();
            }
            result.put(RequestParameter.HIRE_DATE, hireDate);
            result.put(RequestParameter.DISMISS_DATE, dismissDate);
            result.put(RequestParameter.EMPLOYEE_STATUS, employee.getStatus().toString());
            result.put(RequestParameter.USER_ID, String.valueOf(employee.getUserId()));
        }
        return result;
    }

    @Override
    public Optional<Entity.Status> findStatus(int employeeId) throws ServiceException {
        Optional<Entity.Status> result;
        try {
            if (employeeId > 0) {
                result = employeeDao.findStatus(employeeId);
            } else {
                LOGGER.log(Level.DEBUG, "Employee id " + employeeId + " is not valid");
                result = Optional.empty();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean fireEmployee(int employeeId, int userId) throws ServiceException {
        boolean result = false;
        try {
            if (employeeId > 0 && userId > 0) {
                Optional<Entity.Status> optionalStatus = findStatus(employeeId);
                if (optionalStatus.isPresent()) {
                    Entity.Status currentEmployeeStatus = optionalStatus.get();
                    if (currentEmployeeStatus != Entity.Status.ARCHIVE) {
                        UserDao userDao = UserDaoImpl.INSTANCE;
                        Entity.Status newEmployeeStatus = Entity.Status.ARCHIVE;
                        Optional<Entity.Status> optionalUserStatus = userDao.findStatus(userId);
                        Entity.Status userStatus = optionalUserStatus.orElseThrow();
                        if (userStatus != Entity.Status.UNREMOVABLE) {
                            UserEmployeeDao userEmployeeDao = UserEmployeeDaoImpl.INSTANCE;
                            Entity.Status newUserStatus = Entity.Status.BLOCKED;
                            if (userEmployeeDao.updateStatuses(employeeId, userId, newEmployeeStatus, newUserStatus)) {
                                setInstantDismissDate(employeeId);
                                result = true;
                            } else {
                                LOGGER.log(Level.ERROR, "Statuses haven't been set");
                            }
                        } else {
                            LOGGER.log(Level.INFO, "User's status is UNREMOVABLE, " +
                                    "employee will have been fired w/t changing user's status");
                            if (employeeDao.updateStatus(employeeId, newEmployeeStatus)) {
                                setInstantDismissDate(employeeId);
                                result = true;
                            } else {
                                LOGGER.log(Level.ERROR, "Employee status haven't been set");
                            }
                        }
                    } else {
                        LOGGER.log(Level.ERROR, "Employee's current status is ARCHIVE, cannot be fired");
                    }
                } else {
                    LOGGER.log(Level.ERROR, "Employee hasn't been found");
                }
            } else {
                LOGGER.log(Level.DEBUG, "Employee or user ids are not valid");
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean restoreEmployee(int employeeId) throws ServiceException {
        boolean result = false;
        try {
            if (employeeId > 0) {
                Entity.Status newStatus = Entity.Status.ACTIVE;
                if (employeeDao.updateStatus(employeeId, newStatus)) {
                    employeeDao.setDismissDate(employeeId, 0);
                    result = true;
                } else {
                    LOGGER.log(Level.ERROR, "Employee status haven't been set");
                }
            } else {
                LOGGER.log(Level.DEBUG, "Employee id " + employeeId + " are not valid");
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean sendToVacation(int employeeId) throws ServiceException {
        boolean result;
        try {
            result = employeeDao.updateStatus(employeeId, Entity.Status.VACATION);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean returnFromVacation(int employeeId) throws ServiceException {
        boolean result;
        try {
            result = employeeDao.updateStatus(employeeId, Entity.Status.ACTIVE);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Optional<Employee> findByUserId(int userId) throws ServiceException {
        Optional<Employee> result;
        try {
            if (userId > 0) {
                result = employeeDao.findByUserId(userId);
            } else {
                LOGGER.log(Level.DEBUG, "Invalid user id value");
                result = Optional.empty();
            }
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

    private boolean setInstantDismissDate(int employeeId) throws DaoException {
        boolean result = true;
        long dismissDateMillis = Instant.now().toEpochMilli();
        if (!employeeDao.setDismissDate(employeeId, dismissDateMillis)) {
            result = false;
            LOGGER.log(Level.ERROR, "Dismiss date hasn't been set");
        }
        return result;
    }
}
