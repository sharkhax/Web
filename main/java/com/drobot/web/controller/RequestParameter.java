package com.drobot.web.controller;

public final class RequestParameter {

    private RequestParameter() {
    }

    /**
     * Form's pages
     */
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String WRONG_LOGIN_PASSWORD = "invalidLoginOrPass";
    public static final String USER_BLOCKED = "userBlocked";
    public static final String REGISTRATION_SUCCESS = "registrationSuccess";
    public static final String VALIDATED = "validated";
    public static final String USER_REGISTRATION_FIELDS = "userRegistrationFields";
    public static final String USER_REGISTRATION_EXISTING_FIELDS = "userRegistrationExistingFields";
    public static final String CURRENT_PASSWORD = "currentPassword";
    public static final String NEW_PASSWORD = "newPassword";

    /**
     * Employee's creating/updating form
     */
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String AGE = "age";
    public static final String GENDER = "gender";
    public static final String POSITION = "position";
    public static final String HIRE_DATE = "hireDate";
    public static final String DISMISS_DATE = "dismissDate";
    public static final String STATUS_ID = "statusId";
    public static final String DOCTOR = "doctor";
    public static final String ASSISTANT = "assistant";
    public static final String EMPLOYEE_STATUS = "employeeStatus";

    /**
     * Session's attributes
     */
    public static final String USER_ROLE = "role";
    public static final String USER_ID = "userId";
    public static final String ADMIN_ROLE = "admin_role";
    public static final String DOCTOR_ROLE = "doctor_role";
    public static final String ASSISTANT_ROLE = "assistant_role";
    public static final String GUEST_ROLE = "guest_role";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String CURRENT_LOCALE = "currentLocale";
    public static final String DEFAULT_LOCALE = "en_EN";
    public static final String LOGIN_INFO = "login_info";
    public static final String USER_DATA_FIELDS = "userDataFields";
    public static final String CHANGING_PASSWORD_FIELDS = "changingPasswordFields";

    /**
     * Common
     */
    public static final String REDIRECTING_PAGE = "r_page";
    public static final String COMMAND = "command";

}
