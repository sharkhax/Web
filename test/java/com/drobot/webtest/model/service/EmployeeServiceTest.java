package com.drobot.webtest.model.service;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.exception.ConnectionPoolException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.pool.ConnectionPool;
import com.drobot.web.model.service.EmployeeService;
import com.drobot.web.model.service.impl.EmployeeServiceImpl;
import com.drobot.web.util.DateConverter;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EmployeeServiceTest {

    private EmployeeService employeeService;

    @BeforeTest
    public void setUp() {
        employeeService = EmployeeServiceImpl.INSTANCE;
    }

    @Test
    public void findByIdTest1() {
        int id = 9;
        Employee employee = new Employee(id, "Doc", "Tor", 41, 'F',
                Employee.Position.DOCTOR, 1599609600000L, 0, Entity.Status.ACTIVE, 8);
        Optional<Employee> expected = Optional.of(employee);
        Optional<Employee> actual = Optional.empty();
        try {
            actual = employeeService.findById(id);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void findByIdTest2() {
        int id = 0;
        Optional<Employee> expected = Optional.empty();
        Optional<Employee> actual = Optional.empty();
        try {
            actual = employeeService.findById(id);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void existsTest1() {
        int id = 9;
        try {
            Assert.assertTrue(employeeService.exists(id));
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
    }

    @Test
    public void existsTest2() {
        int id = 0;
        try {
            Assert.assertFalse(employeeService.exists(id));
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
    }

    @Test
    public void packIntoMapTest1() {
        String id = "9";
        String name = "Doc";
        String surname = "Tor";
        String age = "41";
        String gender = "F";
        Employee.Position position = Employee.Position.DOCTOR;
        String hireDate = DateConverter.millisToLocalDate(1599609600000L).toString();
        Entity.Status status = Entity.Status.ACTIVE;
        String userId = "8";
        Employee employee = new Employee(9, name, surname, 41, 'F',
                position, 1599609600000L, 0, status, 8);
        Map<String, String> expected = new HashMap<>();
        expected.put(RequestParameter.EMPLOYEE_ID, id);
        expected.put(RequestParameter.EMPLOYEE_NAME, name);
        expected.put(RequestParameter.EMPLOYEE_SURNAME, surname);
        expected.put(RequestParameter.EMPLOYEE_AGE, age);
        expected.put(RequestParameter.EMPLOYEE_GENDER, gender);
        expected.put(RequestParameter.EMPLOYEE_POSITION, position.toString());
        expected.put(RequestParameter.HIRE_DATE, hireDate);
        expected.put(RequestParameter.DISMISS_DATE, "-");
        expected.put(RequestParameter.EMPLOYEE_STATUS, status.toString());
        expected.put(RequestParameter.USER_ID, userId);
        Map<String, String> actual = employeeService.packIntoMap(employee);
        Assert.assertEquals(actual, expected);
    }

    @AfterTest
    public void destroy() throws ConnectionPoolException {
        ConnectionPool.INSTANCE.destroyPool();
    }
}
