package com.drobot.web.controller.command;

import com.drobot.web.controller.command.impl.*;

public enum CommandType {

    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTER_USER(new RegisterUserCommand()),
    UPDATE_EMPLOYEE(new UpdateEmployeeCommand()),
    USER_LIST_COMMAND(new UserListCommand()),
    EMPLOYEE_LIST_COMMAND(new EmployeeListCommand()),
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    CHANGE_PASSWORD(new ChangePasswordCommand()),
    SETTINGS_PAGE(new SettingsPageCommand()),
    RECORD_LIST_COMMAND(new RecordListCommand()),
    REDIRECT_TO_USER_REGISTRATION(new RedirectToUserRegistrationCommand()),
    CREATE_RECORD(new CreateRecordCommand()),
    REGISTER_PATIENT(new RegisterPatientCommand()),
    REDIRECT_TO_PATIENT_CREATING(new RedirectToPatientCreatingCommand()),
    PATIENT_LIST_COMMAND(new PatientListCommand()),
    PATIENT_DATA(new PatientDataCommand()),
    EMPLOYEE_DATA(new EmployeeDataCommand()),
    USER_DATA(new UserDataCommand()),
    REDIRECT_TO_CHANGING_PASSWORD(new RedirectToChangingPasswordCommand()),
    RECORD_DATA(new RecordDataCommand()),
    REDIRECT_TO_UPDATE_USER_PAGE(new RedirectToUpdateUserPageCommand()),
    UPDATE_USER_PASSWORD(new UpdateUserPasswordCommand()),
    REDIRECT_TO_UPDATING_PASSWORD(new RedirectToUpdatingPasswordCommand());

    private final ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCommand() {
        return command;
    }
}
