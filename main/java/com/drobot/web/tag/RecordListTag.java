package com.drobot.web.tag;

import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.command.CommandType;
import com.drobot.web.model.dao.ColumnName;
import com.drobot.web.model.entity.Patient;
import com.drobot.web.model.entity.SpecifiedRecord;
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

/**
 * Custom tag that creates a table of records.
 */
public class RecordListTag extends TagSupport {

    /**
     * Represents a number of rows in the table.
     */
    public static final int ROWS_NUMBER = 10;
    private static final Logger LOGGER = LogManager.getLogger(RecordListTag.class);
    private static final String ID_KEY = "table.id";
    private static final String DOCTOR_NAME_KEY = "table.attendingDoctor";
    private static final String TREATMENT_KEY = "table.treatment";
    private static final String EXECUTOR_NAME_KEY = "table.executorName";
    private static final String DIAGNOSIS_KEY = "table.diagnosis";
    private static final String HEAD_BUTTON_STYLE = "background-color: #fff0; border: #fff0; font-weight: bold;";
    private int patientId;

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession session = pageContext.getSession();
        createList(out, session);
        int currentPage = (int) session.getAttribute(SessionAttribute.RECORD_LIST_CURRENT_PAGE);
        int recordsNumber = (int) session.getAttribute(SessionAttribute.RECORDS_NUMBER);
        int pagesNumber = recordsNumber % ROWS_NUMBER == 0
                ? recordsNumber / ROWS_NUMBER : recordsNumber / ROWS_NUMBER + 1;
        String command = CommandType.RECORD_LIST_COMMAND.toString().toLowerCase();
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
            List<SpecifiedRecord> recordList = (List<SpecifiedRecord>) session.getAttribute(SessionAttribute.RECORD_LIST);
            createRows(out, session, recordList);
            out.write("</tbody></table>");
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Error while creating record list", e);
            throw new JspException(e);
        }
    }

    private void createHead(JspWriter out, HttpSession session) throws JspException {
        String lang = (String) session.getAttribute(SessionAttribute.CURRENT_LOCALE);
        ResourceBundle bundle = TagUtil.getMessageBundle(lang);
        String recordId = bundle.getString(ID_KEY);
        String doctorName = bundle.getString(DOCTOR_NAME_KEY);
        String diagnosis = bundle.getString(DIAGNOSIS_KEY);
        String treatment = bundle.getString(TREATMENT_KEY);
        String executorName = bundle.getString(EXECUTOR_NAME_KEY);
        String sortedBy = (String) session.getAttribute(SessionAttribute.RECORD_LIST_SORT_BY);
        boolean reverseSorting = (boolean) session.getAttribute(SessionAttribute.RECORD_LIST_REVERSE_SORTING);
        String numericArrow = reverseSorting ? TagUtil.SORT_NUMERIC_UP_IMAGE : TagUtil.SORT_NUMERIC_DOWN_IMAGE;
        String alphaArrow = reverseSorting ? TagUtil.SORT_ALPHA_UP_IMAGE : TagUtil.SORT_ALPHA_DOWN_IMAGE;
        switch (sortedBy) {
            case ColumnName.RECORD_ID -> recordId = recordId + " " + numericArrow;
            case ColumnName.DOCTOR_NAME -> doctorName = doctorName + " " + alphaArrow;
            case ColumnName.EXECUTOR_NAME -> executorName = executorName + " " + alphaArrow;
            case ColumnName.TREATMENT_NAME -> treatment = treatment + " " + alphaArrow;
            case ColumnName.DIAGNOSIS -> diagnosis = diagnosis + " " + alphaArrow;
        }
        try {
            out.write("<form action=\"/mainController\" method=\"post\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"");
            out.write(CommandType.RECORD_LIST_COMMAND.toString() + "\"/>");
            out.write("<input type=\"hidden\" name=\"patientId\" value=\"" + patientId + "\"/>");
            out.write("<thead class=\"thead-light\"><tr>");
            out.write("<th scope=\"col\"><span style=\"font-weight: bold\">№</span></th>");
            TagUtil.createTableHeadButton(out, recordId, HEAD_BUTTON_STYLE, ColumnName.RECORD_ID);
            TagUtil.createTableHeadButton(out, doctorName, HEAD_BUTTON_STYLE, ColumnName.DOCTOR_NAME);
            TagUtil.createTableHeadButton(out, diagnosis, HEAD_BUTTON_STYLE, ColumnName.DIAGNOSIS);
            TagUtil.createTableHeadButton(out, treatment, HEAD_BUTTON_STYLE, ColumnName.TREATMENT_NAME);
            TagUtil.createTableHeadButton(out, executorName, HEAD_BUTTON_STYLE, ColumnName.EXECUTOR_NAME);
            out.write("</tr></thead></form>");
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Error while creating record list rows", e);
            throw new JspException("Error while creating record list rows", e);
        }
    }

    private void createRows(JspWriter out, HttpSession session, List<SpecifiedRecord> recordList) throws JspException {
        try {
            if (recordList != null) {
                int size = recordList.size();
                int currentPage = (int) session.getAttribute(SessionAttribute.RECORD_LIST_CURRENT_PAGE);
                for (int i = 0; i < ROWS_NUMBER; i++) {
                    int rowNumber = ROWS_NUMBER * (currentPage - 1) + i + 1;
                    if (size > i) {
                        SpecifiedRecord record = recordList.get(i);
                        out.write("<tr style=\"cursor: pointer\" onclick=\"goToRecordInfo("
                                + record.getId() + ")\"><th scope=\"row\">" + rowNumber + "</th>");
                        out.write("<td>" + record.getId() + "</td>");
                        out.write("<td>" + record.getDoctorName() + " " + record.getDoctorSurname() + "</td>");
                        out.write("<td>" + record.getDiagnosis() + "</td>");
                        out.write("<td>" + record.getTreatment() + "</td>");
                        String executorName = record.getExecutorName();
                        if (executorName == null || executorName.isEmpty()) {
                            out.write("<td>" + "-" + "</td>");
                        } else {
                            out.write("<td>" + record.getExecutorName() + " ");
                            out.write(record.getExecutorSurname() + "</td>");
                        }
                    } else {
                        out.write("<tr><th scope=\"row\">" + rowNumber + "</th>");
                        out.write("<td></td><td></td><td></td><td></td><td></td>");
                    }
                    out.write("</tr>");
                }
                LOGGER.log(Level.DEBUG, "Table has been created");
            } else {
                LOGGER.log(Level.ERROR, "Record list is null");
            }
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Error while creating record list rows", e);
            throw new JspException("Error while creating record list rows", e);
        }
    }
}
