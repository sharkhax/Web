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
import com.drobot.web.model.entity.PatientRecord;
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

@CommandAccessLevel({AccessType.ADMIN, AccessType.DOCTOR, AccessType.ASSISTANT})
public class RecordDataCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger(RecordDataCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        String stringRecordId = request.getParameter(RequestParameter.RECORD_INFO_ID);
        int recordId;
        try {
            recordId = Integer.parseInt(stringRecordId);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, "Incorrect record id value, returning null");
            return null;
        }
        RecordService recordService = RecordServiceImpl.INSTANCE;
        try {
            Optional<PatientRecord> optionalRecord = recordService.findById(recordId);
            if (optionalRecord.isPresent()) {
                PatientRecord record = optionalRecord.get();
                Map<String, String> fields = recordService.packRecordIntoMap(record);
                HttpSession session = request.getSession();
                session.setAttribute(SessionAttribute.RECORD_DATA_FIELDS, fields);
                session.setAttribute(SessionAttribute.RECORD_INFO_ID, recordId);
                StringBuilder sb = new StringBuilder(UrlPattern.RECORD_INFO);
                page = sb.deleteCharAt(sb.length() - 1).append(recordId).toString();
                LOGGER.log(Level.INFO, "Record data has been got");
            } else {
                LOGGER.log(Level.INFO, "No record found");
                page = null;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }
}
