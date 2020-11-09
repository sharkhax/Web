package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandAccessLevel;
import com.drobot.web.controller.RequestParameter;
import com.drobot.web.exception.CommandException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@CommandAccessLevel
public class ChangeLocaleCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ChangeLocaleCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String newLocale = request.getParameter(SessionAttribute.CURRENT_LOCALE);
        String page;
        if (newLocale != null) {
            page = (String) session.getAttribute(SessionAttribute.CURRENT_PAGE);
            session.setAttribute(SessionAttribute.CURRENT_LOCALE, newLocale);
            LOGGER.log(Level.DEBUG, "Locale was changed");
        } else {
            LOGGER.log(Level.ERROR, "Locale parameter is null");
            throw new CommandException("Locale parameter is null");
        }
        return page;
    }
}
