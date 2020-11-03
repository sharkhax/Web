package com.drobot.web.model.dao;

public final class ColumnName {

    private ColumnName() {
    }

    public static final char SEMICOLON = ';';

    /**
     * Users' table
     */
    public static final String USER_ID = "user_id";
    public static final String USER_LOGIN = "login";
    public static final String USER_EMAIL = "email";
    public static final String USER_ROLE = "role";
    public static final String USER_STATUS = "user_status";

    /**
     * Employees' table
     */
    public static final String EMPLOYEE_ID = "employee_id";
    public static final String EMPLOYEE_NAME = "employee_name";
    public static final String EMPLOYEE_SURNAME = "employee_surname";
    public static final String EMPLOYEE_AGE = "employee_age";
    public static final String EMPLOYEE_GENDER = "employee_gender";
    public static final String EMPLOYEE_POSITION = "position";
    public static final String EMPLOYEE_HIRE_DATE = "hire_date";
    public static final String EMPLOYEE_DISMISS_DATE = "dismiss_date";
    public static final String EMPLOYEE_STATUS = "employee_status";

    // patients' table
    public static final String PATIENT_ID = "patient_id";
    public static final String PATIENT_NAME = "patient_name";
    public static final String PATIENT_SURNAME = "patient_surname";
    public static final String PATIENT_AGE = "patient_age";
    public static final String PATIENT_GENDER = "patient_gender";
    public static final String PATIENT_DIAGNOSIS = "diagnosis";
    public static final String PATIENT_STATUS = "patient_status";
}
