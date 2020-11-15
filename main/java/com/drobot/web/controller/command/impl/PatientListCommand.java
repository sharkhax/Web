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
import com.drobot.web.model.entity.Patient;
import com.drobot.web.model.service.PatientService;
import com.drobot.web.model.service.impl.PatientServiceImpl;
import com.drobot.web.tag.PatientListTag;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@CommandAccessLevel({AccessType.ADMIN, AccessType.DOCTOR, AccessType.ASSISTANT})
public class PatientListCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(PatientListCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page = UrlPattern.PATIENT_LIST;
        HttpSession session = request.getSession();
        Integer currentPage = (Integer) session.getAttribute(SessionAttribute.PATIENT_LIST_CURRENT_PAGE);
        String requestedPage = request.getParameter(RequestParameter.LIST_PAGE);
        if (requestedPage == null && currentPage == null) {
            currentPage = 1;
        } else if (requestedPage != null) {
            currentPage = Integer.parseInt(requestedPage);
        }
        session.setAttribute(SessionAttribute.PATIENT_LIST_CURRENT_PAGE, currentPage);
        int start = (currentPage - 1) * PatientListTag.ROWS_NUMBER;
        int end = PatientListTag.ROWS_NUMBER + start;
        String sortBy = (String) session.getAttribute(SessionAttribute.PATIENT_LIST_SORT_BY);
        String requestedSortBy = request.getParameter(RequestParameter.SORT_BY);
        Boolean reverseSorting = (Boolean) session.getAttribute(SessionAttribute.PATIENT_LIST_REVERSE_SORTING);
        if (reverseSorting == null) {
            reverseSorting = false;
        }
        if (requestedSortBy == null && sortBy == null) {
            sortBy = ColumnName.PATIENT_ID;
        } else if (requestedSortBy != null) {
            if (requestedSortBy.equals(sortBy)) {
                reverseSorting = !reverseSorting;
            } else {
                reverseSorting = false;
            }
            sortBy = requestedSortBy;
        }
        session.setAttribute(SessionAttribute.PATIENT_LIST_SORT_BY, sortBy);
        session.setAttribute(SessionAttribute.PATIENT_LIST_REVERSE_SORTING, reverseSorting);
        PatientService patientService = PatientServiceImpl.INSTANCE;
        try {
            List<Patient> patientList = patientService.findAll(start, end, sortBy, reverseSorting);
            session.setAttribute(SessionAttribute.PATIENT_LIST, patientList);
            int patientsNumber = patientService.count();
            session.setAttribute(SessionAttribute.PATIENTS_NUMBER, patientsNumber);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        LOGGER.log(Level.INFO, "Patient list has been got");
        return page;
    }
}
