package com.drobot.web.model.service.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.exception.DaoException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.creator.Creator;
import com.drobot.web.model.creator.impl.EmployeeCreator;
import com.drobot.web.model.creator.impl.UserCreator;
import com.drobot.web.model.dao.EmployeeDao;
import com.drobot.web.model.dao.UserDao;
import com.drobot.web.model.dao.impl.EmployeeDaoImpl;
import com.drobot.web.model.dao.impl.UserDaoImpl;
import com.drobot.web.model.dao.impl.UserEmployeeDaoImpl;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.model.entity.User;
import com.drobot.web.model.service.UserEmployeeService;
import com.drobot.web.util.DateConverter;
import com.drobot.web.util.Encrypter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * UserEmployeeService implementation.
 *
 * @author Vladislav Drobot
 */
public enum UserEmployeeServiceImpl implements UserEmployeeService {

    /**
     * Represents a singleton pattern realization.
     */
    INSTANCE;

    @Override
    public boolean register(Map<String, String> fields, Map<String, String> existingFields)
            throws ServiceException {
        boolean result = false;
        Creator<User> userCreator = new UserCreator();
        Creator<Employee> employeeCreator = new EmployeeCreator();
        Optional<User> optionalUser = userCreator.create(fields);
        Optional<Employee> optionalEmployee = employeeCreator.create(fields);
        if (optionalEmployee.isPresent() && optionalUser.isPresent()) {
            User user = optionalUser.get();
            Employee employee = optionalEmployee.get();
            String password = fields.get(RequestParameter.PASSWORD);
            String encPassword = Encrypter.encrypt(password);
            String login = user.getLogin();
            String email = user.getEmail();
            String name = employee.getName();
            String surname = employee.getSurname();
            boolean noMatches = true;
            try {
                UserDao userDao = UserDaoImpl.INSTANCE;
                if (userDao.existsLogin(login)) {
                    existingFields.put(RequestParameter.LOGIN, login);
                    noMatches = false;
                }
                if (userDao.existsEmail(email)) {
                    existingFields.put(RequestParameter.EMAIL, email);
                    noMatches = false;
                }
                EmployeeDao employeeDao = EmployeeDaoImpl.INSTANCE;
                if (employeeDao.exists(name, surname)) {
                    existingFields.put(RequestParameter.EMPLOYEE_NAME, name);
                    existingFields.put(RequestParameter.EMPLOYEE_SURNAME, surname);
                    noMatches = false;
                }
                if (noMatches) {
                    UserEmployeeDaoImpl userEmployeeDao = UserEmployeeDaoImpl.INSTANCE;
                    result = userEmployeeDao.register(user, encPassword, employee);
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return result;
    }

    @Override
    public Map<String, String> loadUserData(int userId) throws ServiceException {
        Map<String, String> fields = new HashMap<>();
        User user = new User();
        Employee employee = new Employee();
        try {
            UserEmployeeDaoImpl userEmployeeDao = UserEmployeeDaoImpl.INSTANCE;
            if (userEmployeeDao.loadUserEmployeeData(userId, user, employee)) {
                String login = user.getLogin();
                String email = user.getEmail();
                String role = user.getRole().toString();
                String name = employee.getName();
                String surname = employee.getSurname();
                String age = String.valueOf(employee.getAge());
                String gender = String.valueOf(employee.getGender());
                String position = employee.getPosition().toString();
                long hireDateMillis = employee.getHireDateMillis();
                long hireDateDays = DateConverter.millisToDays(hireDateMillis);
                String hireDate = LocalDate.ofEpochDay(hireDateDays).toString();
                String status = employee.getStatus().toString();
                fields.put(RequestParameter.LOGIN, login);
                fields.put(RequestParameter.EMAIL, email);
                fields.put(RequestParameter.USER_ROLE, role);
                fields.put(RequestParameter.EMPLOYEE_NAME, name);
                fields.put(RequestParameter.EMPLOYEE_SURNAME, surname);
                fields.put(RequestParameter.EMPLOYEE_AGE, age);
                fields.put(RequestParameter.EMPLOYEE_GENDER, gender);
                fields.put(RequestParameter.EMPLOYEE_POSITION, position);
                fields.put(RequestParameter.HIRE_DATE, hireDate);
                fields.put(RequestParameter.EMPLOYEE_STATUS, status);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return fields;
    }
}
