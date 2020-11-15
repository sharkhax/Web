package com.drobot.web.tag;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.command.CommandType;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.entity.Treatment;
import com.drobot.web.model.service.RecordService;
import com.drobot.web.model.service.impl.RecordServiceImpl;
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
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Custom tag that creates patient info menu at appropriate page.
 */
public class PatientInfoMenuTag extends TagSupport {

    private static final Logger LOGGER = LogManager.getLogger(UserInfoMenuTag.class);
    private static final String BUTTON_STYLE = "width: 90%; color: #495057; text-align: center; font-weight: bold; "
            + "background-color: #ffffffb5; font-family: 'Times New Roman', sans-serif";
    private static final String UPDATE_PATIENT_BUTTON_KEY = "patientInfo.updatePatientButton";
    private static final String RECORD_LIST_BUTTON_KEY = "patientInfo.recordListButton";
    private static final String DISCHARGE_PATIENT_BUTTON_KEY = "patientInfo.dischargeButton";
    private static final String EXECUTE_PROCEDURE_BUTTON_KEY = "patientInfo.executeProcedure";
    private static final String EXECUTE_SURGERY_BUTTON_KEY = "patientInfo.executeSurgery";
    private static final String CREATE_RECORD_BUTTON_KEY = "patientInfo.createRecordButton";

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession session = pageContext.getSession();
        String locale = (String) session.getAttribute(SessionAttribute.CURRENT_LOCALE);
        ResourceBundle bundle = TagUtil.getMessageBundle(locale);
        createUpdatePatientButton(out, session, bundle);
        createRecordListButton(out, bundle);
        createNewRecordButton(out, session, bundle);
        createDischargePatientButton(out, session, bundle);
        createExecuteProcedureOrSurgeryButton(out, session, bundle);
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }

    private void createUpdatePatientButton(JspWriter out, HttpSession session, ResourceBundle bundle)
            throws JspException {
        String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
        if (userRole.equals(SessionAttribute.DOCTOR_ROLE) || userRole.equals(SessionAttribute.ADMIN_ROLE)) {
            try {
                out.write("<button class=\"list-group-item list-group-item-action\" type=\"submit\" ");
                out.write("name=\"command\" value=\"" + CommandType.REDIRECT_TO_UPDATE_PATIENT_PAGE + "\" ");
                out.write("style=\"" + BUTTON_STYLE + "\">");
                out.write(bundle.getString(UPDATE_PATIENT_BUTTON_KEY));
                out.write("</button>");
            } catch (IOException e) {
                LOGGER.log(Level.FATAL, "Error while creating update patient button");
                throw new JspException("Error while creating update patient button", e);
            }
        }
    }

    private void createRecordListButton(JspWriter out, ResourceBundle bundle) throws JspException {
        try {
            out.write("<button class=\"list-group-item list-group-item-action\" type=\"submit\" name=\"command\" ");
            out.write("value=\"" + CommandType.RECORD_LIST_COMMAND + "\" ");
            out.write("style=\"" + BUTTON_STYLE + "\">");
            out.write(bundle.getString(RECORD_LIST_BUTTON_KEY));
            out.write("</button>");
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Error while creating update patient button");
            throw new JspException("Error while creating update patient button", e);
        }
    }

    private void createNewRecordButton(JspWriter out, HttpSession session, ResourceBundle bundle)
            throws JspException {
        String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
        if (userRole.equals(SessionAttribute.DOCTOR_ROLE) || userRole.equals(SessionAttribute.ADMIN_ROLE)) {
            Map<String, String> patientFields =
                    (Map<String, String>) session.getAttribute(SessionAttribute.PATIENT_DATA_FIELDS);
            String stringStatus = patientFields.get(RequestParameter.PATIENT_STATUS);
            Entity.Status patientStatus = Entity.Status.valueOf(stringStatus);
            if (patientStatus == Entity.Status.ARCHIVE || patientStatus == Entity.Status.WAITING_FOR_DECISION) {
                try {
                    out.write("<button class=\"list-group-item list-group-item-action\" type=\"submit\" ");
                    out.write("name=\"command\" value=\"" + CommandType.REDIRECT_TO_RECORD_CREATING_PAGE + "\" ");
                    out.write("style=\"" + BUTTON_STYLE + "\">");
                    out.write(bundle.getString(CREATE_RECORD_BUTTON_KEY));
                    out.write("</button>");
                } catch (IOException e) {
                    LOGGER.log(Level.FATAL, "Error while creating new record button", e);
                    throw new JspException("Error while creating new record button", e);
                }
            }
        }
    }

    private void createDischargePatientButton(JspWriter out, HttpSession session, ResourceBundle bundle)
            throws JspException {
        String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
        if (userRole.equals(SessionAttribute.DOCTOR_ROLE) || userRole.equals(SessionAttribute.ADMIN_ROLE)) {
            Map<String, String> patientFields =
                    (Map<String, String>) session.getAttribute(SessionAttribute.PATIENT_DATA_FIELDS);
            String stringStatus = patientFields.get(RequestParameter.PATIENT_STATUS);
            Entity.Status patientStatus = Entity.Status.valueOf(stringStatus);
            if (patientStatus == Entity.Status.WAITING_FOR_DECISION) {
                try {
                    out.write("<button class=\"list-group-item list-group-item-action\" type=\"submit\" ");
                    out.write("name=\"command\" value=\"" + CommandType.DISCHARGE_PATIENT + "\" ");
                    out.write("style=\"" + BUTTON_STYLE + "\">");
                    out.write(bundle.getString(DISCHARGE_PATIENT_BUTTON_KEY));
                    out.write("</button>");
                } catch (IOException e) {
                    LOGGER.log(Level.FATAL, "Error while creating discharge patient button", e);
                    throw new JspException("Error while creating discharge patient button", e);
                }
            }
        }
    }

    private void createExecuteProcedureOrSurgeryButton(JspWriter out, HttpSession session, ResourceBundle bundle)
            throws JspException {
        Map<String, String> patientFields =
                (Map<String, String>) session.getAttribute(SessionAttribute.PATIENT_DATA_FIELDS);
        String stringStatus = patientFields.get(RequestParameter.PATIENT_STATUS);
        Entity.Status patientStatus = Entity.Status.valueOf(stringStatus);
        if (patientStatus == Entity.Status.WAITING_FOR_CURING) {
            int lastRecordId = Integer.parseInt(patientFields.get(RequestParameter.LAST_RECORD_ID));
            RecordService recordService = RecordServiceImpl.INSTANCE;
            try {
                Optional<Treatment> optionalTreatment = recordService.findTreatment(lastRecordId);
                Treatment treatment = optionalTreatment.orElseThrow();
                String userRole = (String) session.getAttribute(SessionAttribute.USER_ROLE);
                switch (treatment) {
                    case SURGERY -> createExecuteSurgeryButton(out, bundle, userRole);
                    case PROCEDURE -> createExecuteProcedureButton(out, bundle, userRole);
                    default -> LOGGER.log(Level.ERROR, "Incorrect treatment, can't create a button");
                }
            } catch (ServiceException e) {
                LOGGER.log(Level.FATAL, "Error while finding patient's treatment", e);
                throw new JspException("Error while finding patient's treatment", e);
            }
        }
    }

    private void createExecuteProcedureButton(JspWriter out, ResourceBundle bundle, String userRole)
            throws JspException {
        if (!userRole.equals(SessionAttribute.GUEST_ROLE)) {
            try {
                out.write("<button class=\"list-group-item list-group-item-action\" type=\"submit\" ");
                out.write("name=\"command\" value=\"" + CommandType.EXECUTE_PROCEDURE + "\" ");
                out.write("style=\"" + BUTTON_STYLE + "\">");
                out.write(bundle.getString(EXECUTE_PROCEDURE_BUTTON_KEY));
                out.write("</button>");
            } catch (IOException e) {
                LOGGER.log(Level.FATAL, "Error while creating execute procedure button", e);
                throw new JspException("Error while creating execute procedure button", e);
            }
        }
    }

    private void createExecuteSurgeryButton(JspWriter out, ResourceBundle bundle, String userRole)
            throws JspException {
        if (userRole.equals(SessionAttribute.DOCTOR_ROLE) || userRole.equals(SessionAttribute.ADMIN_ROLE)) {
            try {
                out.write("<button class=\"list-group-item list-group-item-action\" type=\"submit\" ");
                out.write("name=\"command\" value=\"" + CommandType.EXECUTE_SURGERY + "\" ");
                out.write("style=\"" + BUTTON_STYLE + "\">");
                out.write(bundle.getString(EXECUTE_SURGERY_BUTTON_KEY));
                out.write("</button>");
            } catch (IOException e) {
                LOGGER.log(Level.FATAL, "Error while creating execute procedure button", e);
                throw new JspException("Error while creating execute procedure button", e);
            }
        }
    }
}
