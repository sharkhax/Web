package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.command.AccessType;
import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandAccessLevel;
import com.drobot.web.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

@CommandAccessLevel(AccessType.ADMIN)
public class UserListCommand implements ActionCommand {

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return null;
    }
}
