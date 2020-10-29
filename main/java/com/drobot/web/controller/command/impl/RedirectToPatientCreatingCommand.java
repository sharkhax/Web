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

@CommandAccessLevel({AccessType.ADMIN, AccessType.DOCTOR})
public class RedirectToPatientCreatingCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(RedirectToPatientCreatingCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page = UrlPattern.PATIENT_CREATING;
        LOGGER.log(Level.DEBUG, "Redirected to patient creating");
        return page;
    }
}
