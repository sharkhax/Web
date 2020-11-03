package com.drobot.web.tag;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.command.CommandType;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.model.util.DateConverter;
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

public class EmployeeListTag extends TagSupport {

    public static final int ROWS_NUMBER = 10;
    private static final Logger LOGGER = LogManager.getLogger(EmployeeListTag.class);
    private static final String ID_KEY = "table.id";
    private static final String NAME_KEY = "table.name";
    private static final String SURNAME_KEY = "table.surname";
    private static final String AGE_KEY = "table.age";
    private static final String GENDER_KEY = "table.gender";
    private static final String POSITION_KEY = "table.position";
    private static final String HIRE_DATE_KEY = "table.hireDate";
    private static final String DISMISS_DATE_KEY = "table.dismissDate";
    private static final String STATUS_KEY = "table.status";
    private static final String USER_ID_KEY = "table.userId";

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession session = pageContext.getSession();
        createList(out, session);
        int currentPage = (int) session.getAttribute(RequestParameter.EMPLOYEE_LIST_CURRENT_PAGE);
        int employeesNumber = (int) session.getAttribute(RequestParameter.EMPLOYEES_NUMBER);
        int pagesNumber = employeesNumber % ROWS_NUMBER == 0
                ? employeesNumber / ROWS_NUMBER : employeesNumber / ROWS_NUMBER + 1;
        String command = CommandType.EMPLOYEE_LIST_COMMAND.toString().toLowerCase();
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
            List<Employee> employeeList = (List<Employee>) session.getAttribute(RequestParameter.EMPLOYEE_LIST);
            createRows(out, session, employeeList);
            out.write("</tbody></table>");
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Error while creating employee list", e);
            throw new JspException(e);
        }
    }

    private void createHead(JspWriter out, HttpSession session) throws JspException {
        try {
            String lang = (String) session.getAttribute(RequestParameter.CURRENT_LOCALE);
            ResourceBundle bundle = TagUtil.getMessageBundle(lang);
            String id = bundle.getString(ID_KEY);
            String name = bundle.getString(NAME_KEY);
            String surname = bundle.getString(SURNAME_KEY);
            String age = bundle.getString(AGE_KEY);
            String gender = bundle.getString(GENDER_KEY);
            String position = bundle.getString(POSITION_KEY);
            String hireDate = bundle.getString(HIRE_DATE_KEY);
            String dismissDate = bundle.getString(DISMISS_DATE_KEY);
            String status = bundle.getString(STATUS_KEY);
            String userId = bundle.getString(USER_ID_KEY);
            out.write("<thead class=\"thead-light\"><tr>");
            out.write("<th scope=\"col\">#</th>");
            out.write("<th scope=\"col\">" + id + "</th>");
            out.write("<th scope=\"col\">" + name + "</th>");
            out.write("<th scope=\"col\">" + surname + "</th>");
            out.write("<th scope=\"col\">" + age + "</th>");
            out.write("<th scope=\"col\">" + gender + "</th>");
            out.write("<th scope=\"col\">" + position + "</th>");
            out.write("<th scope=\"col\">" + hireDate + "</th>");
            out.write("<th scope=\"col\">" + dismissDate + "</th>");
            out.write("<th scope=\"col\">" + status + "</th>");
            out.write("<th scope=\"col\">" + userId + "</th>");
            out.write("</tr></thead>");
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Error while creating employee list head", e);
            throw new JspException(e);
        }
    }

    private void createRows(JspWriter out, HttpSession session, List<Employee> employeeList) throws JspException {
        try {
            if (employeeList != null) {
                int size = employeeList.size();
                for (int i = 0; i < ROWS_NUMBER; i++) {
                    out.write("<tr><th scope=\"row\">" + (i + 1) + "</th>");
                    if (size > i) {
                        Employee currentEmployee = employeeList.get(i);
                        out.write("<td>" + currentEmployee.getId() + "</td>");
                        out.write("<td>" + currentEmployee.getName() + "</td>");
                        out.write("<td>" + currentEmployee.getSurname() + "</td>");
                        out.write("<td>" + currentEmployee.getAge() + "</td>");
                        out.write("<td>" + currentEmployee.getGender() + "</td>");
                        out.write("<td>" + currentEmployee.getPosition() + "</td>");
                        long hireDateMillis = currentEmployee.getHireDateMillis();
                        String stringHireDate = DateConverter.millisToLocalDate(hireDateMillis).toString();
                        long dismissDateMillis = currentEmployee.getDismissDateMillis();
                        String stringDismissDate;
                        if (dismissDateMillis == 0L) {
                            stringDismissDate = "-";
                        } else {
                            stringDismissDate = DateConverter.millisToLocalDate(dismissDateMillis).toString();
                        }
                        out.write("<td>" + stringHireDate + "</td>");
                        out.write("<td>" + stringDismissDate + "</td>");
                        out.write("<td>" + currentEmployee.getStatus() + "</td>");
                        out.write("<td>" + currentEmployee.getUserId() + "</td>");
                    } else {
                        out.write("<td></td><td></td><td></td><td></td><td></td><td></td>");
                        out.write("<td></td><td></td><td></td><td></td>");
                    }
                    out.write("</tr>");
                }
                LOGGER.log(Level.DEBUG, "Table has been created");
            } else {
                LOGGER.log(Level.ERROR, "Employee list is null");
            }
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "", e);
            throw new JspException(e);
        }
    }
}
