package com.drobot.web.tag;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.command.CommandType;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.entity.Patient;
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

public class PatientListTag extends TagSupport {

    public static final int ROWS_NUMBER = 10;
    private static final Logger LOGGER = LogManager.getLogger(PatientListTag.class);
    private static final String ID_KEY = "table.id";
    private static final String PATIENT_NAME_KEY = "table.patientName";
    private static final String PATIENT_SURNAME_KEY = "table.patientSurname";
    private static final String PATIENT_AGE_KEY = "table.patientAge";
    private static final String PATIENT_GENDER_KEY = "table.patientGender";
    private static final String DIAGNOSIS_KEY = "table.diagnosis";
    private static final String RECORD_ID_KEY = "table.recordId";
    private static final String STATUS_KEY = "table.patientStatus";

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession session = pageContext.getSession();
        createList(out, session);
        int currentPage = (int) session.getAttribute(RequestParameter.PATIENT_LIST_CURRENT_PAGE);
        int patientsNumber = (int) session.getAttribute(RequestParameter.PATIENTS_NUMBER);
        int pagesNumber = patientsNumber % ROWS_NUMBER == 0
                ? patientsNumber / ROWS_NUMBER : patientsNumber / ROWS_NUMBER + 1;
        String command = CommandType.PATIENT_LIST_COMMAND.toString().toLowerCase();
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
            List<Patient> patientList = (List<Patient>) session.getAttribute(RequestParameter.PATIENT_LIST);
            createRows(out, patientList);
            out.write("</tbody></table>");
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Error while creating user list", e);
            throw new JspException(e);
        }
    }

    private void createHead(JspWriter out, HttpSession session) throws JspException {
        String lang = (String) session.getAttribute(RequestParameter.CURRENT_LOCALE);
        ResourceBundle bundle = TagUtil.getMessageBundle(lang);
        String id = bundle.getString(ID_KEY);
        String name = bundle.getString(PATIENT_NAME_KEY);
        String surname = bundle.getString(PATIENT_SURNAME_KEY);
        String age = bundle.getString(PATIENT_AGE_KEY);
        String gender = bundle.getString(PATIENT_GENDER_KEY);
        String diagnosis = bundle.getString(DIAGNOSIS_KEY);
        String currentRecordId = bundle.getString(RECORD_ID_KEY);
        String status = bundle.getString(STATUS_KEY);
        try {
            out.write("<thead class=\"thead-light\"><tr>");
            out.write("<th scope=\"col\">#</th>");
            out.write("<th scope=\"col\">" + id + "</th>");
            out.write("<th scope=\"col\">" + name + "</th>");
            out.write("<th scope=\"col\">" + surname + "</th>");
            out.write("<th scope=\"col\">" + age + "</th>");
            out.write("<th scope=\"col\">" + gender + "</th>");
            out.write("<th scope=\"col\">" + diagnosis + "</th>");
            out.write("<th scope=\"col\">" + status + "</th>");
            out.write("<th scope=\"col\">" + currentRecordId + "</th>");
            out.write("</tr></thead>");
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Error while creating patient list head", e);
            throw new JspException(e);
        }
    }

    private void createRows(JspWriter out, List<Patient> patientList) throws JspException {
        try {
            if (patientList != null) {
                int size = patientList.size();
                for (int i = 0; i < ROWS_NUMBER; i++) {
                    out.write("<tr><th scope=\"row\">" + (i + 1) + "</th>");
                    if (size > i) {
                        Patient currentPatient = patientList.get(i);
                        out.write("<td>" + currentPatient.getId() + "</td>");
                        out.write("<td>" + currentPatient.getName() + "</td>");
                        out.write("<td>" + currentPatient.getSurname() + "</td>");
                        out.write("<td>" + currentPatient.getAge() + "</td>");
                        out.write("<td>" + currentPatient.getGender() + "</td>");
                        out.write("<td>" + currentPatient.getDiagnosis() + "</td>");
                        Entity.Status status = currentPatient.getStatus();
                        boolean isEven = i % 2 == 0;
                        String cellStyle = defineStyle(status, isEven);
                        out.write("<td style=\"" + cellStyle + "\">" + status + "</td>");
                        int recordId = currentPatient.getRecordId();
                        if (recordId == 0) {
                            out.write("<td>-</td>");
                        } else {
                            out.write("<td>" + recordId + "</td>");
                        }
                    } else {
                        out.write("<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>");
                    }
                    out.write("</tr>");
                }
                LOGGER.log(Level.DEBUG, "Table has been created");
            } else {
                LOGGER.log(Level.ERROR, "Patient list is null");
            }
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Error while creating patient list rows", e);
            throw new JspException(e);
        }
    }

    private String defineStyle(Entity.Status status, boolean isEven) {
        String result;
        switch (status) {
            case WAITING_FOR_DECISION -> result = isEven ? "background-color: #ffeebab8"
                    : "background-color: #f0e0b0b8";
            case WAITING_FOR_CURING -> result = isEven ? "background-color: #c3e6cbb8"
                    : "background-color: #b5d7bdb8";
            default -> result = "";
        }
        return result;
    }
}
