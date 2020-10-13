package com.drobot.web.model.entity;

public class Employee extends Entity {

    public enum Position {
        DOCTOR, ASSISTANT
    }

    private String name;
    private String surname;
    private int age;
    private char gender;
    private Position position;
    private final long hireDateMillis;
    private long dismissDateMillis;
    private Entity.Status status;
    private int userAccountId;

    public Employee(String name, String surname,
                    int age, char gender, Position position,
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

    public Employee(String name, String surname,
                    int age, char gender, Position position,
                    long hireDateMillis, int userAccountId) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
        this.position = position;
        this.hireDateMillis = hireDateMillis;
        this.userAccountId = userAccountId;
    }

    public Employee(String name, String surname, int age, char gender,
                    Position position, long hireDateMillis, long dismissDateMillis,
                    Status status, int userAccountId) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
        this.position = position;
        this.hireDateMillis = hireDateMillis;
        this.dismissDateMillis = dismissDateMillis;
        this.status = status;
        this.userAccountId = userAccountId;
    }

    public Employee(int employeeId, String name, String surname,
                    int age, char gender, Position position,
                    long hireDateMillis, long dismissDateMillis, Status status, int userAccountId) {
        super(employeeId);
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
        this.position = position;
        this.hireDateMillis = hireDateMillis;
        this.dismissDateMillis = dismissDateMillis;
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

    public int getAge() {
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

    public long getDismissDateMillis() {
        return dismissDateMillis;
    }

    public void setDismissDateMillis(long dismissDateMillis) {
        this.dismissDateMillis = dismissDateMillis;
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
        Employee employee = (Employee) o;
        if (age != employee.age) {
            return false;
        }
        if (gender != employee.gender) {
            return false;
        }
        if (hireDateMillis != employee.hireDateMillis) {
            return false;
        }
        if (dismissDateMillis != employee.dismissDateMillis) {
            return false;
        }
        if (userAccountId != employee.userAccountId) {
            return false;
        }
        if (name != null ? !name.equals(employee.name) : employee.name != null) {
            return false;
        }
        if (surname != null ? !surname.equals(employee.surname) : employee.surname != null) {
            return false;
        }
        if (position != employee.position) {
            return false;
        }
        return status == employee.status;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (int) gender;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (int) (hireDateMillis ^ (hireDateMillis >>> 32));
        result = 31 * result + (int) (dismissDateMillis ^ (dismissDateMillis >>> 32));
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + userAccountId;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Employee{");
        sb.append("name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", age=").append(age);
        sb.append(", gender=").append(gender);
        sb.append(", position=").append(position);
        sb.append(", hireDateMillis=").append(hireDateMillis);
        sb.append(", dismissDateMillis=").append(dismissDateMillis);
        sb.append(", status=").append(status);
        sb.append(", userAccountId=").append(userAccountId);
        sb.append('}');
        return sb.toString();
    }
}

