package com.drobot.web.controller;

public final class UrlPattern {

    private UrlPattern() {
    }
    public static final String ASTERISK = "*";
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
    //public static final String RECORD_CREATING = "/mainPage/recordCreating";
    //public static final String RECORD_CREATING_SUCCESS = "/mainPage/recordCreating/success";
    //public static final String RECORD_CREATING_FAIL = "/mainPage/recordCreating/fail";
    public static final String PATIENT_CREATING = "/mainPage/newPatient";
    public static final String PATIENT_CREATING_SUCCESS = "/mainPage/newPatient/success";
    public static final String PATIENT_CREATING_FAIL = "/mainPage/newPatient/fail";
    public static final String PATIENT_LIST = "/mainPage/patients";
    public static final String USER_INFO = "/mainPage/users/*";
    public static final String USER_INFO_REQUEST = "/mainController?command=user_data&requestedUserInfoId=";
    public static final String EMPLOYEE_INFO = "/mainPage/employees/*";
    public static final String EMPLOYEE_INFO_REQUEST = "/mainController?command=employee_data&requestedEmployeeInfoId=";
    public static final String PATIENT_INFO = "/mainPage/patients/*";
    public static final String PATIENT_INFO_REQUEST = "/mainController?command=patient_data&requestedPatientInfoId=";
    public static final String ADMIN_CHANGING_PASSWORD = "/mainPage/users/*/changePassword";
    public static final String ADMIN_CHANGING_PASSWORD_REQUEST =
            "/mainController?command=redirect_to_updating_password&userId=";
    public static final String UPDATING_USER = "/mainPage/users/*/update";
    public static final String UPDATING_EMPLOYEE = "/mainPage/employees/*/update";
    public static final String UPDATING_PATIENT = "/mainPage/patients/*/update";
    public static final String UPDATING_USER_REQUEST =
            "/mainController?command=redirect_to_update_user_page&userId=";
    public static final String UPDATING_EMPLOYEE_REQUEST =
            "/mainController?command=redirect_to_update_employee_page&employeeId=";
    public static final String UPDATING_PATIENT_REQUEST =
            "/mainController?command=redirect_to_update_patient_page&patientId=";
}
