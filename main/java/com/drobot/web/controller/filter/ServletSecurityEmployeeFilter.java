package com.drobot.web.controller.filter;

import com.drobot.web.controller.JspPath;
import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.UrlPattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Web filter used to check access for requesting pages that available for employees or higher, except signing in.
 *
 * @author Vladislav Drobot
 */
public class ServletSecurityEmployeeFilter extends AbstractSecurityFilter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        switch (uri) {
            case UrlPattern.PERSONAL_SETTINGS -> doCasePersonalSettings(request, response, session);
            case UrlPattern.CHANGING_PASSWORD -> doCasePasswordChanging(request, response, session);
            case UrlPattern.PATIENT_CREATING -> doCasePatientCreating(request, response, session);
            case UrlPattern.PATIENT_CREATING_SUCCESS -> doCasePatientCreatingSuccess(request, response, session);
            case UrlPattern.PATIENT_CREATING_FAIL -> doCasePatientCreatingFail(request, response, session);
            case UrlPattern.PATIENT_LIST -> doCasePatientList(request, response, session);
            default -> filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private void doCasePersonalSettings(HttpServletRequest request, HttpServletResponse response,
                                        HttpSession session) throws IOException, ServletException {
        String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
        String page = JspPath.PERSONAL_SETTINGS;
        if (session.getAttribute(SessionAttribute.PERSONAL_SETTINGS_DATA) == null) {
            forward(request, response, UrlPattern.PERSONAL_SETTINGS_REQUEST);
        } else {
            boolean condition = userRole != null && (userRole.equals(SessionAttribute.ASSISTANT_ROLE)
                    || userRole.equals(SessionAttribute.DOCTOR_ROLE)
                    || userRole.equals(SessionAttribute.ADMIN_ROLE));
            forwardOrError404(condition, page, request, response, session);
        }
    }

    private void doCasePasswordChanging(HttpServletRequest request, HttpServletResponse response,
                                        HttpSession session) throws IOException, ServletException {
        String prevPage = (String) session.getAttribute(SessionAttribute.CURRENT_PAGE);
        if (!prevPage.equals(UrlPattern.CHANGING_PASSWORD)) {
            session.setAttribute(SessionAttribute.VALIDATED, null);
        }
        String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
        String page = JspPath.PASSWORD_CHANGING;
        boolean condition = userRole != null && (userRole.equals(SessionAttribute.ASSISTANT_ROLE)
                || userRole.equals(SessionAttribute.DOCTOR_ROLE)
                || userRole.equals(SessionAttribute.ADMIN_ROLE));
        forwardOrError404(condition, page, request, response, session);
    }

    private void doCasePatientCreating(HttpServletRequest request, HttpServletResponse response,
                                       HttpSession session) throws IOException, ServletException {
        session.setAttribute(SessionAttribute.VALIDATED, null);
        session.setAttribute(SessionAttribute.PATIENT_CREATING_EXISTING_FIELDS, null);
        session.setAttribute(SessionAttribute.PATIENT_CREATING_FIELDS, null);
        forwardToPatientCreating(request, response, session);
    }

    private void doCasePatientCreatingFail(HttpServletRequest request, HttpServletResponse response,
                                           HttpSession session) throws IOException, ServletException {
        session.setAttribute(SessionAttribute.VALIDATED, true);
        forwardToPatientCreating(request, response, session);
    }

    private void doCasePatientCreatingSuccess(HttpServletRequest request, HttpServletResponse response,
                                              HttpSession session) throws IOException, ServletException {
        request.setAttribute(SessionAttribute.PATIENT_CREATING_SUCCESS, true);
        session.setAttribute(SessionAttribute.VALIDATED, null);
        session.setAttribute(SessionAttribute.PATIENT_CREATING_EXISTING_FIELDS, null);
        session.setAttribute(SessionAttribute.PATIENT_CREATING_FIELDS, null);
        forwardToPatientCreating(request, response, session);
    }

    private void forwardToPatientCreating(HttpServletRequest request, HttpServletResponse response,
                                          HttpSession session) throws IOException, ServletException {
        String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
        String page = JspPath.PATIENT_CREATING;
        boolean condition = userRole != null && (userRole.equals(SessionAttribute.ADMIN_ROLE)
                || userRole.equals(SessionAttribute.DOCTOR_ROLE));
        forwardOrError404(condition, page, request, response, session);
    }

    private void doCasePatientList(HttpServletRequest request, HttpServletResponse response,
                                   HttpSession session) throws IOException, ServletException {
        String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
        String page = JspPath.PATIENT_LIST;
        if (session.getAttribute(SessionAttribute.PATIENT_LIST) == null) {
            forward(request, response, UrlPattern.PATIENT_LIST_REQUEST);
        } else {
            boolean condition = userRole != null && !userRole.equals(SessionAttribute.GUEST_ROLE);
            forwardOrError404(condition, page, request, response, session);
        }
    }
}
