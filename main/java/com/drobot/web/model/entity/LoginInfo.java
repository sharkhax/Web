package com.drobot.web.model.entity;

public class LoginInfo {

    private User.Role role;
    private byte status;

    public LoginInfo(User.Role role, byte status) {
        this.role = role;
        this.status = status;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
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
        LoginInfo loginInfo = (LoginInfo) o;
        if (status != loginInfo.status) {
            return false;
        }
        return role == loginInfo.role;
    }

    @Override
    public int hashCode() {
        int result = role != null ? role.hashCode() : 0;
        result = 31 * result + status;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LoginInfo{");
        sb.append("role=").append(role);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
