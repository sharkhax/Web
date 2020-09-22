package com.drobot.web.model.entity;

public class Employee extends Entity { // TODO: 18.09.2020 equals hashcode toString

    public enum Position {
        DOCTOR, ASSISTANT
    }

    private String name;
    private String surname;
    private byte age;
    private char gender;
    private Position position;
    private final long hireDateMillis;
    private Entity.Status status;
    private int userAccountId;

    public Employee(String name, String surname,
                    byte age, char gender, Position position,
                    long hireDateMillis, Status status, int userAccountId) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
        this.position = position;
        this.hireDateMillis = hireDateMillis;
        this.status = status;
        this.userAccountId = userAccountId;
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

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public long getHireDateMillis() {
        return hireDateMillis;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(int userAccountId) {
        this.userAccountId = userAccountId;
    }
}
