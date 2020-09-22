package com.drobot.web.model.entity;

public class User extends Entity {

    public enum Role {
        ADMIN, DOCTOR, ASSISTANT
    }

    private String login;
    private String email;
    private Role role;
    private boolean isActive;

    public User(int id, String login, String email, Role role, boolean isActive) {
        super(id);
        this.login = login;
        this.email = email;
        this.role = role;
        this.isActive = isActive;
    }

    public User(String login, String email, Role role) {
        this.login = login;
        this.email = email;
        this.role = role;
    }

    public User(Role role, boolean isActive) {
        this.login = "";
        this.email = "";
        this.role = role;
        this.isActive = isActive;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
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
        if (isActive != user.isActive) {
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
        return role == user.role;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("userId=").append(getId());
        sb.append(", login='").append(login).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", role=").append(role);
        sb.append(", isActive=").append(isActive);
        sb.append('}');
        return sb.toString();
    }
}
