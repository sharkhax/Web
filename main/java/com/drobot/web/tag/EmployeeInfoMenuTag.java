package com.drobot.web.tag;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.command.CommandType;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.tag.util.TagUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Custom tag that creates buttons for employee info page.
 */
public class EmployeeInfoMenuTag extends TagSupport {

    private static final Logger LOGGER = LogManager.getLogger(UserInfoMenuTag.class);
    private static final String BUTTON_STYLE = "width: 90%; color: #495057; text-align: center; font-weight: bold; "
            + "background-color: #ffffffb5; font-family: 'Times New Roman', sans-serif";
    private static final String UPDATE_EMPLOYEE_BUTTON_KEY = "employeeInfo.updateEmployeeButton";
    private static final String FIRE_EMPLOYEE_BUTTON_KEY = "employeeInfo.fireEmployeeButton";
    private static final String RESTORE_EMPLOYEE_BUTTON_KEY = "employeeInfo.restoreEmployeeButton";
    private static final String SEND_TO_VACATION_BUTTON_KEY = "employeeInfo.sendToVacationButton";
    private static final String RETURN_FROM_VACATION_BUTTON_KEY = "employeeInfo.returnFromVacationButton";

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession session = pageContext.getSession();
        String locale = (String) session.getAttribute(SessionAttribute.CURRENT_LOCALE);
        ResourceBundle bundle = TagUtil.getMessageBundle(locale);
        createUpdateEmployeeButton(out, bundle);
        createFireOrRestoreButton(out, session, bundle);
        createVacationButton(out, session, bundle);
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }

    private void createUpdateEmployeeButton(JspWriter out, ResourceBundle bundle) throws JspException {
        try {
            out.write("<button class=\"list-group-item list-group-item-action\" type=\"submit\" name=\"command\" ");
            out.write("value=\"" + CommandType.REDIRECT_TO_UPDATE_EMPLOYEE_PAGE + "\" ");
            out.write("style=\"" + BUTTON_STYLE + "\">");
            out.write(bundle.getString(UPDATE_EMPLOYEE_BUTTON_KEY));
            out.write("</button>");
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Error while creating update employee button", e);
            throw new JspException("Error while creating update employee button", e);
        }
    }

    private void createFireOrRestoreButton(JspWriter out, HttpSession session, ResourceBundle bundle)
            throws JspException {
        Map<String, String> employeeFields =
                (Map<String, String>) session.getAttribute(SessionAttribute.EMPLOYEE_DATA_FIELDS);
        if (employeeFields != null) {
            Entity.Status employeeStatus = Entity.Status.valueOf(employeeFields.get(RequestParameter.EMPLOYEE_STATUS));
            switch (employeeStatus) {
                case ACTIVE, VACATION -> createFireEmployeeButton(out, bundle);
                case ARCHIVE -> createRestoreEmployeeButton(out, bundle);
            }
        } else {
            LOGGER.log(Level.ERROR, "Employee data fields are null");
        }
    }

    private void createVacationButton(JspWriter out, HttpSession session, ResourceBundle bundle)
            throws JspException {
        Map<String, String> employeeFields =
                (Map<String, String>) session.getAttribute(SessionAttribute.EMPLOYEE_DATA_FIELDS);
        if (employeeFields != null) {
            Entity.Status employeeStatus = Entity.Status.valueOf(employeeFields.get(RequestParameter.EMPLOYEE_STATUS));
            switch (employeeStatus) {
                case ACTIVE -> createSendToVacationButton(out, bundle);
                case VACATION -> createReturnFromVacationButton(out, bundle);
            }
        } else {
            LOGGER.log(Level.ERROR, "Employee data fields are null");
        }
    }

    private void createFireEmployeeButton(JspWriter out, ResourceBundle bundle) throws JspException {
        try {
            out.write("<button class=\"list-group-item list-group-item-action\" type=\"submit\" ");
            out.write("name=\"command\" value=\"" + CommandType.FIRE_EMPLOYEE + "\" ");
            out.write("style=\"" + BUTTON_STYLE + "\">");
            out.write(bundle.getString(FIRE_EMPLOYEE_BUTTON_KEY));
            out.write("</button>");
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Error while creating fire employee button", e);
            throw new JspException("Error while creating fire employee button", e);
        }
    }

    private void createRestoreEmployeeButton(JspWriter out, ResourceBundle bundle) throws JspException {
        try {
            out.write("<button class=\"list-group-item list-group-item-action\" type=\"submit\" ");
            out.write("name=\"command\" value=\"" + CommandType.RESTORE_EMPLOYEE + "\" ");
            out.write("style=\"" + BUTTON_STYLE + "\">");
            out.write(bundle.getString(RESTORE_EMPLOYEE_BUTTON_KEY));
            out.write("</button>");
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Error while creating restore employee button", e);
            throw new JspException("Error while creating restore employee button", e);
        }
    }

    private void createSendToVacationButton(JspWriter out, ResourceBundle bundle) throws JspException {
        try {
            out.write("<button class=\"list-group-item list-group-item-action\" type=\"submit\" ");
            out.write("name=\"command\" value=\"" + CommandType.SEND_TO_VACATION + "\" ");
            out.write("style=\"" + BUTTON_STYLE + "\">");
            out.write(bundle.getString(SEND_TO_VACATION_BUTTON_KEY));
            out.write("</button>");
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Error while creating send to vacation button", e);
            throw new JspException("Error while creating send to vacation button", e);
        }
    }

    private void createReturnFromVacationButton(JspWriter out, ResourceBundle bundle) throws JspException {
        try {
            out.write("<button class=\"list-group-item list-group-item-action\" type=\"submit\" ");
            out.write("name=\"command\" value=\"" + CommandType.RETURN_FROM_VACATION + "\" ");
            out.write("style=\"" + BUTTON_STYLE + "\">");
            out.write(bundle.getString(RETURN_FROM_VACATION_BUTTON_KEY));
            out.write("</button>");
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Error while creating return from vacation button", e);
            throw new JspException("Error while creating return from vacation button", e);
        }
    }
}
