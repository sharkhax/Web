package com.drobot.web.controller.filter;

import com.drobot.web.controller.JspPath;
import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.UrlPattern;
import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandProvider;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * Web filter used to check access for requesting pages that available for any user.
 *
 * @author Vladislav Drobot
 */
public class ServletSecurityCommonFilter extends AbstractSecurityFilter {

    private static final Logger LOGGER = LogManager.getLogger(ServletSecurityCommonFilter.class);

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
            default -> filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private void doCaseMainController(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                      FilterChain filterChain) throws IOException, ServletException {
        String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
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
                        "User doesn't have an access to such command, forwarding to error page");
                shouldBeRedirected = true;
            }
        }
        if (shouldBeRedirected) {
            String page = JspPath.ERROR_404;
            forward(request, response, page);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private void doCaseMainPage(HttpServletRequest request, HttpServletResponse response,
                                HttpSession session) throws IOException, ServletException {
        String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
        if (userRole == null || userRole.equals(SessionAttribute.GUEST_ROLE)) {
            redirect(response, UrlPattern.LOGIN_PAGE);
        } else {
            String page = JspPath.MAIN;
            session.setAttribute(SessionAttribute.CURRENT_PAGE, page);
            forward(request, response, page);
        }
    }

    private void doCaseLoginPage(HttpServletRequest request, HttpServletResponse response,
                                 HttpSession session) throws IOException, ServletException {
        String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
        if (userRole == null || userRole.equals(SessionAttribute.GUEST_ROLE)) {
            String page = JspPath.LOGIN;
            session.setAttribute(SessionAttribute.CURRENT_PAGE, page);
            forward(request, response, page);
        } else {
            redirect(response, UrlPattern.MAIN_PAGE);
        }
    }
}
