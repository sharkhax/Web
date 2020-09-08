package com.drobot.webtest.model.service;

import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.service.UserService;
import com.drobot.web.model.service.impl.UserServiceImpl;
import com.drobot.web.model.util.Encrypter;
import org.mindrot.jbcrypt.BCrypt;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class UserServiceImplTest {

    @Test
    public void updatePasswordTest() throws ServiceException {
        UserService service = new UserServiceImpl();
        boolean actual = service.updatePassword(1, "admin");
        assertTrue(actual);
    }
}
