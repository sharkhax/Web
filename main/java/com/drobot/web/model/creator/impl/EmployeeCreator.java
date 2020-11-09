package com.drobot.web.model.creator.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.model.creator.Creator;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.model.service.impl.EmployeeMapService;
import com.drobot.web.model.util.DateConverter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Map;
import java.util.Optional;

public class EmployeeCreator implements Creator<Employee> {

    private static final Logger LOGGER = LogManager.getLogger(EmployeeCreator.class);

    @Override
    public Optional<Employee> create(Map<String, String> fields) {
        Optional<Employee> result;
        EmployeeMapService mapService = EmployeeMapService.INSTANCE;
        if (mapService.isMapValid(fields)) {
            String name = fields.get(RequestParameter.EMPLOYEE_NAME);
            String surname = fields.get(RequestParameter.EMPLOYEE_SURNAME);
            String stringAge = fields.get(RequestParameter.EMPLOYEE_AGE);
            int age = Integer.parseInt(stringAge);
            String stringGender = fields.get(RequestParameter.EMPLOYEE_GENDER);
            char gender = stringGender.charAt(0);
            String stringPosition = fields.get(RequestParameter.EMPLOYEE_POSITION);
            Employee.Position position = Employee.Position.valueOf(stringPosition.toUpperCase());
            String stringDate = fields.get(RequestParameter.HIRE_DATE);
            long hireDateDays = LocalDate.parse(stringDate).getLong(ChronoField.EPOCH_DAY);
            long hireDateMillis = DateConverter.daysToMillis(hireDateDays);
            Employee employee = new Employee(name, surname, age, gender, position, hireDateMillis);
            result = Optional.of(employee);
        } else {
            result = Optional.empty();
            LOGGER.log(Level.DEBUG, "Some fields are invalid or absent");
        }
        return result;
    }
}
