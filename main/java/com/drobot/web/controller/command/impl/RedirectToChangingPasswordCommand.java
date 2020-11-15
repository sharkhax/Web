package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.UrlPattern;
import com.drobot.web.controller.command.AccessType;
import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandAccessLevel;
import com.drobot.web.exception.CommandException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Action command for redirecting to changing password page.
 *
 * @author Vladislav Drobot
 */
@CommandAccessLevel({AccessType.ADMIN, AccessType.DOCTOR, AccessType.ASSISTANT})
public class RedirectToChangingPasswordCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(RedirectToChangingPasswordCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page = UrlPattern.CHANGING_PASSWORD;
        LOGGER.log(Level.DEBUG, "Redirected to changing password page");
        return page;
    }
}
