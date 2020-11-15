package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.UrlPattern;
import com.drobot.web.controller.command.AccessType;
import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandAccessLevel;
import com.drobot.web.exception.CommandException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.dao.ColumnName;
import com.drobot.web.model.entity.SpecifiedRecord;
import com.drobot.web.model.service.PatientService;
import com.drobot.web.model.service.RecordService;
import com.drobot.web.model.service.impl.PatientServiceImpl;
import com.drobot.web.model.service.impl.RecordServiceImpl;
import com.drobot.web.tag.RecordListTag;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Action command for loading record list.
 *
 * @author Vladislav Drobot
 */
@CommandAccessLevel({AccessType.ADMIN, AccessType.DOCTOR, AccessType.ASSISTANT})
public class RecordListCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(RecordListCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        String stringPatientId = request.getParameter(RequestParameter.PATIENT_ID);
        int patientId;
        try {
            patientId = Integer.parseInt(stringPatientId);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, "Incorrect patient id value");
            return null;
        }
        PatientService patientService = PatientServiceImpl.INSTANCE;
        try {
            if (patientService.exists(patientId)) {
                HttpSession session = request.getSession();
                Integer currentPage = (Integer) session.getAttribute(SessionAttribute.RECORD_LIST_CURRENT_PAGE);
                String requestedPage = request.getParameter(RequestParameter.LIST_PAGE);
                if (requestedPage == null && currentPage == null) {
                    currentPage = 1;
                } else if (requestedPage != null) {
                    currentPage = Integer.parseInt(requestedPage);
                }
                session.setAttribute(SessionAttribute.RECORD_LIST_CURRENT_PAGE, currentPage);
                int start = (currentPage - 1) * RecordListTag.ROWS_NUMBER;
                int end = RecordListTag.ROWS_NUMBER + start;
                String sortBy = (String) session.getAttribute(SessionAttribute.RECORD_LIST_SORT_BY);
                String requestedSortBy = request.getParameter(RequestParameter.SORT_BY);
                Boolean reverseSorting = (Boolean) session.getAttribute(SessionAttribute.RECORD_LIST_REVERSE_SORTING);
                if (reverseSorting == null) {
                    reverseSorting = false;
                }
                if (requestedSortBy == null && sortBy == null) {
                    sortBy = ColumnName.RECORD_ID;
                } else if (requestedSortBy != null) {
                    if (requestedSortBy.equals(sortBy)) {
                        reverseSorting = !reverseSorting;
                    } else {
                        reverseSorting = false;
                    }
                    sortBy = requestedSortBy;
                }
                session.setAttribute(SessionAttribute.RECORD_LIST_SORT_BY, sortBy);
                session.setAttribute(SessionAttribute.RECORD_LIST_REVERSE_SORTING, reverseSorting);
                RecordService recordService = RecordServiceImpl.INSTANCE;
                List<SpecifiedRecord> recordList =
                        recordService.findByPatientId(patientId, start, end, sortBy, reverseSorting);
                session.setAttribute(SessionAttribute.RECORD_LIST, recordList);
                int recordsNumber = recordService.count(patientId);
                session.setAttribute(SessionAttribute.RECORDS_NUMBER, recordsNumber);
                StringBuilder sb = new StringBuilder(UrlPattern.RECORD_LIST);
                int indexOfAsterisk = sb.indexOf(UrlPattern.ASTERISK);
                page = sb.replace(indexOfAsterisk, indexOfAsterisk + 1, stringPatientId).toString();
                session.setAttribute(SessionAttribute.PATIENT_EXISTS, true);
                LOGGER.log(Level.DEBUG, "Flag PATIENT_EXISTS has been set to true");
                LOGGER.log(Level.INFO, "Record list has been got");
            } else {
                LOGGER.log(Level.INFO, "Patient with id " + stringPatientId + " doesn't exist");
                page = null;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
