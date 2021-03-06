package com.drobot.web.controller.filter;

import com.drobot.web.controller.JspPath;
import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.UrlPattern;
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

/**
 * Web filter used to check the access for requesting pages with dynamic URLs.
 *
 * @author Vladislav Drobot
 */
public class DynamicUrlFilter extends AbstractSecurityFilter {

    private static final Logger LOGGER = LogManager.getLogger(DynamicUrlFilter.class);
    private static final String NUMBER_REGEX = "[0-9]+";

    /**
     * Dynamic URL patterns enumeration.
     *
     * @author Vladislav Drobot
     */
    enum DynamicUrl {

        /**
         * Represents dynamic URL pattern for user's info page.
         */
        USER_INFO("/mainPage/users/[0-9]+"),

        /**
         * Represents dynamic URL pattern for employee's info page.
         */
        EMPLOYEE_INFO("/mainPage/employees/[0-9]+"),

        /**
         * Represents dynamic URL pattern for patient's info page.
         */
        PATIENT_INFO("/mainPage/patients/[0-9]+"),

        /**
         * Represents dynamic URL pattern for record's info page.
         */
        RECORD_INFO("/mainPage/records/[0-9]+"),

        /**
         * Represents dynamic URL pattern for changing password page.
         */
        CHANGING_PASSWORD("/mainPage/users/[0-9]+/changePassword"),

        /**
         * Represents dynamic URL pattern for updating user page.
         */
        UPDATING_USER("/mainPage/users/[0-9]+/update"),

        /**
         * Represents dynamic URL pattern for updating employee page.
         */
        UPDATING_EMPLOYEE("/mainPage/employees/[0-9]+/update"),

        /**
         * Represents dynamic URL pattern for updating patient page.
         */
        UPDATING_PATIENT("/mainPage/patients/[0-9]+/update"),

        /**
         * Represents dynamic URL pattern for creating record page.
         */
        CREATING_RECORD("/mainPage/patients/[0-9]+/createRecord"),

        /**
         * Represents dynamic URL pattern for record list page.
         */
        RECORD_LIST("/mainPage/patients/[0-9]+/records");

        private final String pattern;

        DynamicUrl(String pattern) {
            this.pattern = pattern;
        }

        /**
         * Getter method for dynamic URL patterns.
         *
         * @return String objects of dynamic URL patterns.
         */
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
                case RECORD_INFO -> doCaseRecordInfo(request, response, session);
                case CHANGING_PASSWORD -> doCaseChangingPassword(request, response, session);
                case UPDATING_USER -> doCaseUpdatingUser(request, response, session);
                case UPDATING_EMPLOYEE -> doCaseUpdatingEmployee(request, response, session);
                case UPDATING_PATIENT -> doCaseUpdatingPatient(request, response, session);
                case CREATING_RECORD -> doCaseCreatingRecord(request, response, session);
                case RECORD_LIST -> doCaseRecordList(request, response, session);
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
            LOGGER.log(Level.DEBUG, "Requesting the command to check user id");
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
            boolean condition = userRole != null && !userRole.equals(SessionAttribute.GUEST_ROLE);
            forwardOrError404(condition, page, request, response, session);
        } else {
            LOGGER.log(Level.DEBUG, "Requesting the command to check patient id");
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
            LOGGER.log(Level.DEBUG, "Requesting the command to check employee id");
            page = UrlPattern.EMPLOYEE_INFO_REQUEST + requestedEmployeeId;
            forward(request, response, page);
        }
    }

    private void doCaseRecordInfo(HttpServletRequest request, HttpServletResponse response,
                                  HttpSession session) throws IOException, ServletException {
        String page;
        int requestedRecordId = defineIdFromUri(request.getRequestURI());
        Integer currentRecordId = (Integer) session.getAttribute(SessionAttribute.RECORD_INFO_ID);
        if (currentRecordId != null && requestedRecordId == currentRecordId) {
            String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
            page = JspPath.RECORD_INFO;
            boolean condition = userRole != null && !userRole.equals(SessionAttribute.GUEST_ROLE);
            forwardOrError404(condition, page, request, response, session);
        } else {
            LOGGER.log(Level.DEBUG, "Requesting the command to check record id");
            page = UrlPattern.RECORD_INFO_REQUEST + requestedRecordId;
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
            session.setAttribute(SessionAttribute.USER_EXISTS, null);
            LOGGER.log(Level.DEBUG, "Flag \"USER_EXISTS\" has been removed from the session");
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
            session.setAttribute(SessionAttribute.USER_EXISTS, null);
            LOGGER.log(Level.DEBUG, "Flag \"USER_EXISTS\" has been removed from the session");
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
            session.setAttribute(SessionAttribute.EMPLOYEE_EXISTS, null);
            LOGGER.log(Level.DEBUG, "Flag \"EMPLOYEE_EXISTS\" has been removed from the session");
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
            session.setAttribute(SessionAttribute.PATIENT_EXISTS, null);
            LOGGER.log(Level.DEBUG, "Flag \"PATIENT_EXISTS\" has been removed from the session");
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

    private void doCaseCreatingRecord(HttpServletRequest request, HttpServletResponse response,
                                      HttpSession session) throws IOException, ServletException {
        String page;
        int patientId = defineIdFromUri(request.getRequestURI());
        Boolean creationAllowed = (Boolean) session.getAttribute(SessionAttribute.RECORD_CREATION_ALLOWED);
        if (creationAllowed != null && creationAllowed) {
            String prevPage = (String) session.getAttribute(SessionAttribute.CURRENT_PAGE);
            if (!prevPage.matches(DynamicUrl.CREATING_RECORD.getPattern())) {
                session.setAttribute(SessionAttribute.VALIDATED, null);
            }
            session.setAttribute(SessionAttribute.RECORD_CREATION_ALLOWED, null);
            LOGGER.log(Level.DEBUG, "Flag \"RECORD_CREATION_ALLOWED\" has been removed from the session");
            String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
            page = JspPath.RECORD_CREATING;
            boolean condition = userRole != null
                    && (userRole.equals(SessionAttribute.ADMIN_ROLE) || userRole.equals(SessionAttribute.DOCTOR_ROLE));
            forwardOrError404(condition, page, request, response, session);
        } else {
            LOGGER.log(Level.DEBUG, "Requesting the command to check patient and user id");
            page = UrlPattern.RECORD_CREATING_REQUEST + patientId;
            forward(request, response, page);
        }
    }

    private void doCaseRecordList(HttpServletRequest request, HttpServletResponse response,
                                  HttpSession session) throws IOException, ServletException {
        String page;
        int patientId = defineIdFromUri(request.getRequestURI());
        Boolean patientExists = (Boolean) session.getAttribute(SessionAttribute.PATIENT_EXISTS);
        if (patientExists != null && patientExists) {
            session.setAttribute(SessionAttribute.PATIENT_EXISTS, null);
            LOGGER.log(Level.DEBUG, "Flag \"PATIENT_EXISTS\" has been removed from the session");
            String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
            page = JspPath.RECORD_LIST;
            boolean condition = userRole != null && !userRole.equals(SessionAttribute.GUEST_ROLE);
            request.setAttribute(RequestParameter.PATIENT_ID, patientId);
            forwardOrError404(condition, page, request, response, session);
        } else {
            LOGGER.log(Level.DEBUG, "Requesting the command to check patient id");
            page = UrlPattern.RECORD_LIST_REQUEST + patientId;
            forward(request, response, page);
        }
    }
}

