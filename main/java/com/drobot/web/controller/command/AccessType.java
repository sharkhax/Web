package com.drobot.web.controller.command;

import com.drobot.web.controller.RequestParameter;

public enum AccessType {

    ADMIN(RequestParameter.ADMIN_ROLE),
    DOCTOR(RequestParameter.DOCTOR_ROLE),
    ASSISTANT(RequestParameter.ASSISTANT_ROLE),
    GUEST(RequestParameter.GUEST_ROLE);

    private final String userRole;

    AccessType(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }
}
