package com.drobot.webtest.model.validator;

import com.drobot.web.model.validator.UserValidator;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserValidatorTest {

    @Test
    public void isLoginValidTest() {
        UserValidator validator = new UserValidator();
        String login = ";1233,,,";
        boolean result = validator.isLoginValid(login);
        assertFalse(result);
    }
}
