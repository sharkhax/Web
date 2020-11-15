package com.drobot.web.controller.filter;

import com.drobot.web.controller.JspPath;
import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.UrlPattern;
import com.drobot.web.controller.command.CommandType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ServletSecurityAdminFilter extends AbstractSecurityFilter {

    private static final Logger LOGGER = LogManager.getLogger(ServletSecurityAdminFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        switch (uri) {
            case UrlPattern.USER_REGISTRATION -> doCaseUserRegistration(request, response, session);
            case UrlPattern.USER_REGISTRATION_SUCCESS -> doCaseUserRegistrationSuccess(request, response, session);
            case UrlPattern.USER_REGISTRATION_FAIL -> doCaseUserRegistrationFail(request, response, session);
            case UrlPattern.EMPLOYEE_LIST -> doCaseEmployeeList(request, response, session);
            case UrlPattern.USER_LIST -> doCaseUserList(request, response, session);
            default -> filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private void doCaseUserRegistration(HttpServletRequest request, HttpServletResponse response,
                                        HttpSession session) throws IOException, ServletException {
        session.setAttribute(SessionAttribute.VALIDATED, null);
        session.setAttribute(SessionAttribute.USER_REGISTRATION_FIELDS, null);
        session.setAttribute(SessionAttribute.USER_REGISTRATION_EXISTING_FIELDS, null);
        forwardToRegistration(request, response, session);
    }

    private void doCaseUserRegistrationSuccess(HttpServletRequest request, HttpServletResponse response,
                                               HttpSession session) throws IOException, ServletException {
        request.setAttribute(RequestParameter.REGISTRATION_SUCCESS, true);
        session.setAttribute(SessionAttribute.VALIDATED, null);
        session.setAttribute(SessionAttribute.USER_REGISTRATION_FIELDS, null);
        session.setAttribute(SessionAttribute.USER_REGISTRATION_EXISTING_FIELDS, null);
        forwardToRegistration(request, response, session);
    }

    private void doCaseUserRegistrationFail(HttpServletRequest request, HttpServletResponse response,
                                            HttpSession session) throws IOException, ServletException {
        session.setAttribute(SessionAttribute.VALIDATED, true);
        forwardToRegistration(request, response, session);
    }

    private void forwardToRegistration(HttpServletRequest request, HttpServletResponse response,
                                       HttpSession session) throws IOException, ServletException {
        String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
        String page = JspPath.USER_REGISTRATION;
        boolean condition = userRole.equals(SessionAttribute.ADMIN_ROLE);
        forwardOrError404(condition, page, request, response, session);
    }

    private void doCaseEmployeeList(HttpServletRequest request, HttpServletResponse response,
                                    HttpSession session) throws IOException, ServletException {
        String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
        String page = JspPath.EMPLOYEE_LIST;
        if (session.getAttribute(SessionAttribute.EMPLOYEE_LIST) == null) {
            forward(request, response, UrlPattern.EMPLOYEE_LIST_REQUEST);
        } else {
            boolean condition = userRole.equals(SessionAttribute.ADMIN_ROLE);
            forwardOrError404(condition, page, request, response, session);
        }
    }

    private void doCaseUserList(HttpServletRequest request, HttpServletResponse response,
                                HttpSession session) throws IOException, ServletException {
        String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
        String page = JspPath.USER_LIST;
        if (session.getAttribute(SessionAttribute.USER_LIST) == null) {
            forward(request, response, UrlPattern.USER_LIST_REQUEST);
        } else {
            boolean condition = userRole.equals(SessionAttribute.ADMIN_ROLE);
            forwardOrError404(condition, page, request, response, session);
        }
    }
}
