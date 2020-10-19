package com.drobot.webtest.model.service;

import com.drobot.web.exception.ConnectionPoolException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.User;
import com.drobot.web.model.pool.ConnectionPool;
import com.drobot.web.model.service.UserService;
import com.drobot.web.model.service.impl.UserServiceImpl;
import com.drobot.web.model.util.Encrypter;
import org.mindrot.jbcrypt.BCrypt;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserServiceImplTest {

    @Test
    public void updatePasswordTest() throws ServiceException {
        UserService service = UserServiceImpl.INSTANCE;
        boolean actual = service.updatePassword(1, "admin");
        assertTrue(actual);
    }

    @Test
    public void addUser() {
        UserService userService = UserServiceImpl.INSTANCE;
        String login = "doctor";
        String password = "doctor";
        String email = "doc@test.by";
        User.Role role = User.Role.DOCTOR;
        boolean result = false;
        try {
            result = userService.add(login, email, password, role);
        } catch (ServiceException e) {
            fail();
        }
        assertTrue(result);
    }

    @AfterTest
    public void destroyPool() throws ConnectionPoolException {
        ConnectionPool.INSTANCE.destroyPool();
    }
}
