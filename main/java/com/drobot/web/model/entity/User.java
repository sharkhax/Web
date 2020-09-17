package com.drobot.web.model.entity;

public class User extends Entity {

    public enum Role {
        ADMIN, DOCTOR, ASSISTANT
    }

    private String login;
    private String email;
    private final String encPassword;
    private Role role;
    private byte status;

    public User(int id, String login, String email, Role role, byte status) {
        super(id);
        this.login = login;
        this.email = email;
        this.encPassword = "";
        this.role = role;
        this.status = status;
    }

    public User(String login, String email, String encPassword, Role role) {
        this.login = login;
        this.email = email;
        this.encPassword = encPassword;
        this.role = role;
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

    public String getEncPassword() {
        return encPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
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
        if (!login.equals(user.login)) {
            return false;
        }
        if (!email.equals(user.email)) {
            return false;
        }
        return encPassword.equals(user.encPassword);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + encPassword.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("userId='").append(getId()).append('\'');
        sb.append(", login='").append(login).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", encPassword='").append(encPassword).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
