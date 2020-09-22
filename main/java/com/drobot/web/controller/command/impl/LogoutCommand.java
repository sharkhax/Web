package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.JspPath;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String page = JspPath.LOGIN;
        request.getSession().invalidate();
        LOGGER.log(Level.DEBUG, "Logged out");
        return page;
    }
}
