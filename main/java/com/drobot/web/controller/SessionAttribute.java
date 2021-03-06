package com.drobot.web.controller;

/**
 * Class represents constant names of session attributes.
 *
 * @author Vladislav Drobot
 */
public final class SessionAttribute {

    private SessionAttribute() {
    }

    /**
     * Represents message about incorrect login or password at login page.
     */
    public static final String WRONG_LOGIN_PASSWORD = "invalidLoginOrPass";

    /**
     * Represents message that user is blocked at login page.
     */
    public static final String USER_BLOCKED = "userBlocked";

    /**
     * The attribute is used in different registration or updating forms.
     */
    public static final String VALIDATED = "validated";

    /**
     * Represents map of user's fields at registration form.
     */
    public static final String USER_REGISTRATION_FIELDS = "userRegistrationFields";

    /**
     * Represents map of user's fields, that already exist.
     */
    public static final String USER_REGISTRATION_EXISTING_FIELDS = "userRegistrationExistingFields";

    /**
     * Represents logged user's role.
     */
    public static final String USER_ROLE = "role";

    /**
     * Represents logged user's id.
     */
    public static final String USER_ID = "userId";

    /**
     * Represents user's role "Admin".
     */
    public static final String ADMIN_ROLE = "admin_role";

    /**
     * Represents user's role "Doctor".
     */
    public static final String DOCTOR_ROLE = "doctor_role";

    /**
     * Represents user's role "Assistant".
     */
    public static final String ASSISTANT_ROLE = "assistant_role";

    /**
     * Represents user's role "Guest".
     */
    public static final String GUEST_ROLE = "guest_role";

    /**
     * Represents current page uri.
     */
    public static final String CURRENT_PAGE = "currentPage";

    /**
     * Represents current locale.
     */
    public static final String CURRENT_LOCALE = "currentLocale";

    /**
     * Represents default locale.
     */
    public static final String DEFAULT_LOCALE = "en_EN";

    /**
     * Represents map, that contains logged user's ID and login.
     */
    public static final String LOGIN_INFO = "login_info";

    /**
     * Represents map, that contains user's data at personal settings page.
     */
    public static final String PERSONAL_SETTINGS_DATA = "personalSettingsData";

    /**
     * Represents map, that contains fields at password changing form.
     */
    public static final String CHANGING_PASSWORD_FIELDS = "changingPasswordFields";

    /**
     * Represents list of users at appropriate page.
     */
    public static final String USER_LIST = "user_list";

    /**
     * Represents number of current page at list of users page.
     */
    public static final String USER_LIST_CURRENT_PAGE = "userListCurrentPage";

    /**
     * Represents sorting tag for list of users at appropriate page.
     */
    public static final String USER_LIST_SORT_BY = "userListSortBy";

    /**
     * Represents list of employees at appropriate page.
     */
    public static final String EMPLOYEE_LIST = "employee_list";

    /**
     * Represents number of current page at list of employees page.
     */
    public static final String EMPLOYEE_LIST_CURRENT_PAGE = "employeeListCurrentPage";

    /**
     * Represents number of current page at list of records page.
     */
    public static final String RECORD_LIST_CURRENT_PAGE = "recordListCurrentPage";

    /**
     * Represents sorting tag for list of employees at appropriate page.
     */
    public static final String EMPLOYEE_LIST_SORT_BY = "employeeListSortBy";

    /**
     * Represents list of patients at appropriate page.
     */
    public static final String PATIENT_LIST = "patient_list";

    /**
     * Represents list of records at appropriate page.
     */
    public static final String RECORD_LIST = "record_list";

    /**
     * Represents number of current page at list of patients page.
     */
    public static final String PATIENT_LIST_CURRENT_PAGE = "patientListCurrentPage";

    /**
     * Represents sorting tag for list of patients at appropriate page.
     */
    public static final String PATIENT_LIST_SORT_BY = "patientListSortBy";

    /**
     * Represents sorting tag for list of records at appropriate page.
     */
    public static final String RECORD_LIST_SORT_BY = "recordListSortBy";

    /**
     * Represents general number of users at user list page.
     */
    public static final String USERS_NUMBER = "usersNumber";

    /**
     * Represents general number of employees at employee list page.
     */
    public static final String EMPLOYEES_NUMBER = "employeesNumber";

    /**
     * Represents general number of patients at patient list page.
     */
    public static final String PATIENTS_NUMBER = "patientsNumber";

    /**
     * Represents general number of records at patient list page.
     */
    public static final String RECORDS_NUMBER = "recordsNumber";

    /**
     * Represents flag for reserve sorting at user list page.
     */
    public static final String USER_LIST_REVERSE_SORTING = "userListReverseSorting";

