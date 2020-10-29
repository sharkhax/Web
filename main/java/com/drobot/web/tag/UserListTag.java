package com.drobot.web.tag;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.model.entity.User;
import com.drobot.web.tag.util.TagUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

public class UserListTag extends TagSupport {

    public static final int ROWS_NUMBER = 10;
    private static final Logger LOGGER = LogManager.getLogger(UserListTag.class);
    private static final String ID_KEY = "table.id";
    private static final String LOGIN_KEY = "table.login";
    private static final String EMAIL_KEY = "table.email";
    private static final String ROLE_KEY = "table.role";
    private static final String STATUS_KEY = "table.status";
    private static final String EMPLOYEE_ID_KEY = "table.employeeId";

    @Override
    public int doStartTag() throws JspException {
        try {
            HttpSession session = pageContext.getSession();
            String lang = (String) session.getAttribute(RequestParameter.CURRENT_LOCALE);
            ResourceBundle bundle = TagUtil.getMessageBundle(lang);
            String id = bundle.getString(ID_KEY);
            String login = bundle.getString(LOGIN_KEY);
            String email = bundle.getString(EMAIL_KEY);
            String role = bundle.getString(ROLE_KEY);
            String stringStatus = bundle.getString(STATUS_KEY);
            String employeeId = bundle.getString(EMPLOYEE_ID_KEY);
            JspWriter out = pageContext.getOut();
            out.write("<table class=\"table table-striped table-bordered table-hover\" style=\"text-align: center;");
            out.write(" background-color: #f4f4f4b8; font-family: 'Times New Roman', sans-serif\">");
            out.write("<thead class=\"thead-light\"><tr>");
            out.write("<th scope=\"col\">#</th>");
            out.write("<th scope=\"col\">" + id + "</th>");
            out.write("<th scope=\"col\">"+ login +"</th>");
            out.write("<th scope=\"col\">" + email +"</th>");
            out.write("<th scope=\"col\">" + role + "</th>");
            out.write("<th scope=\"col\">" + stringStatus + "</th>");
            out.write("<th scope=\"col\">" + employeeId + "</th>");
            out.write("</tr></thead><tbody>");
            List<User> userList = (List<User>) pageContext.getSession().getAttribute(RequestParameter.USER_LIST);
            if (userList != null) {
                int size = userList.size();
                for (int i = 0; i < ROWS_NUMBER; i++) {
                    out.write("<tr><th scope=\"row\">" + (i + 1) + "</th>");
                    if (size > i) {
                        User currentUser = userList.get(i);
                        out.write("<td>" + currentUser.getId() + "</td>");
                        out.write("<td>" + currentUser.getLogin() + "</td>");
                        out.write("<td>" + currentUser.getEmail() + "</td>");
                        out.write("<td>" + currentUser.getRole() + "</td>");
                        out.write("<td>" + currentUser.getStatus() + "</td>");
                        out.write("<td>" + currentUser.getEmployeeId() + "</td>");
                    } else {
                        out.write("<td></td><td></td><td></td><td></td><td></td><td></td>");
                    }
                    out.write("</tr>");
                }
                LOGGER.log(Level.DEBUG, "Table has been created");
            } else {
                LOGGER.log(Level.ERROR, "User list is null");
            }
            out.write("</tbody></table>");
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, e);
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}
