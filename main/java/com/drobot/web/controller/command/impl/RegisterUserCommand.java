package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.command.AccessType;
import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandAccessLevel;

import javax.servlet.http.HttpServletRequest;

@CommandAccessLevel({AccessType.ADMIN})
public class RegisterUserCommand implements ActionCommand {

    @Override
    public String execute(HttpServletRequest request) {
        return null;
    }
}
