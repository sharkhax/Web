package com.drobot.web.controller.listener;

import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.UrlPattern;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Web listener of session creation.
 *
 * @author Vladislav Drobot
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(SessionAttribute.USER_ROLE, SessionAttribute.GUEST_ROLE);
        session.setAttribute(SessionAttribute.CURRENT_LOCALE, SessionAttribute.DEFAULT_LOCALE);
        session.setAttribute(SessionAttribute.CURRENT_PAGE, UrlPattern.LOGIN_PAGE);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }
}
