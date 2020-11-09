package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.UrlPattern;
import com.drobot.web.controller.command.AccessType;
import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandAccessLevel;
import com.drobot.web.exception.CommandException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.service.PatientService;
import com.drobot.web.model.service.impl.PatientServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@CommandAccessLevel({AccessType.ADMIN, AccessType.DOCTOR})
public class RegisterPatientCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(RegisterPatientCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        Map<String, String> fields = new HashMap<>();
        HttpSession session = request.getSession();
        String name = request.getParameter(RequestParameter.PATIENT_NAME);
        String surname = request.getParameter(RequestParameter.PATIENT_SURNAME);
        String age = request.getParameter(RequestParameter.PATIENT_AGE);
        String gender = request.getParameter(RequestParameter.PATIENT_GENDER);
        String diagnosis = request.getParameter(RequestParameter.DIAGNOSIS);
        fields.put(RequestParameter.PATIENT_NAME, name);
        fields.put(RequestParameter.PATIENT_SURNAME, surname);
        fields.put(RequestParameter.PATIENT_AGE, age);
        fields.put(RequestParameter.PATIENT_GENDER, gender);
        fields.put(RequestParameter.DIAGNOSIS, diagnosis);
        Map<String, String> existingFields = new HashMap<>();
        PatientService patientService = PatientServiceImpl.INSTANCE;
        try {
            if (patientService.add(fields, existingFields)) {
                page = UrlPattern.PATIENT_CREATING_SUCCESS;
                session.setAttribute(SessionAttribute.PATIENT_CREATING_FIELDS, null);
                session.setAttribute(SessionAttribute.PATIENT_CREATING_EXISTING_FIELDS, null);
                LOGGER.log(Level.INFO, "Patient has been created successfully");
            } else {
                page = UrlPattern.PATIENT_CREATING_FAIL;
                session.setAttribute(SessionAttribute.PATIENT_CREATING_FIELDS, fields);
                session.setAttribute(SessionAttribute.PATIENT_CREATING_EXISTING_FIELDS, existingFields);
                LOGGER.log(Level.INFO, "Patient registration failed");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