    /**
     * Represents flag for reserve sorting at patient list page.
     */
    public static final String PATIENT_LIST_REVERSE_SORTING = "patientListReverseSorting";

    /**
     * Represents flag for reserve sorting at employee list page.
     */
    public static final String EMPLOYEE_LIST_REVERSE_SORTING = "employeeListReverseSorting";

    /**
     * Represents flag for reserve sorting at record list page.
     */
    public static final String RECORD_LIST_REVERSE_SORTING = "recordListReverseSorting";

    /**
     * Represents map of patient's fields at patient creating page.
     */
    public static final String PATIENT_CREATING_FIELDS = "patientCreatingFields";

    /**
     * Represents map of existing patient's fields at patient creating page.
     */
    public static final String PATIENT_CREATING_EXISTING_FIELDS = "patientCreatingExistingFields";

    /**
     * Represents flag for message about successful patient creating.
     */
    public static final String PATIENT_CREATING_SUCCESS = "patientCreatingSuccess";

    /**
     * Represents map of patient's fields at patient's info page.
     */
    public static final String PATIENT_DATA_FIELDS = "patientDataFields";

    /**
     * Represents map of record's fields at record's info page.
     */
    public static final String RECORD_DATA_FIELDS = "recordDataFields";

    /**
     * Represents map of user's fields at user's info page.
     */
    public static final String USER_DATA_FIELDS = "userDataFields";

    /**
     * Represents map of employee's fields at employee's info page.
     */
    public static final String EMPLOYEE_DATA_FIELDS = "employeeDataFields";

    /**
     * Represents employee's ID at employee's info page.
     */
    public static final String EMPLOYEE_INFO_ID = "employeeInfoId";

    /**
     * Represents patient's ID at patient's info page.
     */
    public static final String PATIENT_INFO_ID = "patientInfoId";

    /**
     * Represents record's ID at record's info page.
     */
    public static final String RECORD_INFO_ID = "recordInfoId";

    /**
     * Represents user's ID at user's info page.
     */
    public static final String USER_INFO_ID = "userInfoId";

    /**
     * Flag that shows that concrete user exists. Should be removed from session in filter after the checking.
     */
    public static final String USER_EXISTS = "userExists";

    /**
     * Represents map of user's new fields at its updating form page.
     */
    public static final String USER_DATA_NEW_FIELDS = "userDataNewFields";

    /**
     * Represents map of user's new fields, that are already used, at its updating form page.
     */
    public static final String USER_DATA_EXISTING_FIELDS = "userDataExistingFields";

    /**
     * Represents map of user's fields, that were not updated, at its updating form page.
     */
    public static final String USER_DATA_EMPTY_FIELDS = "userDataEmptyFields";

    /**
     * Flag that shows that concrete employee exists. Should be removed from session in filter after checking.
     */
    public static final String EMPLOYEE_EXISTS = "employeeExists";

    /**
     * Represents map of employee's new fields at its updating form page.
     */
    public static final String EMPLOYEE_DATA_NEW_FIELDS = "employeeDataNewFields";

    /**
     * Represents map of employee's new fields, that are already used, at its updating form page.
     */
    public static final String EMPLOYEE_DATA_EXISTING_FIELDS = "employeeDataExistingFields";

    /**
     * Represents map of employee's fields, that were not updated, at its updating form page.
     */
    public static final String EMPLOYEE_DATA_EMPTY_FIELDS = "employeeDataEmptyFields";

    /**
     * Flag that shows that concrete patient exists. Should be removed from session in filter after checking.
     */
    public static final String PATIENT_EXISTS = "patientExists";

    /**
     * Represents map of patient's new fields, that are already, used at its updating form page.
     */
    public static final String PATIENT_DATA_EXISTING_FIELDS = "patientDataExistingFields";

    /**
     * Represents map of patient's new fields at its updating form page.
     */
    public static final String PATIENT_DATA_NEW_FIELDS = "patientDataNewFields";

    /**
     * Represents map of patient's fields, that were not updated, at its updating form page.
     */
    public static final String PATIENT_DATA_EMPTY_FIELDS = "patientDataEmptyFields";

    /**
     * Represents map of data for creating record page.
     */
    public static final String CREATING_RECORD_DATA = "recordCreating";

    /**
     * Flag that shows that record creation is allowed. Should be removed from session in filter after checking.
     */
    public static final String RECORD_CREATION_ALLOWED = "recordCreationAllowed";

    /**
     * Represents map of fields at record creating page.
     */
    public static final String RECORD_CREATING_FIELDS = "recordCreatingFields";
}
