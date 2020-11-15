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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CommandAccessLevel({AccessType.ADMIN, AccessType.DOCTOR})
public class UpdatePatientCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(UpdatePatientCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        HttpSession session = request.getSession();
        Map<String, String> currentFields =
                (Map<String, String>) session.getAttribute(SessionAttribute.PATIENT_DATA_FIELDS);
        int patientId = Integer.parseInt(currentFields.get(RequestParameter.PATIENT_ID));
        String newName = request.getParameter(RequestParameter.PATIENT_NAME);
        String newSurname = request.getParameter(RequestParameter.PATIENT_SURNAME);
        String newAge = request.getParameter(RequestParameter.PATIENT_AGE);
        String newGender = request.getParameter(RequestParameter.PATIENT_GENDER);
        Map<String, String> newFields = new HashMap<>();
        Map<String, String> emptyFields = new HashMap<>();
        if (newName.isEmpty()) {
            emptyFields.put(RequestParameter.PATIENT_NAME, "true");
        } else {
            newFields.put(RequestParameter.PATIENT_NAME, newName);
        }
        if (newSurname.isEmpty()) {
            emptyFields.put(RequestParameter.PATIENT_SURNAME, "true");
        } else {
            newFields.put(RequestParameter.PATIENT_SURNAME, newSurname);
        }
        if (newAge.isEmpty()) {
            emptyFields.put(RequestParameter.PATIENT_AGE, "true");
        } else {
            newFields.put(RequestParameter.PATIENT_AGE, newAge);
        }
        if (newGender.isEmpty()) {
            emptyFields.put(RequestParameter.PATIENT_GENDER, "true");
        } else {
            newFields.put(RequestParameter.PATIENT_GENDER, newGender);
        }
        if (newFields.isEmpty()) {
            StringBuilder sb = new StringBuilder(UrlPattern.PATIENT_INFO);
            page = sb.deleteCharAt(sb.length() - 1).append(patientId).toString();
            LOGGER.log(Level.DEBUG, "New fields are absent");
        } else {
            PatientService patientService = PatientServiceImpl.INSTANCE;
            Map<String, String> existingFields = new HashMap<>();
            try {
                if (patientService.update(newFields, existingFields, currentFields)) {
                    Optional<Patient> optionalPatient = patientService.findById(patientId);
                    Patient patient = optionalPatient.orElseThrow();
                    Map<String, String> fields = patientService.packPatientIntoMap(patient);
                    session.setAttribute(SessionAttribute.PATIENT_DATA_FIELDS, fields);
                    LOGGER.log(Level.DEBUG, "Patient fields have been replaced");
                    StringBuilder sb = new StringBuilder(UrlPattern.PATIENT_INFO);
                    page = sb.deleteCharAt(sb.length() - 1).append(patientId).toString();
                    LOGGER.log(Level.INFO, "Patient has been updated successfully");
                } else {
                    StringBuilder sb = new StringBuilder(UrlPattern.UPDATING_PATIENT);
                    int indexOfAsterisk = sb.indexOf(UrlPattern.ASTERISK);
                    page = sb.replace(indexOfAsterisk, indexOfAsterisk + 1, String.valueOf(patientId)).toString();
                    session.setAttribute(SessionAttribute.PATIENT_DATA_EMPTY_FIELDS, emptyFields);
                    session.setAttribute(SessionAttribute.PATIENT_DATA_NEW_FIELDS, newFields);
                    session.setAttribute(SessionAttribute.PATIENT_DATA_EXISTING_FIELDS, existingFields);
                    session.setAttribute(SessionAttribute.VALIDATED, true);
                    session.setAttribute(SessionAttribute.PATIENT_EXISTS, true);
                    LOGGER.log(Level.DEBUG, "Flag \"PATIENT_EXISTS\" is set to true");
                    LOGGER.log(Level.INFO, "Patient has not been updated");
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        }
        return page;
    }
}
