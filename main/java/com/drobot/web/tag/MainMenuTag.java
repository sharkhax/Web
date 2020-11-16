package com.drobot.web.tag;

import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.tag.util.TagUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Custom tag that creates main menu buttons.
 *
 * @author Vladislav Drobot
 */
public class MainMenuTag extends TagSupport {

    private static final Logger LOGGER = LogManager.getLogger(MainMenuTag.class);
    private static final String BUTTON_STYLE = "width: 90%; color: #495057; text-align: center;" +
            "background-color: #ffffffb5; font-weight: bold; font-family: 'Times New Roman', sans-serif";
    private static final String USER_LIST_BUTTON_KEY = "main.userListButton";
    private static final String EMPLOYEE_LIST_BUTTON_KEY = "main.employeeListButton";
    private static final String REGISTER_USER_BUTTON_KEY = "main.registerUserButton";
    private static final String RECORD_LIST_BUTTON_KEY = "main.recordListButton";
    private static final String SETTINGS_BUTTON_KEY = "main.personalSettingsButton";
    private static final String CREATE_RECORD_BUTTON_KEY = "main.createRecordButton";
    private static final String PATIENT_LIST_BUTTON = "main.patientListButton";
    private static final String NEW_PATIENT_BUTTON = "main.newPatientButton";

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession session = pageContext.getSession();
        String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
        String lang = (String) session.getAttribute(SessionAttribute.CURRENT_LOCALE);
        ResourceBundle bundle = TagUtil.getMessageBundle(lang);
        try {
            out.write("<form action=\"mainController\" method=\"post\">");
            switch (userRole) {
                case SessionAttribute.ADMIN_ROLE -> createAdminMenu(out, bundle);
                case SessionAttribute.DOCTOR_ROLE -> createDoctorMenu(out, bundle);
                case SessionAttribute.ASSISTANT_ROLE -> createAssistantMenu(out, bundle);
            }
            out.write("</form>");
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

    private void createAdminMenu(JspWriter out, ResourceBundle bundle) throws IOException {
        createUserListButton(out, bundle);
        createEmployeeListButton(out, bundle);
        createPatientListButton(out, bundle);
        createNewPatientButton(out, bundle);
        createRegisterUserButton(out, bundle);
        createSettingsButton(out, bundle);
    }

    private void createDoctorMenu(JspWriter out, ResourceBundle bundle) throws IOException {
        createPatientListButton(out, bundle);
        createNewPatientButton(out, bundle);
        createSettingsButton(out, bundle);
    }

    private void createAssistantMenu(JspWriter out, ResourceBundle bundle) throws IOException {
        createPatientListButton(out, bundle);
        createSettingsButton(out, bundle);
    }

    private void createSettingsButton(JspWriter out, ResourceBundle bundle) throws IOException {
        String settingsButton = bundle.getString(SETTINGS_BUTTON_KEY);
        out.write("<button class=\"list-group-item list-group-item-action\" type=\"submit\" name=\"command\"");
        out.write("value=\"settings_page\" style=\"" + BUTTON_STYLE + "\">" + settingsButton + "</button>");
    }

    private void createNewRecordButton(JspWriter out, ResourceBundle bundle) throws IOException {
        String createRecordButton = bundle.getString(CREATE_RECORD_BUTTON_KEY);
        out.write("<button class=\"list-group-item list-group-item-action\" type=\"submit\" name=\"command\" ");
        out.write("value=\"create_record\" style=\"" + BUTTON_STYLE + "\">" + createRecordButton + "</button>");
    }

    private void createUserListButton(JspWriter out, ResourceBundle bundle) throws IOException {
        String userListButton = bundle.getString(USER_LIST_BUTTON_KEY);
        out.write("<button class=\"list-group-item list-group-item-action\" type=\"submit\" name=\"command\" ");
        out.write("value=\"user_list_command\" style=\"" + BUTTON_STYLE + "\">" + userListButton + "</button>");
    }

    private void createEmployeeListButton(JspWriter out, ResourceBundle bundle) throws IOException {
        String employeeListButton = bundle.getString(EMPLOYEE_LIST_BUTTON_KEY);
        out.write("<button class=\"list-group-item list-group-item-action\" type=\"submit\" name=\"command\"");
        out.write("value=\"employee_list_command\" style=\"" + BUTTON_STYLE + "\">"
                + employeeListButton + "</button>");
    }

    private void createRegisterUserButton(JspWriter out, ResourceBundle bundle) throws IOException {
        String registerUserButton = bundle.getString(REGISTER_USER_BUTTON_KEY);
        out.write("<button class=\"list-group-item list-group-item-action\" type=\"submit\" name=\"command\"");
        out.write("value=\"redirect_to_user_registration\" style=\"" + BUTTON_STYLE + "\">"
                + registerUserButton + "</button>");
    }

    private void createRecordListButton(JspWriter out, ResourceBundle bundle) throws IOException {
        String recordListButton = bundle.getString(RECORD_LIST_BUTTON_KEY);
        out.write("<button class=\"list-group-item list-group-item-action\" type=\"submit\" name=\"command\"");
        out.write("value=\"record_list_command\" style=\"" + BUTTON_STYLE + "\">"
                + recordListButton + "</button>");
    }

    private void createPatientListButton(JspWriter out, ResourceBundle bundle) throws IOException {
        String patientListButton = bundle.getString(PATIENT_LIST_BUTTON);
        out.write("<button class=\"list-group-item list-group-item-action\" type=\"submit\" name=\"command\"");
        out.write("value=\"patient_list_command\" style=\"" + BUTTON_STYLE + "\">"
                + patientListButton + "</button>");
    }

    private void createNewPatientButton(JspWriter out, ResourceBundle bundle) throws IOException {
        String newPatientButton = bundle.getString(NEW_PATIENT_BUTTON);
        out.write("<button class=\"list-group-item list-group-item-action\" type=\"submit\" name=\"command\"");
        out.write("value=\"redirect_to_patient_creating\" style=\"" + BUTTON_STYLE + "\">"
                + newPatientButton + "</button>");
    }
}
