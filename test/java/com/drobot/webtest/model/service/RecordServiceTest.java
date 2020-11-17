package com.drobot.webtest.model.service;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.exception.ConnectionPoolException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.PatientRecord;
import com.drobot.web.model.entity.Treatment;
import com.drobot.web.model.pool.ConnectionPool;
import com.drobot.web.model.service.RecordService;
import com.drobot.web.model.service.impl.RecordServiceImpl;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RecordServiceTest {

    private RecordService recordService;

    @BeforeTest
    public void setUp() {
        recordService = RecordServiceImpl.INSTANCE;
    }

    @Test
    public void findByIdTest1() {
        int id = 12;
        PatientRecord record = new PatientRecord(12, 5, 12, Treatment.SURGERY,
                12, "Fractures");
        Optional<PatientRecord> expected = Optional.of(record);
        Optional<PatientRecord> actual = Optional.empty();
        try {
            actual = recordService.findById(id);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void findByIdTest2() {
        int id = 0;
        Optional<PatientRecord> expected = Optional.empty();
        Optional<PatientRecord> actual = Optional.empty();
        try {
            actual = recordService.findById(id);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void packIntoMapTest1() {
        String id = "12";
        String patientId = "5";
        String doctorId = "12";
        Treatment treatment = Treatment.SURGERY;
        String executorId = "12";
        String diagnosis = "Fractures";
        PatientRecord record = new PatientRecord(12, 5, 12, Treatment.SURGERY,
                12, "Fractures");
        Map<String, String> expected = new HashMap<>();
        expected.put(RequestParameter.RECORD_ID, id);
        expected.put(RequestParameter.PATIENT_ID, patientId);
        expected.put(RequestParameter.ATTENDING_DOCTOR_ID, doctorId);
        expected.put(RequestParameter.PATIENT_TREATMENT, treatment.toString());
        expected.put(RequestParameter.EXECUTOR_ID, executorId);
        expected.put(RequestParameter.PATIENT_DIAGNOSIS, diagnosis);
        Map<String, String> actual = recordService.packRecordIntoMap(record);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void findTreatmentTest1() {
        try {
            int recordId = 12;
            Treatment treatment = Treatment.SURGERY;
            Optional<Treatment> expected = Optional.of(treatment);
            Optional<Treatment> actual = recordService.findTreatment(recordId);
            Assert.assertEquals(actual, expected);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
    }

    @Test
    public void findTreatmentTest2() {
        try {
            int recordId = 0;
            Optional<Treatment> expected = Optional.empty();
            Optional<Treatment> actual = recordService.findTreatment(recordId);
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
