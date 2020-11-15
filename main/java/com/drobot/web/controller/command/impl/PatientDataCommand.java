package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.UrlPattern;
import com.drobot.web.controller.command.AccessType;
import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandAccessLevel;
import com.drobot.web.exception.CommandException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.Patient;
import com.drobot.web.model.service.PatientService;
import com.drobot.web.model.service.impl.PatientServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

/**
 * Action command for loading patient's data.
 *
 * @author Vladislav Drobot
 */
@CommandAccessLevel({AccessType.ADMIN, AccessType.DOCTOR, AccessType.ASSISTANT})
public class PatientDataCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(PatientDataCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        String stringPatientId = request.getParameter(RequestParameter.PATIENT_INFO_ID);
        int patientId;
        try {
            patientId = Integer.parseInt(stringPatientId);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, "Incorrect patient id value, returning null");
            return null;
        }
        PatientService patientService = PatientServiceImpl.INSTANCE;
        try {
            Optional<Patient> optionalPatient = patientService.findById(patientId);
            if (optionalPatient.isPresent()) {
                Patient patient = optionalPatient.get();
                Map<String, String> fields = patientService.packPatientIntoMap(patient);
                HttpSession session = request.getSession();
                session.setAttribute(SessionAttribute.PATIENT_DATA_FIELDS, fields);
                session.setAttribute(SessionAttribute.PATIENT_INFO_ID, patientId);
                StringBuilder sb = new StringBuilder(UrlPattern.PATIENT_INFO);
                page = sb.deleteCharAt(sb.length() - 1).append(patientId).toString();
                LOGGER.log(Level.INFO, "Patient data has been got");
            } else {
                LOGGER.log(Level.INFO, "No patient found");
                page = null;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
