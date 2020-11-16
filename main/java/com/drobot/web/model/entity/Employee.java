package com.drobot.web.model.entity;

/**
 * Entity class represents an employee.
 *
 * @author Vladislav Drobot
 */
public class Employee extends Entity {

    /**
     * Enumeration of the available employees' positions.
     *
     * @author Vladislav Drobot
     */
    public enum Position {
        /**
         * Represents doctor's position.
         */
        DOCTOR,

        /**
         * Represents assistant's position.
         */
        ASSISTANT
    }

    /**
     * Class represents a builder pattern.
     *
     * @author Vladislav Drobot
     */
    public static class Builder {

        private final Employee employee;

        /**
         * Constructs an Employee.Builder object with given employee.
         *
         * @param employee Employee object to be built.
         */
        public Builder(Employee employee) {
            this.employee = employee;
        }

        /**
         * Builds employee's ID field.
         *
         * @param employeeId employee ID int value.
         * @return Employee.Builder object containing an Employee object, which is being built.
         */
        public Builder buildId(int employeeId) {
            employee.setId(employeeId);
            return this;
        }

        /**
         * Builds employee's name field.
         *
         * @param name String object of employee's name.
         * @return Employee.Builder object containing an Employee object, which is being built.
         */
        public Builder buildName(String name) {
            employee.setName(name);
            return this;
        }

        /**
         * Builds employee's surname field.
         *
         * @param surname String object of employee's surname.
         * @return Employee.Builder object containing an Employee object, which is being built.
         */
        public Builder buildSurname(String surname) {
            employee.setSurname(surname);
            return this;
        }

        /**
         * Builds employee's age field.
         *
         * @param age employee's age int value.
         * @return Employee.Builder object containing an Employee object, which is being built.
         */
        public Builder buildAge(int age) {
            employee.setAge(age);
            return this;
        }

        /**
         * Builds employee's gender field.
         *
         * @param gender employee's gender char value.
         * @return Employee.Builder object containing an Employee object, which is being built.
         */
        public Builder buildGender(char gender) {
            employee.setGender(gender);
            return this;
        }

        /**
         * Builds employee's position field.
         *
         * @param position Employee.Position object of employee's position.
         * @return Employee.Builder object containing an Employee object, which is being built.
         */
        public Builder buildPosition(Position position) {
            employee.setPosition(position);
            return this;
        }

        /**
         * Builds employee's hireDateMillis field.
         *
         * @param hireDateMillis employee's hire date long value in milliseconds.
         * @return Employee.Builder object containing an Employee object, which is being built.
         */
        public Builder buildHireDateMillis(long hireDateMillis) {
            employee.setHireDateMillis(hireDateMillis);
            return this;
        }

        /**
         * Builds employee's dismissDateMillis field.
         *
         * @param dismissDateMillis employee's dismiss date long value in milliseconds.
         * @return Employee.Builder object containing an Employee object, which is being built.
         */
        public Builder buildDismissDateMillis(long dismissDateMillis) {
            employee.setDismissDateMillis(dismissDateMillis);
            return this;
        }

        /**
         * Builds employee's status field.
         *
         * @param status Entity.Status object of employee's status.
         * @return Employee.Builder object containing an Employee object, which is being built.
         */
        public Builder buildStatus(Status status) {
            employee.setStatus(status);
            return this;
        }

        /**
         * Builds employee's user ID field.
         *
         * @param userId employee's user ID int value.
         * @return Employee.Builder object containing an Employee object, which is being built.
         */
        public Builder buildUserId(int userId) {
            employee.setUserId(userId);
            return this;
        }

        /**
         * Getter method of an Employee object.
         *
         * @return Employee object.
         */
        public Employee getEmployee() {
            return employee;
        }
    }

    private String name;
    private String surname;
    private int age;
    private char gender;
    private Position position;
    private long hireDateMillis;
    private long dismissDateMillis;
    private Entity.Status status;
    private int userId;

