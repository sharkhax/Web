package com.drobot.web.controller.listener;

import com.drobot.web.controller.UrlPattern;
import com.drobot.web.controller.RequestParameter;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(RequestParameter.USER_ROLE, RequestParameter.GUEST_ROLE);
        session.setAttribute(RequestParameter.CURRENT_LOCALE, RequestParameter.DEFAULT_LOCALE);
        session.setAttribute(RequestParameter.CURRENT_PAGE, UrlPattern.LOGIN_PAGE);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }
}
