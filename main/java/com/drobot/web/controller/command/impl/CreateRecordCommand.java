package com.drobot.web.controller.command.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.controller.SessionAttribute;
import com.drobot.web.controller.UrlPattern;
import com.drobot.web.controller.command.AccessType;
import com.drobot.web.controller.command.ActionCommand;
import com.drobot.web.controller.command.CommandAccessLevel;
import com.drobot.web.exception.CommandException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.service.EmployeeService;
import com.drobot.web.model.service.PatientService;
import com.drobot.web.model.service.RecordService;
import com.drobot.web.model.service.impl.EmployeeServiceImpl;
import com.drobot.web.model.service.impl.PatientServiceImpl;
import com.drobot.web.model.service.impl.RecordServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@CommandAccessLevel({AccessType.ADMIN, AccessType.DOCTOR})
public class CreateRecordCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(CreateRecordCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        String stringPatientId = request.getParameter(RequestParameter.PATIENT_ID);
        String stringDoctorId = request.getParameter(RequestParameter.ATTENDING_DOCTOR_ID);
        int patientId;
        int doctorId;
        try {
            patientId = Integer.parseInt(stringPatientId);
            doctorId = Integer.parseInt(stringDoctorId);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, "One of id values is not valid, returning null");
            return null;
        }
        PatientService patientService = PatientServiceImpl.INSTANCE;
        EmployeeService employeeService = EmployeeServiceImpl.INSTANCE;
        try {
            if (patientService.exists(patientId) && employeeService.exists(doctorId)) {
                String diagnosis = request.getParameter(RequestParameter.PATIENT_DIAGNOSIS);
                String treatment = request.getParameter(RequestParameter.PATIENT_TREATMENT);
                Map<String, String> fields = new HashMap<>();
                fields.put(RequestParameter.PATIENT_ID, String.valueOf(patientId));
                fields.put(RequestParameter.ATTENDING_DOCTOR_ID, String.valueOf(doctorId));
                fields.put(RequestParameter.PATIENT_DIAGNOSIS, diagnosis);
                fields.put(RequestParameter.PATIENT_TREATMENT, treatment);
                RecordService recordService = RecordServiceImpl.INSTANCE;
                HttpSession session = request.getSession();
                if (recordService.add(fields)) {
                    session.setAttribute(SessionAttribute.RECORD_CREATING_FIELDS, null);
                    page = UrlPattern.PATIENT_INFO_REQUEST + patientId;
                    LOGGER.log(Level.INFO, "Record has been created successfully");
                } else {
                    session.setAttribute(SessionAttribute.RECORD_CREATING_FIELDS, fields);
                    session.setAttribute(SessionAttribute.VALIDATED, true);
                    StringBuilder sb = new StringBuilder(UrlPattern.RECORD_CREATING);
                    int indexOfAsterisk = sb.indexOf(UrlPattern.ASTERISK);
                    page = sb.replace(indexOfAsterisk, indexOfAsterisk + 1, String.valueOf(patientId)).toString();
                    session.setAttribute(SessionAttribute.RECORD_CREATION_ALLOWED, true);
                    LOGGER.log(Level.DEBUG, "\"RECORD_CREATION_ALLOWED\" flag has been set to true");
                    LOGGER.log(Level.INFO, "Record has not been created");
                }
            } else {
                LOGGER.log(Level.ERROR, "Such patient or doctor doesn't exist");
                page = null;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
