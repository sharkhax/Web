package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.JspPath;
import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.command.AccessType;
import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandAccessLevel;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@CommandAccessLevel({AccessType.ADMIN, AccessType.DOCTOR, AccessType.ASSISTANT})
public class LogoutCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String page = JspPath.LOGIN;
        HttpSession session = request.getSession();
        String currentLocale = (String) session.getAttribute(SessionAttribute.CURRENT_LOCALE);
        session.invalidate();
        session = request.getSession();
        session.setAttribute(SessionAttribute.CURRENT_LOCALE, currentLocale);
        LOGGER.log(Level.DEBUG, "Logged out");
        return page;
    }
}
