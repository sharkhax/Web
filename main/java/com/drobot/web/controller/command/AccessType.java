package com.drobot.web.controller.command;

import com.drobot.web.controller.SessionAttribute;

/**
 * Represents types of access to the pages.
 *
 * @author Vladislav Drobot
 */
public enum AccessType {

    /**
     * Represents an access type for admin.
     */
    ADMIN(SessionAttribute.ADMIN_ROLE),

    /**
     * Represents an access type for doctor.
     */
    DOCTOR(SessionAttribute.DOCTOR_ROLE),

    /**
     * Represents an access type for assistant.
     */
    ASSISTANT(SessionAttribute.ASSISTANT_ROLE),

    /**
     * Represents an access type for unregistered user.
     */
    GUEST(SessionAttribute.GUEST_ROLE);

    private final String userRole;

    AccessType(String userRole) {
        this.userRole = userRole;
    }

    /**
     * Getter method access type's user role.
     *
     * @return String representation of user's role.
     */
    public String getUserRole() {
        return userRole;
    }
}
