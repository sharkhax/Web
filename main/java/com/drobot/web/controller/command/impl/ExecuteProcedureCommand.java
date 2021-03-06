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
import com.drobot.web.model.service.RecordService;
import com.drobot.web.model.service.impl.RecordServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Action command for executing a procedure for a patient.
 *
 * @author Vladislav Drobot
 */
@CommandAccessLevel({AccessType.ADMIN, AccessType.DOCTOR, AccessType.ASSISTANT})
public class ExecuteProcedureCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(ExecuteProcedureCommand.class);

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
        RecordService recordService = RecordServiceImpl.INSTANCE;
        HttpSession session = request.getSession();
        Map<String, String> loginInfo = (Map<String, String>) session.getAttribute(SessionAttribute.LOGIN_INFO);
        int executorUserId = Integer.parseInt(loginInfo.get(SessionAttribute.USER_ID));
        try {
            if (recordService.executeProcedure(patientId, executorUserId)) {
                Map<String, String> patientFields =
                        (Map<String, String>) session.getAttribute(SessionAttribute.PATIENT_DATA_FIELDS);
                patientFields.replace(RequestParameter.PATIENT_STATUS, Entity.Status.WAITING_FOR_DECISION.toString());
                session.setAttribute(SessionAttribute.PATIENT_DATA_FIELDS, patientFields);
                StringBuilder sb = new StringBuilder(UrlPattern.PATIENT_INFO);
                page = sb.deleteCharAt(sb.length() - 1).append(patientId).toString();
                LOGGER.log(Level.INFO, "Procedure has been executed");
            } else {
                page = null;
                LOGGER.log(Level.INFO, "Procedure has not been executed, no access");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
