package com.drobot.web.controller;

public final class UrlPattern {

    private UrlPattern() {
    }

    public static final String MAIN_CONTROLLER = "/mainController";
    public static final String LOGIN_PAGE = "/loginPage";
    public static final String MAIN_PAGE = "/mainPage";
    public static final String USER_REGISTRATION = "/mainPage/userRegistration";
    public static final String USER_REGISTRATION_SUCCESS = "/mainPage/userRegistration/success";
    public static final String USER_REGISTRATION_FAIL = "/mainPage/userRegistration/fail";
    public static final String USER_LIST = "/mainPage/users";
    public static final String EMPLOYEE_LIST = "/mainPage/employees";
    public static final String RECORD_LIST = "/mainPage/records";
    public static final String PERSONAL_SETTINGS = "/settings";
    public static final String CHANGING_PASSWORD = "/settings/changePassword";
}
