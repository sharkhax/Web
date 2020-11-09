package com.drobot.web.controller.command;

import com.drobot.web.controller.SessionAttribute;

public enum AccessType {

    ADMIN(SessionAttribute.ADMIN_ROLE),
    DOCTOR(SessionAttribute.DOCTOR_ROLE),
    ASSISTANT(SessionAttribute.ASSISTANT_ROLE),
    GUEST(SessionAttribute.GUEST_ROLE);

    private final String userRole;

    AccessType(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }
}
