package com.drobot.web.controller;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    protected String defineMainPage(String userRole) {
        String page;
        if (userRole != null) {
            switch (userRole) {
                case RequestParameter.ADMIN_ROLE -> page = JspPath.ADMIN_MAIN;
                case RequestParameter.DOCTOR_ROLE -> page = JspPath.DOCTOR_MAIN;
                case RequestParameter.ASSISTANT_ROLE -> page = JspPath.ASSISTANT_MAIN;
                default -> page = JspPath.LOGIN;
            }
        } else {
            page = JspPath.LOGIN;
        }
        LOGGER.log(Level.DEBUG, "Main page has been defined");
        return page;
    }

    protected void forward(HttpServletRequest request, HttpServletResponse response,
                         String page) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(request.getContextPath() + page);
        dispatcher.forward(request, response);
        LOGGER.log(Level.DEBUG, "User has been forwarded");
    }

    protected void redirect(HttpServletResponse response, String page) throws IOException {
        response.sendRedirect(page);
        LOGGER.log(Level.DEBUG, "User has been redirected");
    }
}
