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

@CommandAccessLevel({AccessType.ADMIN, AccessType.DOCTOR})
public class RedirectToUpdatePatientCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(RedirectToUpdatePatientCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        String stringPatientId = request.getParameter(RequestParameter.PATIENT_ID);
        int patientId;
        try {
            patientId = Integer.parseInt(stringPatientId);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, "Incorrect patient ID value");
            return null;
        }
        try {
            PatientService patientService = PatientServiceImpl.INSTANCE;
            if (patientService.exists(patientId)) {
                HttpSession session = request.getSession();
                Integer patientInfoId = (Integer) session.getAttribute(SessionAttribute.PATIENT_INFO_ID);
                if (patientInfoId == null || patientId != patientInfoId) {
                    Optional<Patient> optionalPatient = patientService.findById(patientId);
                    Patient patient = optionalPatient.orElseThrow();
                    Map<String, String> fields = patientService.packPatientIntoMap(patient);
                    session.setAttribute(SessionAttribute.PATIENT_DATA_FIELDS, fields);
                    session.setAttribute(SessionAttribute.PATIENT_INFO_ID, patientId);
                    LOGGER.log(Level.DEBUG, "User data fields have been refilled");
                }
                StringBuilder sb = new StringBuilder(UrlPattern.UPDATING_PATIENT);
                int indexOfAsterisk = sb.indexOf(UrlPattern.ASTERISK);
                sb.replace(indexOfAsterisk, indexOfAsterisk + 1, stringPatientId);
                page = sb.toString();
                LOGGER.log(Level.DEBUG, "Flag \"PATIENT_EXISTS\" has been set to true");
                session.setAttribute(SessionAttribute.PATIENT_EXISTS, true);
            } else {
                LOGGER.log(Level.DEBUG, "Patient with id " + stringPatientId + " doesn't exist, returning null");
                page = null;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
