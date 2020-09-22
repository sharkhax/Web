package com.drobot.web.controller.command;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class CommandProvider {

    private static final Logger LOGGER = LogManager.getLogger(CommandProvider.class);
    private static final String WRONG_ACTION_MSG = "Error: wrong action";

    private CommandProvider() {
    }

    public static Optional<ActionCommand> defineCommand(HttpServletRequest request) {
        Optional<ActionCommand> result;
        String stringCommand = request.getParameter(RequestParameter.COMMAND);
        if (stringCommand != null && !stringCommand.isBlank()) {
            try {
                CommandType commandType = CommandType.valueOf(stringCommand.toUpperCase());
                ActionCommand command = commandType.getCommand();
                result = Optional.of(command);
                LOGGER.log(Level.DEBUG, "Command has been defined");
            } catch (IllegalArgumentException e) {
                request.setAttribute(RequestParameter.WRONG_ACTION, WRONG_ACTION_MSG);
                LOGGER.log(Level.WARN, "Unsupported command");
                result = Optional.empty();
            }
        } else {
            LOGGER.log(Level.WARN, "Command is null or empty");
            result = Optional.empty();
        }
        return result;
    }
}
