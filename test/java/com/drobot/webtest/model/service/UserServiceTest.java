package com.drobot.webtest.model.service;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.exception.ConnectionPoolException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.entity.User;
import com.drobot.web.model.pool.ConnectionPool;
import com.drobot.web.model.service.UserService;
import com.drobot.web.model.service.impl.UserServiceImpl;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserServiceTest {

    private UserService userService;

    @BeforeTest
    public void setUp() {
        userService = UserServiceImpl.INSTANCE;
    }

    @Test
    public void signInTest1() {
        String login = "admin";
        String password = "admin";
        User user = new User(1, User.Role.ADMIN, Entity.Status.UNREMOVABLE);
        Optional<User> expected = Optional.of(user);
        Optional<User> actual = Optional.empty();
        try {
            actual = userService.signIn(login, password);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void signInTest2() {
        String login = "admin";
        String password = "admin.";
        Optional<User> expected = Optional.empty();
        Optional<User> actual = Optional.empty();
        try {
            actual = userService.signIn(login, password);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void findByIdTest1() {
        int id = 1;
        User user = new User(1, "admin", "vladislav.drobot@mail.ru",
                User.Role.ADMIN, Entity.Status.UNREMOVABLE, 12);
        Optional<User> expected = Optional.of(user);
        Optional<User> actual = Optional.empty();
        try {
            actual = userService.findById(id);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void findByIdTest2() {
        int id = 0;
        Optional<User> expected = Optional.empty();
        Optional<User> actual = Optional.empty();
        try {
            actual = userService.findById(id);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void existsTest1() {
        int id = 1;
        try {
            Assert.assertTrue(userService.exists(id));
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
    }

    @Test
    public void existsTest2() {
        int id = 0;
        try {
            Assert.assertFalse(userService.exists(id));
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
    }

    @Test
    public void packIntoMapTest1() {
        String id = "1";
        String login = "admin";
        String email = "vladislav.drobot@mail.ru";
        User.Role role = User.Role.ADMIN;
        Entity.Status status = Entity.Status.UNREMOVABLE;
        String employeeId = "12";
        User user = new User(1, login, email, role, status, 12);
        Map<String, String> expected = new HashMap<>();
        expected.put(RequestParameter.USER_ID, id);
        expected.put(RequestParameter.LOGIN, login);
        expected.put(RequestParameter.EMAIL, email);
        expected.put(RequestParameter.USER_ROLE, role.toString());
        expected.put(RequestParameter.USER_STATUS, status.toString());
        expected.put(RequestParameter.EMPLOYEE_ID, employeeId);
        Map<String, String> actual = userService.packIntoMap(user);
        Assert.assertEquals(actual, expected);
    }

    @AfterTest
    public void destroy() throws ConnectionPoolException {
        ConnectionPool.INSTANCE.destroyPool();
    }
}
