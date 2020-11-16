package com.drobot.web.model.dao;

/**
 * Class contains constant names of tables' columns.
 *
 * @author Vladislav Drobot
 */
public final class ColumnName {

    private ColumnName() {
    }

    /**
     * Represents user's ID column at user table.
     */
    public static final String USER_ID = "user_id";

    /**
     * Represents user's login column at user table.
     */
    public static final String USER_LOGIN = "login";

    /**
     * Represents user's email column at user table.
     */
    public static final String USER_EMAIL = "email";

    /**
     * Represents user's role column at user table.
     */
    public static final String USER_ROLE = "role";

    /**
     * Represents user's status column at user table.
     */
    public static final String USER_STATUS = "user_status";

    /**
     * Represents employee's ID column at employee table.
     */
    public static final String EMPLOYEE_ID = "employee_id";

    /**
     * Represents employee's name column at employee table.
     */
    public static final String EMPLOYEE_NAME = "employee_name";

    /**
     * Represents employee's surname column at employee table.
     */
    public static final String EMPLOYEE_SURNAME = "employee_surname";

    /**
     * Represents employee's age column at employee table.
     */
    public static final String EMPLOYEE_AGE = "employee_age";

    /**
     * Represents employee's gender column at employee table.
     */
    public static final String EMPLOYEE_GENDER = "employee_gender";

    /**
     * Represents employee's position column at employee table.
     */
    public static final String EMPLOYEE_POSITION = "position";

    /**
     * Represents employee's hire date column at employee table.
     */
    public static final String EMPLOYEE_HIRE_DATE = "hire_date";

    /**
     * Represents employee's dismiss date column at employee table.
     */
    public static final String EMPLOYEE_DISMISS_DATE = "dismiss_date";

    /**
     * Represents employee's status column at employee table.
     */
    public static final String EMPLOYEE_STATUS = "employee_status";

    /**
     * Represents patient's ID column at patient table.
     */
    public static final String PATIENT_ID = "patient_id";

    /**
     * Represents patient's name column at patient table.
     */
    public static final String PATIENT_NAME = "patient_name";

    /**
     * Represents patient's surname column at patient table.
     */
    public static final String PATIENT_SURNAME = "patient_surname";

    /**
     * Represents patient's age column at patient table.
     */
    public static final String PATIENT_AGE = "patient_age";

    /**
     * Represents patient's gender column at patient table.
     */
    public static final String PATIENT_GENDER = "patient_gender";

    /**
     * Represents patient's status column at patient table.
     */
    public static final String PATIENT_STATUS = "patient_status";

    /**
     * Represents record's ID column at record table.
     */
    public static final String RECORD_ID = "record_id";

    /**
     * Represents treatment's name column at treatment table.
     */
    public static final String TREATMENT_NAME = "treatment_name";

    /**
     * Represents record's diagnosis column at record table.
     */
    public static final String DIAGNOSIS = "diagnosis";

    /**
     * Represents user's ID column at user-employee table.
     */
    public static final String INTER_USER_ID = "inter_user_id";

    /**
     * Represents employee's ID column at user-employee table.
     */
    public static final String INTER_EMPLOYEE_ID = "inter_employee_id";

    /**
     * Represents executor's name field at specified record.
     */
    public static final String EXECUTOR_NAME = "executor_name";

    /**
     * Represents doctor's name field at specified record.
     */
    public static final String DOCTOR_NAME = "doctor_name";
}
