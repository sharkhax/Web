package com.drobot.web.model.service.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.exception.DaoException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.dao.impl.UserEmployeeDaoImpl;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.model.entity.User;
import com.drobot.web.model.service.EmployeeService;
import com.drobot.web.model.service.UserEmployeeService;
import com.drobot.web.model.service.UserService;
import com.drobot.web.model.util.DateConverter;
import com.drobot.web.model.util.Encrypter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum UserEmployeeServiceImpl implements UserEmployeeService {

    INSTANCE;

    private final double SECS_TO_DAYS_MULTIPLIER = 1./(3600*24);
    private final UserEmployeeDaoImpl userEmployeeDao = new UserEmployeeDaoImpl();
    private final UserService userService = UserServiceImpl.INSTANCE;
    private final EmployeeService employeeService = EmployeeServiceImpl.INSTANCE;

    @Override
    public boolean register(Map<String, String> fields, Map<String, String> existingFields)
            throws ServiceException {
        boolean result = false;
        Optional<User> optionalUser = userService.create(fields);
        Optional<Employee> optionalEmployee = employeeService.create(fields);
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
            if (userService.existsLogin(login)) {
                existingFields.put(RequestParameter.LOGIN, login);
                noMatches = false;
            }
            if (userService.existsEmail(email)) {
                existingFields.put(RequestParameter.EMAIL, email);
                noMatches = false;
            }
            if (employeeService.exists(name, surname)) {
                existingFields.put(RequestParameter.NAME, name);
                existingFields.put(RequestParameter.SURNAME, surname);
                noMatches = false;
            }
            if (noMatches) {
                try {
                    result = userEmployeeDao.register(user, encPassword, employee);
                } catch (DaoException e) {
                    throw new ServiceException(e);
                }
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
                fields.put(RequestParameter.NAME, name);
                fields.put(RequestParameter.SURNAME, surname);
                fields.put(RequestParameter.AGE, age);
                fields.put(RequestParameter.GENDER, gender);
                fields.put(RequestParameter.POSITION, position);
                fields.put(RequestParameter.HIRE_DATE, hireDate);
                fields.put(RequestParameter.EMPLOYEE_STATUS, status);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return fields;
    }
}
