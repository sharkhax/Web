package com.drobot.web.controller;

/**
 * Represents constant names of request parameters and attributes.
 */
public final class RequestParameter {

    private RequestParameter() {
    }

    /**
     * Represents user's login.
     */
    public static final String LOGIN = "login";

    /**
     * Represents user's password.
     */
    public static final String PASSWORD = "password";

    /**
     * Represents user's email.
     */
    public static final String EMAIL = "email";

    /**
     * Represents message about successful user registration.
     */
    public static final String REGISTRATION_SUCCESS = "registrationSuccess";

    /**
     * Represents current password at changing password form.
     */
    public static final String CURRENT_PASSWORD = "currentPassword";

    /**
     * Represents new password at changing password form.
     */
    public static final String NEW_PASSWORD = "newPassword";

    /**
     * Represents employee's name field at registration or updating forms or appropriate lists.
     */
    public static final String EMPLOYEE_NAME = "employeeName";

    /**
     * Represents employee's surname field at registration or updating forms or appropriate lists.
     */
    public static final String EMPLOYEE_SURNAME = "employeeSurname";

    /**
     * Represents employee's age field at registration or updating forms or appropriate lists.
     */
    public static final String EMPLOYEE_AGE = "employeeAge";

    /**
     * Represents employee's gender field at registration or updating forms or appropriate lists.
     */
    public static final String EMPLOYEE_GENDER = "employeeGender";

    /**
     * Represents employee's position field at registration or updating forms or appropriate lists.
     */
    public static final String EMPLOYEE_POSITION = "employeePosition";

    /**
     * Represents employee's hire date field at registration or updating forms or appropriate lists.
     */
    public static final String HIRE_DATE = "hireDate";

    /**
     * Represents employee's dismiss date field at registration or updating forms or appropriate lists.
     */
    public static final String DISMISS_DATE = "dismissDate";

    /**
     * Represents employee's position "Doctor" field at registration or updating forms or appropriate lists.
     */
    public static final String DOCTOR = "doctor";

    /**
     * Represents employee's position "Doctor" field at registration or updating forms or appropriate lists.
     */
    public static final String ASSISTANT = "assistant";

    /**
     * Represents employee's status field at registration or updating forms or appropriate lists.
     */
    public static final String EMPLOYEE_STATUS = "employeeStatus";

    /**
     * Represents user's role field at registration or updating forms or appropriate lists.
     */
    public static final String USER_ROLE = "userRole";

    /**
     * Represents user's ID field at registration or updating forms or appropriate lists.
     */
    public static final String USER_ID = "userId";

    /**
     * Represents requested page number for lists at appropriate pages.
     */
    public static final String LIST_PAGE = "requestedListPage";

    /**
     * Represents requested sorting tag for lists at appropriate pages.
     */
    public static final String SORT_BY = "requestedSortBy";

    /**
     * Represents command from the request.
     */
    public static final String COMMAND = "command";

    /**
     * Represents patient's name field at registration or updating forms or appropriate lists.
     */
    public static final String PATIENT_NAME = "patientName";

    /**
     * Represents patient's surname field at registration or updating forms or appropriate lists.
     */
    public static final String PATIENT_SURNAME = "patientSurname";

    /**
     * Represents patient's age field at registration or updating forms or appropriate lists.
     */
    public static final String PATIENT_AGE = "patientAge";

    /**
     * Represents patient's gender field at registration or updating forms or appropriate lists.
     */
    public static final String PATIENT_GENDER = "patientGender";

    /**
     * Represents patient's ID field at registration or updating forms or appropriate lists.
     */
    public static final String PATIENT_ID = "patientId";

    /**
     * Represents patient's diagnosis field at registration or updating forms or appropriate lists.
     */
    public static final String PATIENT_DIAGNOSIS = "patientDiagnosis";

    /**
     * Represents patient's status field at registration or updating forms or appropriate lists.
     */
    public static final String PATIENT_STATUS = "patientStatus";

    /**
     * Represents patient's record ID field at registration or updating forms or appropriate lists.
     */
    public static final String LAST_RECORD_ID = "lastRecordId";

    /**
     * Represents user's status field at registration or updating forms or appropriate lists.
     */
    public static final String USER_STATUS = "userStatus";

    /**
     * Represents employee's ID field at registration or updating forms or appropriate lists.
     */
    public static final String EMPLOYEE_ID = "employeeId";

    /**
     * Represents requested employee's ID at employee's info page.
     */
    public static final String EMPLOYEE_INFO_ID = "requestedEmployeeInfoId";

    /**
     * Represents requested patient's ID at patient's info page.
     */
    public static final String PATIENT_INFO_ID = "requestedPatientInfoId";

    /**
     * Represents requested user's ID at user's info page.
     */
    public static final String USER_INFO_ID = "requestedUserInfoId";
}
