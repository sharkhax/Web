package com.drobot.web.controller;

public final class RequestParameter {

    private RequestParameter() {
    }

    /**
     * Login and registration pages
     */
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String WRONG_LOGIN_PASSWORD = "invalidLoginOrPass";
    public static final String USER_BLOCKED = "userBlocked";
    public static final String WRONG_ACTION = "wrongAction";

    /**
     * Employee's creating/updating form
     */
    public static final String EMPLOYEE_ID = "employeeId";
    public static final String EMPLOYEE_NAME = "employeeName";
    public static final String EMPLOYEE_SURNAME = "employeeSurname";
    public static final String EMPLOYEE_AGE = "employeeAge";
    public static final String EMPLOYEE_GENDER = "employeeGender";
    public static final String EMPLOYEE_POSITION = "employeePosition";
    public static final String EMPLOYEE_HIRE_DATE = "employeeHireDate";
    public static final String EMPLOYEE_DISMISS_DATE = "employeeDismissDate";
    public static final String EMPLOYEE_STATUS_ID = "employeeStatusId";
    public static final String EMPLOYEE_ACCOUNT_ID = "employeeAccountId";

    /**
     * Session's attributes
     */
    public static final String USER_ROLE = "role";
    public static final String ADMIN_ROLE = "admin_role";
    public static final String DOCTOR_ROLE = "doctor_role";
    public static final String ASSISTANT_ROLE = "assistant_role";
    public static final String GUEST_ROLE = "guest_role";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String CURRENT_LOCALE = "currentLocale";
    public static final String DEFAULT_LOCALE = "en_EN";
    public static final String LOGIN_INFO = "login_info";

    /**
     * Common
     */
    public static final String REDIRECTING_PAGE = "r_page";
    public static final String COMMAND = "command";

}
