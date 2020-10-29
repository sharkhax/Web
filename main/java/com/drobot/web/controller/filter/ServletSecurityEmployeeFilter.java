package com.drobot.web.controller.filter;

import com.drobot.web.controller.JspPath;
import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.UrlPattern;
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

public class ServletSecurityEmployeeFilter extends AbstractSecurityFilter {

    private static final Logger LOGGER = LogManager.getLogger(ServletSecurityEmployeeFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        switch (uri) {
            case UrlPattern.RECORD_LIST -> doCaseRecordList(request, response, session); // TODO: 27.10.2020
            case UrlPattern.PERSONAL_SETTINGS -> doCasePersonalSettings(request, response, session);
            case UrlPattern.CHANGING_PASSWORD -> doCasePasswordChanging(request, response, session);
            case UrlPattern.PATIENT_CREATING -> doCasePatientCreating(request, response, session);
            case UrlPattern.PATIENT_CREATING_SUCCESS -> doCasePatientCreatingSuccess(request, response, session);
            case UrlPattern.PATIENT_CREATING_FAIL -> doCasePatientCreatingFail(request, response, session);
            case UrlPattern.PATIENT_LIST -> doCasePatientList(request, response, session);
            default -> filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private void doCaseRecordList(HttpServletRequest request, HttpServletResponse response,
                                  HttpSession session) throws IOException, ServletException {
        String userRole = (String) session.getAttribute(RequestParameter.USER_ROLE);
        String page = JspPath.RECORD_LIST;
        boolean condition = userRole != null && (userRole.equals(RequestParameter.ASSISTANT_ROLE)
                || userRole.equals(RequestParameter.DOCTOR_ROLE)
                || userRole.equals(RequestParameter.ADMIN_ROLE));
        forwardOrError404(condition, page, request, response, session);
    }

    private void doCasePersonalSettings(HttpServletRequest request, HttpServletResponse response,
                                        HttpSession session) throws IOException, ServletException {
        String userRole = (String) session.getAttribute(RequestParameter.USER_ROLE);
        String page = JspPath.PERSONAL_SETTINGS;
        boolean condition = userRole != null && (userRole.equals(RequestParameter.ASSISTANT_ROLE)
                || userRole.equals(RequestParameter.DOCTOR_ROLE)
                || userRole.equals(RequestParameter.ADMIN_ROLE));
        forwardOrError404(condition, page, request, response, session);
    }

    private void doCasePasswordChanging(HttpServletRequest request, HttpServletResponse response,
                                        HttpSession session) throws IOException, ServletException {
        String prevPage = request.getRequestURI();
        if (!prevPage.equals(UrlPattern.CHANGING_PASSWORD)) {
            session.setAttribute(RequestParameter.VALIDATED, null);
        }
        String userRole = (String) session.getAttribute(RequestParameter.USER_ROLE);
        String page = JspPath.PASSWORD_CHANGING;
        boolean condition = userRole != null && (userRole.equals(RequestParameter.ASSISTANT_ROLE)
                || userRole.equals(RequestParameter.DOCTOR_ROLE)
                || userRole.equals(RequestParameter.ADMIN_ROLE));
        forwardOrError404(condition, page, request, response, session);
    }

    private void doCasePatientCreating(HttpServletRequest request, HttpServletResponse response,
                                       HttpSession session) throws IOException, ServletException {
        session.setAttribute(RequestParameter.VALIDATED, null);
        session.setAttribute(RequestParameter.PATIENT_CREATING_EXISTING_FIELDS, null);
        session.setAttribute(RequestParameter.PATIENT_CREATING_FIELDS, null);
        forwardToPatientCreating(request, response, session);
    }

    private void doCasePatientCreatingFail(HttpServletRequest request, HttpServletResponse response,
                                           HttpSession session) throws IOException, ServletException {
        session.setAttribute(RequestParameter.VALIDATED, true);
        forwardToPatientCreating(request, response, session);
    }

    private void doCasePatientCreatingSuccess(HttpServletRequest request, HttpServletResponse response,
                                           HttpSession session) throws IOException, ServletException {
        request.setAttribute(RequestParameter.PATIENT_CREATING_SUCCESS, true);
        session.setAttribute(RequestParameter.VALIDATED, null);
        session.setAttribute(RequestParameter.PATIENT_CREATING_EXISTING_FIELDS, null);
        session.setAttribute(RequestParameter.PATIENT_CREATING_FIELDS, null);
        forwardToPatientCreating(request, response, session);
    }

    private void forwardToPatientCreating(HttpServletRequest request, HttpServletResponse response,
                                              HttpSession session) throws IOException, ServletException {
        String userRole = (String) session.getAttribute(RequestParameter.USER_ROLE);
        String page = JspPath.PATIENT_CREATING;
        boolean condition = userRole != null && (userRole.equals(RequestParameter.ADMIN_ROLE)
                || userRole.equals(RequestParameter.DOCTOR_ROLE));
        forwardOrError404(condition, page, request, response, session);
    }

    private void doCasePatientList(HttpServletRequest request, HttpServletResponse response,
                                   HttpSession session) throws IOException, ServletException {

    }
}
