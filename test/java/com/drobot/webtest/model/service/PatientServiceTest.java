package com.drobot.webtest.model.service;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.exception.ConnectionPoolException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.entity.Patient;
import com.drobot.web.model.pool.ConnectionPool;
import com.drobot.web.model.service.PatientService;
import com.drobot.web.model.service.impl.PatientServiceImpl;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PatientServiceTest {

    private PatientService patientService;

    @BeforeTest
    public void setUp() {
        patientService = PatientServiceImpl.INSTANCE;
    }

    @Test
    public void packIntoMapTest1() {
        String id = "1";
        String name = "Donald";
        String surname = "Trump";
        String age = "74";
        String gender = "M";
        String diagnosis = "Covid";
        Entity.Status status = Entity.Status.ARCHIVE;
        String lastRecord = "16";
        Patient patient = new Patient(1, name, surname, 74, 'M', diagnosis, status, 16);
        Map<String, String> expected = new HashMap<>();
        expected.put(RequestParameter.PATIENT_ID, id);
        expected.put(RequestParameter.PATIENT_NAME, name);
        expected.put(RequestParameter.PATIENT_SURNAME, surname);
        expected.put(RequestParameter.PATIENT_AGE, age);
        expected.put(RequestParameter.PATIENT_GENDER, gender);
        expected.put(RequestParameter.PATIENT_STATUS, status.toString());
        expected.put(RequestParameter.LAST_RECORD_ID, lastRecord);
        expected.put(RequestParameter.PATIENT_DIAGNOSIS, diagnosis);
        Map<String, String> actual = patientService.packIntoMap(patient);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void findByIdTest1() {
        int id = 1;
        String name = "Donald";
        String surname = "Trump";
        String diagnosis = "Covid";
        Entity.Status status = Entity.Status.ARCHIVE;
        Patient patient = new Patient(1, name, surname, 74, 'M', diagnosis, status, 16);
        try {
            Optional<Patient> expected = Optional.of(patient);
            Optional<Patient> actual = patientService.findById(id);
            Assert.assertEquals(actual, expected);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
    }

    @Test
    public void findByIdTest2() {
        int id = 0;
        try {
            Optional<Patient> expected = Optional.empty();
            Optional<Patient> actual = patientService.findById(id);
            Assert.assertEquals(actual, expected);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
    }

    @AfterTest
    public void destroy() throws ConnectionPoolException {
        ConnectionPool.INSTANCE.destroyPool();
    }
}
