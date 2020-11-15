package com.drobot.web.controller.filter;

import com.drobot.web.controller.JspPath;
import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.command.AccessType;
import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandAccessLevel;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter implementation, that also contains common methods for other filters
 *
 * @author Vladislav Drobot
 */
public abstract class AbstractSecurityFilter implements Filter {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    /**
     * Wrapper method of RequestDispatcher's forward(ServletRequest, ServletResponse) method with logger.
     *
     * @param request  HttpServletRequest object.
     * @param response HttpServletResponse object.
     * @param page     String object of page to be forwarded.
     * @throws IOException      if RequestDispatcher's forward(ServletRequest, ServletResponse) method
     *                          throws such exception.
     * @throws ServletException if RequestDispatcher's forward(ServletRequest, ServletResponse) method
     *                          throws such exception.
     */
    protected void forward(HttpServletRequest request, HttpServletResponse response,
                           String page) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
        LOGGER.log(Level.DEBUG, "User has been forwarded");
    }

    /**
     * Wrapper method of HttpServletResponse's sendRedirect(String) method with logger.
     *
     * @param response HttpServletResponse object.
     * @param page     String object of page to be redirected.
     * @throws IOException if HttpServletResponse's sendRedirect(String) method throws such exception.
     */
    protected void redirect(HttpServletResponse response, String page) throws IOException {
        response.sendRedirect(page);
        LOGGER.log(Level.DEBUG, "User has been redirected");
    }

    /**
     * Checks user's role for accessing the requested page.
     *
     * @param command  ActionCommand that was requested.
     * @param userRole String representation of user's role.
     * @return true if user's role has the access, false otherwise.
     */
    protected boolean hasAccess(ActionCommand command, String userRole) {
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

    /**
     * Wrapper method of this class's forward(ServletRequest, ServletResponse, String) method which
     * checks the condition, if it is false method forwards to the error page. Also writes logs anyway
     * and sets CURRENT_PAGE attribute.
     *
     * @param condition condition to forward to the specified page, or to the error page either.
     * @param page      String representation of the page to be forwarded if the condition is true.
     * @param request   HttpServletRequest object.
     * @param response  HttpServletResponse object.
     * @param session   HttpSession object for setting CURRENT_PAGE attribute.
     * @throws ServletException if this class's forward(ServletRequest, ServletResponse, String) method
     *                          throws such exception.
     * @throws IOException      if this class's forward(ServletRequest, ServletResponse, String) method
     *                          throws such exception.
     */
    protected void forwardOrError404(boolean condition, String page,
                                     HttpServletRequest request, HttpServletResponse response,
                                     HttpSession session) throws ServletException, IOException {
        if (condition) {
            LOGGER.log(Level.DEBUG, "User has an access to the page, forwarding");
            session.setAttribute(SessionAttribute.CURRENT_PAGE, request.getRequestURI());
        } else {
            LOGGER.log(Level.DEBUG, "User doesn't have an access to the page, redirecting to error 404 page");
            page = JspPath.ERROR_404;
        }
        forward(request, response, page);
    }
}
