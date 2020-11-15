package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.UrlPattern;
import com.drobot.web.controller.command.AccessType;
import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandAccessLevel;
import com.drobot.web.exception.CommandException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.service.PatientService;
import com.drobot.web.model.service.RecordService;
import com.drobot.web.model.service.impl.PatientServiceImpl;
import com.drobot.web.model.service.impl.RecordServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@CommandAccessLevel({AccessType.ADMIN, AccessType.DOCTOR})
public class DischargePatientCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(DischargePatientCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        int patientId = Integer.parseInt(request.getParameter(RequestParameter.PATIENT_ID));
        PatientService recordService = PatientServiceImpl.INSTANCE;
        try {
            if (recordService.discharge(patientId)) {
                HttpSession session = request.getSession();
                Map<String, String> patientFields =
                        (Map<String, String>) session.getAttribute(SessionAttribute.PATIENT_DATA_FIELDS);
                patientFields.replace(RequestParameter.PATIENT_STATUS, Entity.Status.ARCHIVE.toString());
                session.setAttribute(SessionAttribute.PATIENT_DATA_FIELDS, patientFields);
                StringBuilder sb = new StringBuilder(UrlPattern.PATIENT_INFO);
                page = sb.deleteCharAt(sb.length() - 1).append(patientId).toString();
                LOGGER.log(Level.INFO, "Patient has been discharged");
            } else {
                page = null;
                LOGGER.log(Level.INFO, "Patient has not been discharged, no access");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
