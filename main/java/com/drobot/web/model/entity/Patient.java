package com.drobot.web.model.entity;

/**
 * Entity class represents a patient.
 *
 * @author Vladislav Drobot
 */
public class Patient extends Entity {

    private String name;
    private String surname;
    private int age;
    private char gender;
    private String diagnosis;
    private Status status;
    private int recordId;

    /**
     * Constructs a Patient object.
     */
    public Patient() {
    }

    /**
     * Constructs a Patient object with given ID, name, surname, age, gender.
     *
     * @param id      patient's ID int value.
     * @param name    String object of patient's name.
     * @param surname String object of patient's surname.
     * @param age     patient's age int value.
     * @param gender  patient's gender char value.
     */
    public Patient(int id, String name, String surname, int age, char gender) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
    }

    /**
     * Constructs a Patient object with given name, surname, age, gender.
     *
     * @param name    String object of patient's name.
     * @param surname String object of patient's surname.
     * @param age     patient's age int value.
     * @param gender  patient's gender char value.
     */
    public Patient(String name, String surname, int age, char gender) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
    }

    /**
     * Constructs a Patient object with given patient's ID, name, surname, age, gender, diagnosis, status, record's ID.
     *
     * @param id        patient's ID int value.
     * @param name      String object of patient's name.
     * @param surname   String object of patient's surname.
     * @param age       patient's age int value.
     * @param gender    patient's gender char value.
     * @param diagnosis String object of patient's diagnosis.
     * @param status    Entity.Status object of patient's status.
     * @param recordId  record's ID int value.
     */
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

    /**
     * Getter method of patient's name.
     *
     * @return String object of patient's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method of patient's name.
     *
     * @param name String object of patient's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method of patient's surname.
     *
     * @return String object of patient's surname.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Setter method of patient's surname.
     *
     * @param surname String object of patient's surname.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Getter method of patient's age.
     *
     * @return patient's age int value.
     */
    public int getAge() {
        return age;
    }

    /**
     * Setter method of patient's age.
     *
     * @param age patient's age int value.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Getter method of patient's gender.
     *
     * @return patient's gender char value.
     */
    public char getGender() {
        return gender;
    }

    /**
     * Setter method of patient's gender.
     *
     * @param gender patient's gender char value.
     */
    public void setGender(char gender) {
        this.gender = gender;
    }

    /**
     * Getter method of patient's diagnosis.
     *
     * @return String object of patient's diagnosis.
     */
    public String getDiagnosis() {
        return diagnosis;
    }

    /**
     * Setter method of patient's diagnosis.
     *
     * @param diagnosis String object of patient's diagnosis.
     */
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    /**
     * Getter method of patient's status.
     *
     * @return Entity.Status object of patient's status.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Setter method of patient's status.
     *
     * @param status Entity.Status object of patient's status.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Getter method of patient's record ID.
     *
     * @return record's ID int value.
     */
    public int getRecordId() {
        return recordId;
    }

    /**
     * Setter method of patient's record ID.
     *
     * @param recordId record's ID int value.
     */
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
        sb.append("id=").append(getId());
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
