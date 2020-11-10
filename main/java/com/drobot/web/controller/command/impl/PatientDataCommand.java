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

@CommandAccessLevel({AccessType.ADMIN, AccessType.DOCTOR, AccessType.ASSISTANT})
public class PatientDataCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(PatientDataCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        String stringPatientId = request.getParameter(RequestParameter.PATIENT_INFO_ID);
        int patientId;
        try {
            patientId = Integer.parseInt(stringPatientId != null ? stringPatientId : "");
        } catch (NumberFormatException e) {
            page = UrlPattern.PATIENT_LIST;
            LOGGER.log(Level.ERROR, "Incorrect patient id value, redirected to patient list");
            return page;
        }
        PatientService patientService = PatientServiceImpl.INSTANCE;
        try {
            Optional<Patient> optionalPatient = patientService.findById(patientId);
            if (optionalPatient.isPresent()) {
                Patient patient = optionalPatient.get();
                Map<String, String> fields = new HashMap<>();
                fields.put(RequestParameter.PATIENT_ID, String.valueOf(patientId));
                fields.put(RequestParameter.PATIENT_NAME, patient.getName());
                fields.put(RequestParameter.PATIENT_SURNAME, patient.getSurname());
                fields.put(RequestParameter.PATIENT_AGE, String.valueOf(patient.getAge()));
                fields.put(RequestParameter.PATIENT_GENDER, String.valueOf(patient.getGender()));
                fields.put(RequestParameter.PATIENT_DIAGNOSIS, patient.getDiagnosis());
                fields.put(RequestParameter.PATIENT_STATUS, patient.getStatus().toString());
                String stringRecordId;
                int recordId = patient.getRecordId();
                if (recordId == 0) {
                    stringRecordId = "-";
                } else {
                    stringRecordId = String.valueOf(recordId);
                }
                fields.put(RequestParameter.LAST_RECORD_ID, stringRecordId);
                HttpSession session = request.getSession();
                session.setAttribute(SessionAttribute.PATIENT_DATA_FIELDS, fields);
                session.setAttribute(SessionAttribute.PATIENT_INFO_ID, patientId);
                LOGGER.log(Level.DEBUG, "Patient data has been got");
                StringBuilder sb = new StringBuilder(UrlPattern.PATIENT_INFO);
                page = sb.deleteCharAt(sb.length() - 1).append(patientId).toString();
            } else {
                LOGGER.log(Level.DEBUG, "No patient found");
                page = null;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
