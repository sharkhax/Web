package com.drobot.web.controller.command;

import com.drobot.web.controller.command.impl.LoginCommand;
import com.drobot.web.controller.command.impl.LogoutCommand;
import com.drobot.web.controller.command.impl.RegisterCommand;

public enum CommandType {
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTER(new RegisterCommand());

    private final ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCommand() {
        return command;
    }
}
