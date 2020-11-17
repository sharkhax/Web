package com.drobot.web.model.entity;

/**
 * Entity class represents a user.
 *
 * @author Vladislav Drobot
 */
public class User extends Entity {

    /**
     * Enumeration contains users' roles.
     *
     * @author Vladislav Drobot
     */
    public enum Role {

        /**
         * Represents 'admin' role.
         */
        ADMIN,

        /**
         * Represents 'doctor' role.
         */
        DOCTOR,

        /**
         * Represents 'assistant' role.
         */
        ASSISTANT
    }

    private String login;
    private String email;
    private Role role;
    private Status status;
    private int employeeId;

    /**
     * Constructs an User object.
     */
    public User() {
    }

    /**
     * Constructs an User object with given user ID, login, email, role, status, employee ID.
     *
     * @param userId     user's ID int value.
     * @param login      String object of user's login.
     * @param email      String object of user's email.
     * @param role       User.Role object of user's role.
     * @param status     Entity.Status object of user's status.
     * @param employeeId user's employee ID int value.
     */
    public User(int userId, String login, String email, Role role, Status status, int employeeId) {
        super(userId);
        this.login = login;
        this.email = email;
        this.role = role;
        this.status = status;
        this.employeeId = employeeId;
    }

    /**
     * Constructs an User object with given login, email, role.
     *
     * @param login String object of user's login.
     * @param email String object of user's email.
     * @param role  User.Role object of user's role.
     */
    public User(String login, String email, Role role) {
        this.login = login;
        this.email = email;
        this.role = role;
    }

    /**
     * Constructs an User object with given user ID, role, status.
     *
     * @param userId user's ID int value.
     * @param role   User.Role object of user's role.
     * @param status Entity.Status object of user's status.
     */
    public User(int userId, Role role, Status status) {
        super(userId);
        this.login = "";
        this.email = "";
        this.role = role;
        this.status = status;
    }

    /**
     * Getter method of user's login.
     *
     * @return String object of user's login.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Setter method of user's login.
     *
     * @param login String object of user's login.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Getter method of user's email.
     *
     * @return String object of user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method of user's email.
     *
     * @param email String object of user's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter method of user's role.
     *
     * @return User.Role object of user's role.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Setter method of user's role.
     *
     * @param role User.Role object of user's role.
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Getter method of user's status.
     *
     * @return Entity.Status object of user's status.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Setter method of user's status.
     *
     * @param status Entity.Status object of user's status.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Getter method of user's employee ID.
     *
     * @return user's employee ID int value.
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * Setter method of user's employee ID.
     *
     * @param employeeId user's employee ID int value.
     */
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
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
        User user = (User) o;
        if (employeeId != user.employeeId) {
            return false;
        }
        if (login != null ? !login.equals(user.login) : user.login != null) {
            return false;
        }
        if (email != null ? !email.equals(user.email) : user.email != null) {
            return false;
        }
        if (role != user.role) {
            return false;
        }
        return status == user.status;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + employeeId;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(getId());
        sb.append(", login='").append(login).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", role=").append(role);
        sb.append(", status=").append(status);
        sb.append(", employeeId=").append(employeeId);
        sb.append('}');
        return sb.toString();
    }
}
