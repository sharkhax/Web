package com.drobot.webtest.model.creator;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.model.creator.Creator;
import com.drobot.web.model.creator.impl.UserCreator;
import com.drobot.web.model.entity.User;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserCreatorTest {

    private Creator<User> userCreator;

    @BeforeTest
    public void setUp() {
        userCreator = new UserCreator();
    }

    @Test
    public void createTest1() {
        Map<String, String> fields = new HashMap<>();
        String login = "Login";
        String email = "example@email.com";
        String password = "password";
        String position = RequestParameter.DOCTOR;
        fields.put(RequestParameter.LOGIN, login);
        fields.put(RequestParameter.EMAIL, email);
        fields.put(RequestParameter.PASSWORD, password);
        fields.put(RequestParameter.EMPLOYEE_POSITION, position);
        User expected = new User(login, email, User.Role.DOCTOR);
        Optional<User> actual = userCreator.create(fields);
        Assert.assertEquals(actual.orElse(new User()), expected);
    }

    @Test
    public void createTest2() {
        Map<String, String> fields = new HashMap<>();
        String login = "Login!";
        String email = "example@email.com";
        String password = "password";
        String position = RequestParameter.DOCTOR;
        fields.put(RequestParameter.LOGIN, login);
        fields.put(RequestParameter.EMAIL, email);
        fields.put(RequestParameter.PASSWORD, password);
        fields.put(RequestParameter.EMPLOYEE_POSITION, position);
        Optional<User> expected = Optional.empty();
        Optional<User> actual = userCreator.create(fields);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void createTest3() {
        Map<String, String> fields = new HashMap<>();
        String login = "Login";
        String email = "example.email.com";
        String password = "password";
        String position = RequestParameter.DOCTOR;
        fields.put(RequestParameter.LOGIN, login);
        fields.put(RequestParameter.EMAIL, email);
        fields.put(RequestParameter.PASSWORD, password);
        fields.put(RequestParameter.EMPLOYEE_POSITION, position);
        Optional<User> expected = Optional.empty();
        Optional<User> actual = userCreator.create(fields);
        Assert.assertEquals(actual, expected);
    }
}
