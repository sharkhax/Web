package com.drobot.web.model.entity;

public class User extends Entity {

    public enum Role {
        ADMIN, DOCTOR, ASSISTANT
    }

    private String login;
    private String email;
    private Role role;
    private Status status;
    private int employeeId;

    public User() {
    }

    public User(int id, String login, String email, Role role, Status status, int employeeId) {
        super(id);
        this.login = login;
        this.email = email;
        this.role = role;
        this.status = status;
        this.employeeId = employeeId;
    }

    public User(String login, String email, Role role) {
        this.login = login;
        this.email = email;
        this.role = role;
    }

    public User(int userId, Role role, Status status) {
        super(userId);
        this.login = "";
        this.email = "";
        this.role = role;
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getEmployeeId() {
        return employeeId;
    }

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
        if (status != null ? status != user.status : user.status != null) {
            return false;
        }
        if (login != null) {
            if (login.equals(user.login)) {
                return false;
            }
        } else if (user.login != null) {
            return false;
        }
        if (email != null) {
            if (!email.equals(user.email)) {
                return false;
            }
        } else if (user.email != null) {
            return false;
        }
        if (employeeId != user.employeeId) {
            return false;
        }
        return role == user.role;
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
