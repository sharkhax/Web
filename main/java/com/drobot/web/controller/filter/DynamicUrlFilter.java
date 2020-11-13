package com.drobot.web.controller.filter;

import com.drobot.web.controller.JspPath;
import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.UrlPattern;
import com.drobot.web.controller.command.CommandType;
import org.apache.logging.log4j.Level;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DynamicUrlFilter extends AbstractSecurityFilter {

    private static final Logger LOGGER = LogManager.getLogger(DynamicUrlFilter.class);
    private static final String NUMBER_REGEX = "[0-9]+";

    enum DynamicUrl {

        USER_INFO("/mainPage/users/[0-9]+"),
        EMPLOYEE_INFO("/mainPage/employees/[0-9]+"),
        PATIENT_INFO("/mainPage/patients/[0-9]+"),
        CHANGING_PASSWORD("/mainPage/users/[0-9]+/changePassword"),
        UPDATING_USER("/mainPage/users/[0-9]+/update"),
        UPDATING_EMPLOYEE("/mainPage/employees/[0-9]+/update"),
        UPDATING_PATIENT("/mainPage/patients/[0-9]+/update");

        private final String pattern;

        DynamicUrl(String pattern) {
            this.pattern = pattern;
        }

        public String getPattern() {
            return pattern;
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        DynamicUrl dynamicUrl = defineUrl(uri);
        if (dynamicUrl != null) {
            switch (dynamicUrl) {
                case USER_INFO -> doCaseUserInfo(request, response, session);
                case PATIENT_INFO -> doCasePatientInfo(request, response, session);
                case EMPLOYEE_INFO -> doCaseEmployeeInfo(request, response, session);
                case CHANGING_PASSWORD -> doCaseChangingPassword(request, response, session);
                case UPDATING_USER -> doCaseUpdatingUser(request, response, session);
                case UPDATING_EMPLOYEE -> doCaseUpdatingEmployee(request, response, session);
                case UPDATING_PATIENT -> doCaseUpdatingPatient(request, response, session);
                default -> throw new EnumConstantNotPresentException(DynamicUrl.class, dynamicUrl.name());
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private DynamicUrl defineUrl(String uri) {
        DynamicUrl[] enums = DynamicUrl.values();
        DynamicUrl result = null;
        for (DynamicUrl dynamicUrl : enums) {
            if (uri.matches(dynamicUrl.getPattern())) {
                result = dynamicUrl;
                break;
            }
        }
        return result;
    }

    private int defineIdFromUri(String uri) {
        Pattern pattern = Pattern.compile(NUMBER_REGEX);
        Matcher matcher = pattern.matcher(uri);
        String stringId = null;
        int result;
        if (matcher.find()) {
            stringId = matcher.group();
        }
        if (stringId != null) {
            result = Integer.parseInt(stringId);
        } else {
            LOGGER.log(Level.FATAL, "Illegal requested URI");
            throw new IllegalArgumentException("Illegal requested URI");
        }
        return result;
    }

    private void doCaseUserInfo(HttpServletRequest request, HttpServletResponse response,
                                HttpSession session) throws IOException, ServletException {
        String page;
        int requestedUserId = defineIdFromUri(request.getRequestURI());
        Integer currentUserId = (Integer) session.getAttribute(SessionAttribute.USER_INFO_ID);
        if (currentUserId != null && requestedUserId == currentUserId) {
            page = JspPath.USER_INFO;
            String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
            boolean condition = userRole != null && userRole.equals(SessionAttribute.ADMIN_ROLE);
            forwardOrError404(condition, page, request, response, session);
        } else {
            page = UrlPattern.USER_INFO_REQUEST + requestedUserId;
            forward(request, response, page);
        }
    }

    private void doCasePatientInfo(HttpServletRequest request, HttpServletResponse response,
                                   HttpSession session) throws IOException, ServletException {
        String page;
        int requestedPatientId = defineIdFromUri(request.getRequestURI());
        Integer currentPatientId = (Integer) session.getAttribute(SessionAttribute.PATIENT_INFO_ID);
        if (currentPatientId != null && requestedPatientId == currentPatientId) {
            String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
            page = JspPath.PATIENT_INFO;
            boolean condition = userRole != null && userRole.equals(SessionAttribute.ADMIN_ROLE);
            forwardOrError404(condition, page, request, response, session);
        } else {
            page = UrlPattern.PATIENT_INFO_REQUEST + requestedPatientId;
            forward(request, response, page);
        }
    }

    private void doCaseEmployeeInfo(HttpServletRequest request, HttpServletResponse response,
                                    HttpSession session) throws IOException, ServletException {
        String page;
        int requestedEmployeeId = defineIdFromUri(request.getRequestURI());
        Integer currentEmployeeId = (Integer) session.getAttribute(SessionAttribute.EMPLOYEE_INFO_ID);
        if (currentEmployeeId != null && requestedEmployeeId == currentEmployeeId) {
            String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
            page = JspPath.EMPLOYEE_INFO;
            boolean condition = userRole != null && userRole.equals(SessionAttribute.ADMIN_ROLE);
            forwardOrError404(condition, page, request, response, session);
        } else {
            page = UrlPattern.EMPLOYEE_INFO_REQUEST + requestedEmployeeId;
            forward(request, response, page);
        }
    }

    private void doCaseChangingPassword(HttpServletRequest request, HttpServletResponse response,
                                        HttpSession session) throws IOException, ServletException {
        String page;
        int userId = defineIdFromUri(request.getRequestURI());
        Boolean userExists = (Boolean) session.getAttribute(SessionAttribute.USER_EXISTS);
        if (userExists != null && userExists) {
            String prevPage = (String) session.getAttribute(SessionAttribute.CURRENT_PAGE);
            if (!prevPage.matches(DynamicUrl.CHANGING_PASSWORD.getPattern())) {
                session.setAttribute(SessionAttribute.VALIDATED, null);
            }
            LOGGER.log(Level.DEBUG, "Flag \"USER_EXISTS\" has been removed from the session");
            session.setAttribute(SessionAttribute.USER_EXISTS, null);
            String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
            page = JspPath.ADMIN_CHANGING_PASSWORD;
            request.setAttribute(RequestParameter.USER_ID, userId);
            boolean condition = userRole != null && userRole.equals(SessionAttribute.ADMIN_ROLE);
            forwardOrError404(condition, page, request, response, session);
        } else {
            LOGGER.log(Level.DEBUG, "Requesting the command to check user id");
            page = UrlPattern.ADMIN_CHANGING_PASSWORD_REQUEST + userId;
            forward(request, response, page);
        }
    }

    private void doCaseUpdatingUser(HttpServletRequest request, HttpServletResponse response,
                                    HttpSession session) throws IOException, ServletException {
        String page;
        int userId = defineIdFromUri(request.getRequestURI());
        Boolean userExists = (Boolean) session.getAttribute(SessionAttribute.USER_EXISTS);
        if (userExists != null && userExists) {
            String prevPage = (String) session.getAttribute(SessionAttribute.CURRENT_PAGE);
            if (!prevPage.matches(DynamicUrl.UPDATING_USER.getPattern())) {
                session.setAttribute(SessionAttribute.VALIDATED, null);
            }
            LOGGER.log(Level.DEBUG, "Flag \"USER_EXISTS\" has been removed from the session");
            session.setAttribute(SessionAttribute.USER_EXISTS, null);
            String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
            page = JspPath.USER_UPDATING;
            boolean condition = userRole != null && userRole.equals(SessionAttribute.ADMIN_ROLE);
            forwardOrError404(condition, page, request, response, session);
        } else {
            LOGGER.log(Level.DEBUG, "Requesting the command to check user id");
            page = UrlPattern.UPDATING_USER_REQUEST + userId;
            forward(request, response, page);
        }
    }

    private void doCaseUpdatingEmployee(HttpServletRequest request, HttpServletResponse response,
                                        HttpSession session) throws IOException, ServletException {
        String page;
        int employeeId = defineIdFromUri(request.getRequestURI());
        Boolean employeeExists = (Boolean) session.getAttribute(SessionAttribute.EMPLOYEE_EXISTS);
        if (employeeExists != null && employeeExists) {
            String prevPage = (String) session.getAttribute(SessionAttribute.CURRENT_PAGE);
            if (!prevPage.matches(DynamicUrl.UPDATING_EMPLOYEE.getPattern())) {
                session.setAttribute(SessionAttribute.VALIDATED, null);
            }
            LOGGER.log(Level.DEBUG, "Flag \"EMPLOYEE_EXISTS\" has been removed from the session");
            session.setAttribute(SessionAttribute.EMPLOYEE_EXISTS, null);
            String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
            page = JspPath.EMPLOYEE_UPDATING;
            boolean condition = userRole != null && userRole.equals(SessionAttribute.ADMIN_ROLE);
            forwardOrError404(condition, page, request, response, session);
        } else {
            LOGGER.log(Level.DEBUG, "Requesting the command to check employee id");
            page = UrlPattern.UPDATING_EMPLOYEE_REQUEST + employeeId;
            forward(request, response, page);
        }
    }

    private void doCaseUpdatingPatient(HttpServletRequest request, HttpServletResponse response,
                                       HttpSession session) throws IOException, ServletException {
        String page;
        int patientId = defineIdFromUri(request.getRequestURI());
        Boolean patientExists = (Boolean) session.getAttribute(SessionAttribute.PATIENT_EXISTS);
        if (patientExists != null && patientExists) {
            String prevPage = (String) session.getAttribute(SessionAttribute.CURRENT_PAGE);
            if (!prevPage.matches(DynamicUrl.UPDATING_PATIENT.getPattern())) {
                session.setAttribute(SessionAttribute.VALIDATED, null);
            }
            LOGGER.log(Level.DEBUG, "Flag \"PATIENT_EXISTS\" has been removed from the session");
            session.setAttribute(SessionAttribute.PATIENT_EXISTS, null);
            String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
            page = JspPath.PATIENT_UPDATING;
            boolean condition = userRole != null
                    && (userRole.equals(SessionAttribute.ADMIN_ROLE) || userRole.equals(SessionAttribute.DOCTOR_ROLE));
            forwardOrError404(condition, page, request, response, session);
        } else {
            LOGGER.log(Level.DEBUG, "Requesting the command to check patient id");
            page = UrlPattern.UPDATING_PATIENT_REQUEST + patientId;
            forward(request, response, page);
        }
    }
}

