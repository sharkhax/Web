package com.drobot.web.controller.command;

import com.drobot.web.controller.command.impl.*;

/**
 * ActionCommand's implementations enumeration.
 *
 * @author Vladislav Drobot
 */
public enum CommandType {

    /**
     * Represents LoginCommand implementation of ActionCommand interface.
     */
    LOGIN(new LoginCommand()),

    /**
     * Represents LogoutCommand implementation of ActionCommand interface.
     */
    LOGOUT(new LogoutCommand()),

    /**
     * Represents RegisterUserCommand implementation of ActionCommand interface.
     */
    REGISTER_USER(new RegisterUserCommand()),

    /**
     * Represents UpdateEmployeeCommand implementation of ActionCommand interface.
     */
    UPDATE_EMPLOYEE(new UpdateEmployeeCommand()),

    /**
     * Represents UserListCommand implementation of ActionCommand interface.
     */
    USER_LIST_COMMAND(new UserListCommand()),

    /**
     * Represents EmployeeListCommand implementation of ActionCommand interface.
     */
    EMPLOYEE_LIST_COMMAND(new EmployeeListCommand()),

    /**
     * Represents ChangeLocaleCommand implementation of ActionCommand interface.
     */
    CHANGE_LOCALE(new ChangeLocaleCommand()),

    /**
     * Represents ChangePasswordCommand implementation of ActionCommand interface.
     */
    CHANGE_PASSWORD(new ChangePasswordCommand()),

    /**
     * Represents SettingsPageCommand implementation of ActionCommand interface.
     */
    SETTINGS_PAGE(new SettingsPageCommand()),

    /**
     * Represents RecordListCommand implementation of ActionCommand interface.
     */
    RECORD_LIST_COMMAND(new RecordListCommand()),

    /**
     * Represents RedirectToUserRegistrationCommand implementation of ActionCommand interface.
     */
    REDIRECT_TO_USER_REGISTRATION(new RedirectToUserRegistrationCommand()),

    /**
     * Represents CreateRecordCommand implementation of ActionCommand interface.
     */
    CREATE_RECORD(new CreateRecordCommand()),

    /**
     * Represents RegisterPatientCommand implementation of ActionCommand interface.
     */
    REGISTER_PATIENT(new RegisterPatientCommand()),

    /**
     * Represents RedirectToPatientCreatingCommand implementation of ActionCommand interface.
     */
    REDIRECT_TO_PATIENT_CREATING(new RedirectToPatientCreatingCommand()),

    /**
     * Represents PatientListCommand implementation of ActionCommand interface.
     */
    PATIENT_LIST_COMMAND(new PatientListCommand()),

    /**
     * Represents PatientDataCommand implementation of ActionCommand interface.
     */
    PATIENT_DATA(new PatientDataCommand()),

    /**
     * Represents EmployeeDataCommand implementation of ActionCommand interface.
     */
    EMPLOYEE_DATA(new EmployeeDataCommand()),

    /**
     * Represents UserDataCommand implementation of ActionCommand interface.
     */
    USER_DATA(new UserDataCommand()),

    /**
     * Represents RedirectToChangingPasswordCommand implementation of ActionCommand interface.
     */
    REDIRECT_TO_CHANGING_PASSWORD(new RedirectToChangingPasswordCommand()),

    /**
     * Represents RecordDataCommand implementation of ActionCommand interface.
     */
    RECORD_DATA(new RecordDataCommand()),

    /**
     * Represents RedirectToUpdateUserCommand implementation of ActionCommand interface.
     */
    REDIRECT_TO_UPDATE_USER_PAGE(new RedirectToUpdateUserCommand()),

    /**
     * Represents UpdateUserPasswordCommand implementation of ActionCommand interface.
     */
    UPDATE_USER_PASSWORD(new UpdateUserPasswordCommand()),

    /**
     * Represents RedirectToUpdatingPasswordCommand implementation of ActionCommand interface.
     */
    REDIRECT_TO_UPDATING_PASSWORD(new RedirectToUpdatingPasswordCommand()),

    /**
     * Represents UpdateUserCommand implementation of ActionCommand interface.
     */
    UPDATE_USER(new UpdateUserCommand()),

    /**
     * Represents RedirectToUpdateEmployeeCommand implementation of ActionCommand interface.
     */
    REDIRECT_TO_UPDATE_EMPLOYEE_PAGE(new RedirectToUpdateEmployeeCommand()),

    /**
     * Represents UnblockUserCommand implementation of ActionCommand interface.
     */
    UNBLOCK_USER(new UnblockUserCommand()),

    /**
     * Represents BlockUserCommand implementation of ActionCommand interface.
     */
    BLOCK_USER(new BlockUserCommand()),

    /**
     * Represents FireEmployeeCommand implementation of ActionCommand interface.
     */
    FIRE_EMPLOYEE(new FireEmployeeCommand()),

    /**
     * Represents RestoreEmployeeCommand implementation of ActionCommand interface.
     */
    RESTORE_EMPLOYEE(new RestoreEmployeeCommand()),

    /**
     * Represents SendToVacationCommand implementation of ActionCommand interface.
     */
    SEND_TO_VACATION(new SendToVacationCommand()),

    /**
     * Represents ReturnFromVacationCommand implementation of ActionCommand interface.
     */
    RETURN_FROM_VACATION(new ReturnFromVacationCommand()),

    /**
     * Represents UpdatePatientCommand implementation of ActionCommand interface.
     */
    UPDATE_PATIENT(new UpdatePatientCommand()),

    /**
     * Represents RedirectToUpdatePatientCommand implementation of ActionCommand interface.
     */
    REDIRECT_TO_UPDATE_PATIENT_PAGE(new RedirectToUpdatePatientCommand()),

    /**
     * Represents RedirectToRecordCreatingCommand implementation of ActionCommand interface.
     */
    REDIRECT_TO_RECORD_CREATING_PAGE(new RedirectToRecordCreatingCommand()),

    /**
     * Represents DischargePatientCommand implementation of ActionCommand interface.
     */
    DISCHARGE_PATIENT(new DischargePatientCommand()),

    /**
     * Represents ExecuteProcedureCommand implementation of ActionCommand interface.
     */
    EXECUTE_PROCEDURE(new ExecuteProcedureCommand()),

    /**
     * Represents ExecuteSurgeryCommand implementation of ActionCommand interface.
     */
    EXECUTE_SURGERY(new ExecuteSurgeryCommand());

    private final ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    /**
     * Getter method of ActionCommand's implementation.
     * @return implementation of ActionCommand interface.
     */
    public ActionCommand getCommand() {
        return command;
    }
}
