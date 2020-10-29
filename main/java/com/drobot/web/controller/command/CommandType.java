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
    PATIENT_LIST_COMMAND(new PatientListCommand());

    private final ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCommand() {
        return command;
    }
}
