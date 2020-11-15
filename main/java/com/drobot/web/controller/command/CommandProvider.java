package com.drobot.web.controller.command;

import com.drobot.web.controller.RequestParameter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Class used to work with commands.
 *
 * @author Vladislav Drobot
 */
public class CommandProvider {

    private static final Logger LOGGER = LogManager.getLogger(CommandProvider.class);

    private CommandProvider() {
    }

    /**
     * Defines an ActionCommand from the request.
     *
     * @param request HttpServletRequest object, which may contain the ActionCommand.
     * @return empty Optional object if command is empty or not present in the request,
     * otherwise - Optional object of ActionCommand object.
     */
    public static Optional<ActionCommand> defineCommand(HttpServletRequest request) {
        Optional<ActionCommand> result;
        String stringCommand = request.getParameter(RequestParameter.COMMAND);
        if (stringCommand != null && !stringCommand.isBlank()) {
            try {
                CommandType commandType = CommandType.valueOf(stringCommand.toUpperCase());
                ActionCommand command = commandType.getCommand();
                result = Optional.of(command);
                LOGGER.log(Level.DEBUG, "Command has been defined: " + commandType);
            } catch (IllegalArgumentException e) {
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
