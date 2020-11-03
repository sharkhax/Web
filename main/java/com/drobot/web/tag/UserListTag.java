package com.drobot.web.tag;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.command.CommandType;
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
        HttpSession session = pageContext.getSession();
        JspWriter out = pageContext.getOut();
        createList(out, session);
        int currentPage = (int) session.getAttribute(RequestParameter.USER_LIST_CURRENT_PAGE);
        int usersNumber = (int) session.getAttribute(RequestParameter.USERS_NUMBER);
        int pagesNumber = usersNumber % ROWS_NUMBER == 0
                ? usersNumber / ROWS_NUMBER : usersNumber / ROWS_NUMBER + 1;
        String command = CommandType.USER_LIST_COMMAND.toString().toLowerCase();
        TagUtil.createPagination(pageContext, currentPage, pagesNumber, command);
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }

    private void createList(JspWriter out, HttpSession session) throws JspException {
        try {
            out.write("<table class=\"table table-striped table-bordered table-hover\" style=\"text-align: center;");
            out.write(" background-color: #f4f4f4b8; font-family: 'Times New Roman', sans-serif\">");
            createHead(out, session);
            out.write("<tbody>");
            List<User> userList = (List<User>) pageContext.getSession().getAttribute(RequestParameter.USER_LIST);
            createRows(out, session, userList);
            out.write("</tbody></table>");
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Error while creating user list ", e);
            throw new JspException(e);
        }
    }

    private void createHead(JspWriter out, HttpSession session) throws JspException {
        try {
            String lang = (String) session.getAttribute(RequestParameter.CURRENT_LOCALE);
            ResourceBundle bundle = TagUtil.getMessageBundle(lang);
            String id = bundle.getString(ID_KEY);
            String login = bundle.getString(LOGIN_KEY);
            String email = bundle.getString(EMAIL_KEY);
            String role = bundle.getString(ROLE_KEY);
            String stringStatus = bundle.getString(STATUS_KEY);
            String employeeId = bundle.getString(EMPLOYEE_ID_KEY);
            out.write("<form action=\"/mainController\" method=\"post\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"user_list_command\"/>");
            out.write("<thead class=\"thead-light\"><tr>");
            out.write("<th scope=\"col\">#</th>");
            out.write("<th scope=\"col\">" + id + "<button type=\"submit\" name=\"userListSortBy\" value=\"login\">text</button></th>"); // FIXME: 03.11.2020 
            out.write("<th scope=\"col\">" + login + "</th>");
            out.write("<th scope=\"col\">" + email + "</th>");
            out.write("<th scope=\"col\">" + role + "</th>");
            out.write("<th scope=\"col\">" + stringStatus + "</th>");
            out.write("<th scope=\"col\">" + employeeId + "</th>");
            out.write("</tr></thead></form>");
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Error while creating user list head", e);
            throw new JspException(e);
        }
    }

    private void createRows(JspWriter out, HttpSession session, List<User> userList) throws JspException {
        try {
            if (userList != null) {
                int size = userList.size();
                int currentPage = (int) session.getAttribute(RequestParameter.USER_LIST_CURRENT_PAGE);
                for (int i = 0; i < ROWS_NUMBER; i++) {
                    int rowNumber = ROWS_NUMBER * (currentPage - 1) + i + 1;
                    out.write("<tr><th scope=\"row\">" + rowNumber + "</th>");
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
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Error while creating user list rows", e);
            throw new JspException(e);
        }
    }
}
