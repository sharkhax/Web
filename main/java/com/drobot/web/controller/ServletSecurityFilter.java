package com.drobot.web.controller;

import com.drobot.web.controller.command.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebFilter(urlPatterns = {UrlPattern.MAIN_CONTROLLER,
        UrlPattern.MAIN_PAGE,
        UrlPattern.LOGIN_PAGE,
        UrlPattern.USER_REGISTRATION,
        UrlPattern.EMPLOYEE_REGISTRATION,
        UrlPattern.EMPLOYEE_LIST,
        UrlPattern.RECORD_LIST,
        UrlPattern.USER_LIST},
        servletNames = {"MainController"})
public class ServletSecurityFilter extends AbstractSecurityFilter {

    private static final Logger LOGGER = LogManager.getLogger(ServletSecurityFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        switch (uri) {
            case UrlPattern.MAIN_CONTROLLER -> doCaseMainController(request, response, session, filterChain);
            case UrlPattern.MAIN_PAGE -> doCaseMainPage(request, response, session);
            case UrlPattern.LOGIN_PAGE -> doCaseLoginPage(request, response, session);
            case UrlPattern.USER_REGISTRATION -> doCaseUserRegistration(request, response, session);
            case UrlPattern.EMPLOYEE_REGISTRATION -> doCaseEmployeeRegistration(request, response, session);
            case UrlPattern.EMPLOYEE_LIST -> doCaseEmployeeList(request, response, session);
            case UrlPattern.USER_LIST -> doCaseUserList(request, response, session);
            case UrlPattern.RECORD_LIST -> doCaseRecordList(request, response, session);
        }
    }

    private boolean hasAccess(ActionCommand command, String userRole) {
        boolean result = false;
        CommandAccessLevel annotation = command.getClass().getAnnotation(CommandAccessLevel.class);
        AccessType[] accessType = annotation.value();
        for (AccessType type : accessType) {
            String accessRole = type.getUserRole();
            if (accessRole.equals(userRole)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private void doCaseMainController(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                      FilterChain filterChain) throws IOException, ServletException {
        String userRole = (String) session.getAttribute(RequestParameter.USER_ROLE);
        boolean shouldBeRedirected = false;
        Optional<ActionCommand> optionalCommand = CommandProvider.defineCommand(request);
        if (optionalCommand.isEmpty()) {
            shouldBeRedirected = true;
        } else {
            ActionCommand command = optionalCommand.get();
            if (hasAccess(command, userRole)) {
                LOGGER.log(Level.DEBUG, "User has an access to such command, redirecting");
            } else {
                LOGGER.log(Level.DEBUG,
                        "User doesn't have an access to such command, forwarding to its main");
                shouldBeRedirected = true;
            }
        }
        if (shouldBeRedirected) {
            String page = defineMainPage(userRole);
            session.setAttribute(RequestParameter.CURRENT_PAGE, page);
            redirect(response, page);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private void doCaseMainPage(HttpServletRequest request, HttpServletResponse response,
                                HttpSession session) throws IOException, ServletException {
        String userRole = (String) session.getAttribute(RequestParameter.USER_ROLE);
        String page = defineMainPage(userRole);
        session.setAttribute(RequestParameter.CURRENT_PAGE, page);
        if (!page.equals(JspPath.LOGIN)) {
            forward(request, response, page);
        } else {
            redirect(response, UrlPattern.LOGIN_PAGE);
        }
    }

    private void doCaseLoginPage(HttpServletRequest request, HttpServletResponse response,
                                 HttpSession session) throws IOException, ServletException {
        String userRole = (String) session.getAttribute(RequestParameter.USER_ROLE);
        String page = defineMainPage(userRole);
        session.setAttribute(RequestParameter.CURRENT_PAGE, page);
        if (page.equals(JspPath.LOGIN)) {
            forward(request, response, page);
        } else {
            redirect(response, UrlPattern.MAIN_PAGE);
        }
    }

    private void doCaseUserRegistration(HttpServletRequest request, HttpServletResponse response,
                                        HttpSession session) throws IOException, ServletException {
        String userRole = (String) session.getAttribute(RequestParameter.USER_ROLE);
        String page;
        if (userRole.equals(RequestParameter.ADMIN_ROLE)) {
            LOGGER.log(Level.DEBUG, "User has an access to the page, forwarding");
            page = JspPath.USER_REGISTRATION;
            session.setAttribute(RequestParameter.CURRENT_PAGE, UrlPattern.USER_REGISTRATION);
            forward(request, response, page);
        } else {
            LOGGER.log(Level.DEBUG, "User doesn't have an access to the page, redirecting to its main");
            page = UrlPattern.MAIN_PAGE;
            session.setAttribute(RequestParameter.CURRENT_PAGE, page);
            redirect(response, page);
        }
    }

    private void doCaseEmployeeRegistration(HttpServletRequest request, HttpServletResponse response,
                                            HttpSession session) throws IOException, ServletException {
        String userRole = (String) session.getAttribute(RequestParameter.USER_ROLE);
        String page;
        if (userRole.equals(RequestParameter.ADMIN_ROLE)) { // todo общий метод
            LOGGER.log(Level.DEBUG, "User has an access to the page, forwarding");
            page = JspPath.EMPLOYEE_REGISTRATION;
            session.setAttribute(RequestParameter.CURRENT_PAGE, UrlPattern.EMPLOYEE_REGISTRATION);
            forward(request, response, page);
        } else {
            LOGGER.log(Level.DEBUG, "User doesn't have an access to the page, redirecting to its main");
            page = UrlPattern.MAIN_PAGE;
            session.setAttribute(RequestParameter.CURRENT_PAGE, page);
            redirect(response, page);
        }
    }

    private void doCaseEmployeeList(HttpServletRequest request, HttpServletResponse response,
                                    HttpSession session) throws IOException, ServletException {

    }

    private void doCaseUserList(HttpServletRequest request, HttpServletResponse response,
                                HttpSession session) throws IOException, ServletException {

    }

    private void doCaseRecordList(HttpServletRequest request, HttpServletResponse response,
                                  HttpSession session) throws IOException, ServletException {

    }
}
