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
import com.drobot.web.model.service.RecordService;
import com.drobot.web.model.service.impl.PatientServiceImpl;
import com.drobot.web.model.service.impl.RecordServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

/**
 * Action command for redirecting to creating a new record page.
 *
 * @author Vladislav Drobot
 */
@CommandAccessLevel({AccessType.ADMIN, AccessType.DOCTOR})
public class RedirectToRecordCreatingCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(RedirectToRecordCreatingCommand.class);

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
        HttpSession session = request.getSession();
        Map<String, String> loginInfo = (Map<String, String>) session.getAttribute(SessionAttribute.LOGIN_INFO);
        int userId = Integer.parseInt(loginInfo.get(SessionAttribute.USER_ID));
        RecordService recordService = RecordServiceImpl.INSTANCE;
        try {
            Map<String, String> creatingRecordData = recordService.findDataForNewRecord(patientId, userId);
            if (creatingRecordData.isEmpty()) {
                LOGGER.log(Level.INFO, "User is not allowed to create a new record or patient hasn't been found");
                page = null;
            } else {
                session.setAttribute(SessionAttribute.CREATING_RECORD_DATA, creatingRecordData);
                Integer patientInfoId = (Integer) session.getAttribute(SessionAttribute.PATIENT_INFO_ID);
                if (patientInfoId == null || patientInfoId != patientId) {
                    PatientService patientService = PatientServiceImpl.INSTANCE;
                    Optional<Patient> optionalPatient = patientService.findById(patientId);
                    Patient patient = optionalPatient.orElseThrow();
                    Map<String, String> fields = patientService.packIntoMap(patient);
                    session.setAttribute(SessionAttribute.PATIENT_DATA_FIELDS, fields);
                    session.setAttribute(SessionAttribute.PATIENT_INFO_ID, patientId);
                    LOGGER.log(Level.DEBUG, "Patient data has been refilled");
                }
                LOGGER.log(Level.INFO, "Redirecting to creating record page");
                StringBuilder sb = new StringBuilder(UrlPattern.RECORD_CREATING);
                int indexOfAsterisk = sb.indexOf(UrlPattern.ASTERISK);
                sb.replace(indexOfAsterisk, indexOfAsterisk + 1, stringPatientId);
                page = sb.toString();
                session.setAttribute(SessionAttribute.RECORD_CREATION_ALLOWED, true);
                LOGGER.log(Level.DEBUG, "\"RECORD_CREATION_ALLOWED\" flag has been set to true");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
