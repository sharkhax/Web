package com.drobot.webtest.model.creator;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.model.creator.Creator;
import com.drobot.web.model.creator.impl.PatientCreator;
import com.drobot.web.model.entity.Patient;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PatientCreatorTest {

    private Creator<Patient> patientCreator;

    @BeforeTest
    public void setUp() {
        patientCreator = new PatientCreator();
    }

    @Test
    public void createTest1() {
        String name = "Name";
        String surname = "Surname";
        String age = "20";
        String gender = "F";
        Map<String, String> fields = new HashMap<>();
        fields.put(RequestParameter.PATIENT_NAME, name);
        fields.put(RequestParameter.PATIENT_SURNAME, surname);
        fields.put(RequestParameter.PATIENT_AGE, age);
        fields.put(RequestParameter.PATIENT_GENDER, gender);
        Optional<Patient> actual = patientCreator.create(fields);
        Patient patient = new Patient(name, surname, 20, 'F');
        Optional<Patient> expected = Optional.of(patient);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void createTest2() {
        String name = "Name!";
        String surname = "Surname";
        String age = "20";
        String gender = "F";
        Map<String, String> fields = new HashMap<>();
        fields.put(RequestParameter.PATIENT_NAME, name);
        fields.put(RequestParameter.PATIENT_SURNAME, surname);
        fields.put(RequestParameter.PATIENT_AGE, age);
        fields.put(RequestParameter.PATIENT_GENDER, gender);
        Optional<Patient> actual = patientCreator.create(fields);
        Optional<Patient> expected = Optional.empty();
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void createTest3() {
        String name = "Name";
        String surname = "Surname";
        String age = "16";
        String gender = "F";
        Map<String, String> fields = new HashMap<>();
        fields.put(RequestParameter.PATIENT_NAME, name);
        fields.put(RequestParameter.PATIENT_SURNAME, surname);
        fields.put(RequestParameter.PATIENT_AGE, age);
        fields.put(RequestParameter.PATIENT_GENDER, gender);
        Optional<Patient> actual = patientCreator.create(fields);
        Optional<Patient> expected = Optional.empty();
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void createTest4() {
        String name = "Name";
        String surname = "Surname";
        String age = "19";
        String gender = "H";
        Map<String, String> fields = new HashMap<>();
        fields.put(RequestParameter.PATIENT_NAME, name);
        fields.put(RequestParameter.PATIENT_SURNAME, surname);
        fields.put(RequestParameter.PATIENT_AGE, age);
        fields.put(RequestParameter.PATIENT_GENDER, gender);
        Optional<Patient> actual = patientCreator.create(fields);
        Optional<Patient> expected = Optional.empty();
        Assert.assertEquals(actual, expected);
    }
}
