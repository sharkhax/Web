package com.drobot.webtest.model.dao;

import com.drobot.web.exception.DaoException;
import com.drobot.web.model.dao.UserEmployeeDao;
import com.drobot.web.model.dao.impl.UserEmployeeDaoImpl;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.model.entity.User;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserEmployeeDaoTest {

    private final UserEmployeeDao userEmployeeDao = new UserEmployeeDaoImpl();

    @Test
    public void registerTest() {
        User user = new User("login1" , "email@email1.com", User.Role.DOCTOR);
        Employee employee = new Employee("name1", "surname1", 23, 'F',
                Employee.Position.DOCTOR, 22222222222L);
        String password = "123456";
        try {
            boolean result = userEmployeeDao.register(user, password, employee);
            Assert.assertTrue(result);
        } catch (DaoException e) {
            Assert.fail(e.getMessage(), e);
        }
    }
}
