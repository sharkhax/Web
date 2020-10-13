package com.drobot.web.controller;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/jsp/*"})
public class JspSecurityFilter extends AbstractSecurityFilter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String page;
        if (session != null) {
            page = UrlPattern.MAIN_PAGE;
        } else {
            page = UrlPattern.LOGIN_PAGE;
        }
        redirect(response, page);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
