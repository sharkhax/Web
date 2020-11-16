package com.drobot.web.controller;

/**
 * Class contains URL patterns of existing pages.
 *
 * @author Vladislav Drobot
 */
public final class UrlPattern {

    private UrlPattern() {
    }

    /**
     * Represents asterisk symbol that used in dynamic patterns.
     */
    public static final String ASTERISK = "*";

    /**
     * Represents URL pattern for main controller.
     */
    public static final String MAIN_CONTROLLER = "/mainController";

    /**
     * Represents URL pattern for login page.
     */
    public static final String LOGIN_PAGE = "/loginPage";

    /**
     * Represents URL pattern for main page.
     */
    public static final String MAIN_PAGE = "/mainPage";

    /**
     * Represents URL pattern for user registration page.
     */
    public static final String USER_REGISTRATION = "/mainPage/userRegistration";

    /**
     * Represents URL pattern for successful user registration page.
     */
    public static final String USER_REGISTRATION_SUCCESS = "/mainPage/userRegistration/success";

    /**
     * Represents URL pattern for failed user registration page.
     */
    public static final String USER_REGISTRATION_FAIL = "/mainPage/userRegistration/fail";

    /**
     * Represents URL pattern for user list page.
     */
    public static final String USER_LIST = "/mainPage/users";

    /**
     * Represents URL pattern for user list request.
     */
    public static final String USER_LIST_REQUEST = "/mainController?command=user_list_command";

    /**
     * Represents URL pattern for employee list page.
     */
    public static final String EMPLOYEE_LIST = "/mainPage/employees";

    /**
     * Represents URL pattern for employee list request.
     */
    public static final String EMPLOYEE_LIST_REQUEST = "//mainController?command=employee_list_command";

    /**
     * Represents URL pattern for record list page.
     */
    public static final String RECORD_LIST = "/mainPage/patients/*/records";

    /**
     * Represents URL pattern for personal settings page.
     */
    public static final String PERSONAL_SETTINGS = "/settings";

    /**
     * Represents URL pattern for personal settings request.
     */
    public static final String PERSONAL_SETTINGS_REQUEST = "/mainController?command=settings_page";

    /**
     * Represents URL pattern for changing password page.
     */
    public static final String CHANGING_PASSWORD = "/settings/changePassword";

    /**
     * Represents URL pattern for record creating page.
     */
    public static final String RECORD_CREATING = "/mainPage/patients/*/createRecord";

    /**
     * Represents URL pattern for record creating request.
     */
    public static final String RECORD_CREATING_REQUEST =
            "/mainController?command=redirect_to_record_creating_page&patientId=";

    /**
     * Represents URL pattern for patient creating page.
     */
    public static final String PATIENT_CREATING = "/mainPage/newPatient";

    /**
     * Represents URL pattern for successful patient creating page.
     */
    public static final String PATIENT_CREATING_SUCCESS = "/mainPage/newPatient/success";

    /**
     * Represents URL pattern for failed patient creating page.
     */
    public static final String PATIENT_CREATING_FAIL = "/mainPage/newPatient/fail";

    /**
     * Represents URL pattern for patient list page.
     */
    public static final String PATIENT_LIST = "/mainPage/patients";

    /**
     * Represents URL pattern for patient list request.
     */
    public static final String PATIENT_LIST_REQUEST = "/mainController?command=patient_list_command";

    /**
     * Represents URL pattern for user info page.
     */
    public static final String USER_INFO = "/mainPage/users/*";

    /**
     * Represents URL pattern for user info request.
     */
    public static final String USER_INFO_REQUEST = "/mainController?command=user_data&requestedUserInfoId=";

    /**
     * Represents URL pattern for employee info page.
     */
    public static final String EMPLOYEE_INFO = "/mainPage/employees/*";

    /**
     * Represents URL pattern for employee info request.
     */
    public static final String EMPLOYEE_INFO_REQUEST = "/mainController?command=employee_data&requestedEmployeeInfoId=";

    /**
     * Represents URL pattern for patient info page.
     */
    public static final String PATIENT_INFO = "/mainPage/patients/*";

    /**
     * Represents URL pattern for patient info request.
     */
    public static final String PATIENT_INFO_REQUEST = "/mainController?command=patient_data&requestedPatientInfoId=";

    /**
     * Represents URL pattern for page of changing password by admin.
     */
    public static final String ADMIN_CHANGING_PASSWORD = "/mainPage/users/*/changePassword";

    /**
     * Represents URL pattern for request of changing password by admin.
     */
    public static final String ADMIN_CHANGING_PASSWORD_REQUEST =
            "/mainController?command=redirect_to_updating_password&userId=";

    /**
     * Represents URL pattern for updating user page.
     */
    public static final String UPDATING_USER = "/mainPage/users/*/update";

    /**
     * Represents URL pattern for updating employee page.
     */
    public static final String UPDATING_EMPLOYEE = "/mainPage/employees/*/update";

    /**
     * Represents URL pattern for updating patient page.
     */
    public static final String UPDATING_PATIENT = "/mainPage/patients/*/update";

    /**
     * Represents URL pattern for updating user request.
     */
    public static final String UPDATING_USER_REQUEST =
            "/mainController?command=redirect_to_update_user_page&userId=";

    /**
     * Represents URL pattern for updating employee request.
     */
    public static final String UPDATING_EMPLOYEE_REQUEST =
            "/mainController?command=redirect_to_update_employee_page&employeeId=";

    /**
     * Represents URL pattern for updating patient request.
     */
    public static final String UPDATING_PATIENT_REQUEST =
            "/mainController?command=redirect_to_update_patient_page&patientId=";

    /**
     * Represents URL pattern for record list request.
     */
    public static final String RECORD_LIST_REQUEST = "/mainController?command=record_list_command&patientId=";

    /**
     * Represents URL pattern for record info page.
     */
    public static final String RECORD_INFO = "/mainPage/records/*";

    /**
     * Represents URL pattern for record info request.
     */
    public static final String RECORD_INFO_REQUEST = "/mainController?command=record_data&requestedRecordInfoId=";
}
