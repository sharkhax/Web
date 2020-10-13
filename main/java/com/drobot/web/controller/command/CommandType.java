package com.drobot.web.controller.command;

import com.drobot.web.controller.command.impl.*;

public enum CommandType {

    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTER_USER(new RegisterUserCommand()),
    REGISTER_EMPLOYEE(new RegisterEmployeeCommand()),
    UPDATE_EMPLOYEE(new UpdateEmployeeCommand()),
    USER_LIST(new UserListCommand()),
    EMPLOYEE_LIST(new EmployeeListCommand()),
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    REDIRECT_COMMAND(new RedirectCommand());

    private final ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCommand() {
        return command;
    }
}
