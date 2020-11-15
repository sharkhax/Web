package com.drobot.web.model.entity;

public class Patient extends Entity {

    private String name;
    private String surname;
    private int age;
    private char gender;
    private String diagnosis;
    private Status status;
    private int recordId;

    public Patient() {
    }

    public Patient(int id, String name, String surname, int age, char gender) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
    }

    public Patient(String name, String surname, int age, char gender) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
    }

    public Patient(int id, String name, String surname, int age, char gender,
                   String diagnosis, Status status, int recordId) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
        this.diagnosis = diagnosis;
        this.status = status;
        this.recordId = recordId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Patient patient = (Patient) o;
        if (age != patient.age) {
            return false;
        }
        if (gender != patient.gender) {
            return false;
        }
        if (name != null ? !name.equals(patient.name) : patient.name != null) {
            return false;
        }
        if (surname != null ? !surname.equals(patient.surname) : patient.surname != null) {
            return false;
        }
        if (diagnosis != null ? !diagnosis.equals(patient.diagnosis) : patient.diagnosis != null) {
            return false;
        }
        if (recordId != patient.recordId) {
            return false;
        }
        return status == patient.status;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + recordId;
        result = 31 * result + (int) gender;
        result = 31 * result + (diagnosis != null ? diagnosis.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Patient{");
        sb.append("id='").append(getId()).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", age=").append(age);
        sb.append(", gender=").append(gender);
        sb.append(", diagnosis='").append(diagnosis).append('\'');
        sb.append(", status=").append(status);
        sb.append(", recordId=").append(recordId);
        sb.append('}');
        return sb.toString();
    }
}
