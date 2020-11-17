package com.drobot.webtest.model.creator;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.model.creator.Creator;
import com.drobot.web.model.creator.impl.EmployeeCreator;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.util.DateConverter;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EmployeeCreatorTest {

    private Creator<Employee> employeeCreator;

    @BeforeTest
    public void setUp() {
        employeeCreator = new EmployeeCreator();
    }

    @Test
    public void createTest1() {
        Map<String, String> fields = new HashMap<>();
        String name = "Name";
        String surname = "Surname";
        String age = "19";
        String gender = "F";
        String position = RequestParameter.DOCTOR;
        long hireDate = DateConverter.localDateToMillis(LocalDate.now());
        String stringHireDate = LocalDate.now().toString();
        fields.put(RequestParameter.EMPLOYEE_NAME, name);
        fields.put(RequestParameter.EMPLOYEE_SURNAME, surname);
        fields.put(RequestParameter.EMPLOYEE_AGE, age);
        fields.put(RequestParameter.EMPLOYEE_GENDER, gender);
        fields.put(RequestParameter.EMPLOYEE_POSITION, position);
        fields.put(RequestParameter.HIRE_DATE, stringHireDate);
        Employee.Builder builder = new Employee.Builder(new Employee());
        builder.buildName(name)
                .buildSurname(surname)
                .buildAge(19)
                .buildGender('F')
                .buildPosition(Employee.Position.DOCTOR)
                .buildHireDateMillis(hireDate);
        Employee employee = builder.getEmployee();
        Optional<Employee> expected = Optional.of(employee);
        Optional<Employee> actual = employeeCreator.create(fields);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void createTest2() {
        Map<String, String> fields = new HashMap<>();
        String name = "Name1";
        String surname = "Surname";
        String age = "19";
        String gender = "F";
        String position = RequestParameter.DOCTOR;
        String stringHireDate = LocalDate.now().toString();
        fields.put(RequestParameter.EMPLOYEE_NAME, name);
        fields.put(RequestParameter.EMPLOYEE_SURNAME, surname);
        fields.put(RequestParameter.EMPLOYEE_AGE, age);
        fields.put(RequestParameter.EMPLOYEE_GENDER, gender);
        fields.put(RequestParameter.EMPLOYEE_POSITION, position);
        fields.put(RequestParameter.HIRE_DATE, stringHireDate);
        Optional<Employee> expected = Optional.empty();
        Optional<Employee> actual = employeeCreator.create(fields);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void createTest3() {
        Map<String, String> fields = new HashMap<>();
        String name = "Name";
        String surname = "Surname";
        String age = "17";
        String gender = "F";
        String position = RequestParameter.DOCTOR;
        String stringHireDate = LocalDate.now().toString();
        fields.put(RequestParameter.EMPLOYEE_NAME, name);
        fields.put(RequestParameter.EMPLOYEE_SURNAME, surname);
        fields.put(RequestParameter.EMPLOYEE_AGE, age);
        fields.put(RequestParameter.EMPLOYEE_GENDER, gender);
        fields.put(RequestParameter.EMPLOYEE_POSITION, position);
        fields.put(RequestParameter.HIRE_DATE, stringHireDate);
        Optional<Employee> expected = Optional.empty();
        Optional<Employee> actual = employeeCreator.create(fields);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void createTest4() {
        Map<String, String> fields = new HashMap<>();
        String name = "Name";
        String surname = "Surname";
        String age = "19";
        String gender = "F";
        String position = RequestParameter.DOCTOR;
        String stringHireDate = "2021-12-31";
        fields.put(RequestParameter.EMPLOYEE_NAME, name);
        fields.put(RequestParameter.EMPLOYEE_SURNAME, surname);
        fields.put(RequestParameter.EMPLOYEE_AGE, age);
        fields.put(RequestParameter.EMPLOYEE_GENDER, gender);
        fields.put(RequestParameter.EMPLOYEE_POSITION, position);
        fields.put(RequestParameter.HIRE_DATE, stringHireDate);
        Optional<Employee> expected = Optional.empty();
        Optional<Employee> actual = employeeCreator.create(fields);
        Assert.assertEquals(actual, expected);
    }
}
