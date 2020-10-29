package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CreateRecordCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(CreateRecordCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return null;
    }
}