    /**
     * Constructs an Employee object.
     */
    public Employee() {
    }

    /**
     * Constructs an Employee object with given employee ID, name, surname, age, gender, position, hire date,
     * dismiss date, status, user ID.
     *
     * @param employeeId        employee's ID int value.
     * @param name              String object of employee's name.
     * @param surname           String object of employee's surname.
     * @param age               employee's age int value.
     * @param gender            employee's gender char value.
     * @param position          Employee.Position object of employee's position.
     * @param hireDateMillis    employee's hire date long value in milliseconds.
     * @param dismissDateMillis employee's dismiss date long value in milliseconds.
     * @param status            Entity.Status object of employee's status.
     * @param userId            employee's user ID int value.
     */
    public Employee(int employeeId, String name, String surname,
                    int age, char gender, Position position,
                    long hireDateMillis, long dismissDateMillis, Status status, int userId) {
        super(employeeId);
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
        this.position = position;
        this.hireDateMillis = hireDateMillis;
        this.dismissDateMillis = dismissDateMillis;
        this.status = status;
        this.userId = userId;
    }

    /**
     * Getter method of employee's name.
     *
     * @return String object of employee's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method of employee's name.
     *
     * @param name String object of employee's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method of employee's surname.
     *
     * @return String object of employee's surname.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Setter method of employee's surname.
     *
     * @param surname String object of employee's surname.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Getter method of employee's age.
     *
     * @return int value of employee's age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Setter method of employee's age.
     *
     * @param age int value of employee's age.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Getter method of employee's gender.
     *
     * @return char value of employee's gender.
     */
    public char getGender() {
        return gender;
    }

    /**
     * Setter method of employee's gender.
     *
     * @param gender char value of employee's gender.
     */
    public void setGender(char gender) {
        this.gender = gender;
    }

    /**
     * Getter method of employee's position.
     *
     * @return Employee.Position object of employee's position.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Setter method of employee's position.
     *
     * @param position Employee.Position object of employee's position.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Getter method of employee's hire date.
     *
     * @return long value of employee's hire date in milliseconds.
     */
    public long getHireDateMillis() {
        return hireDateMillis;
    }

    /**
     * Setter method of employee's hire date.
     *
     * @param hireDateMillis long value of employee's hire date in milliseconds.
     */
    public void setHireDateMillis(long hireDateMillis) {
        this.hireDateMillis = hireDateMillis;
    }

    /**
     * Getter method of employee's dismiss date.
     *
     * @return long value of employee's dismiss date in milliseconds.
     */
    public long getDismissDateMillis() {
        return dismissDateMillis;
    }

    /**
     * Setter method of employee's dismiss date.
     *
     * @param dismissDateMillis long value of employee's dismiss date in milliseconds.
     */
    public void setDismissDateMillis(long dismissDateMillis) {
        this.dismissDateMillis = dismissDateMillis;
    }

    /**
     * Getter method of employee's status.
     *
     * @return Employee.Status object of employee's status.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Setter method of employee's status.
     *
     * @param status Employee.Status object of employee's status.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Getter method of employee's user ID.
     *
     * @return int value of employee's user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter method of employee's user ID.
     *
     * @param userId int value of employee's user ID.
     */
    public void setUserId(int userId) {
        this.userId = userId;
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
        if (name != null ? !name.equals(employee.name) : employee.name != null) {
            return false;
        }
        if (surname != null ? !surname.equals(employee.surname) : employee.surname != null) {
            return false;
        }
        if (position != employee.position) {
            return false;
        }
        if (userId != employee.userId) {
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
        result = 31 * result + userId;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Employee{");
        sb.append("id='").append(getId()).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", age=").append(age);
        sb.append(", gender=").append(gender);
        sb.append(", position=").append(position);
        sb.append(", hireDateMillis=").append(hireDateMillis);
        sb.append(", dismissDateMillis=").append(dismissDateMillis);
        sb.append(", status=").append(status);
        sb.append(", userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }
}

