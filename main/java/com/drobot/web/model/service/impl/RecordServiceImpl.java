package com.drobot.web.model.service.impl;

import com.drobot.web.controller.RequestParameter;
import com.drobot.web.exception.DaoException;
import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.dao.ColumnName;
import com.drobot.web.model.dao.EmployeeDao;
import com.drobot.web.model.dao.RecordDao;
import com.drobot.web.model.dao.impl.EmployeeDaoImpl;
import com.drobot.web.model.dao.impl.RecordDaoImpl;
import com.drobot.web.model.entity.*;
import com.drobot.web.model.service.EmployeeService;
import com.drobot.web.model.service.PatientService;
import com.drobot.web.model.service.RecordService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum RecordServiceImpl implements RecordService {

    INSTANCE;

    private final Logger LOGGER = LogManager.getLogger(RecordServiceImpl.class);
    private final RecordDao recordDao = RecordDaoImpl.INSTANCE;

    @Override
    public boolean add(Map<String, String> fields) throws ServiceException {
        boolean result = false;
        PatientMapService mapService = PatientMapService.INSTANCE;
        boolean isValid = true;
        int patientId = Integer.parseInt(fields.get(RequestParameter.PATIENT_ID));
        int doctorId = Integer.parseInt(fields.get(RequestParameter.ATTENDING_DOCTOR_ID));
        String stringTreatment = fields.get(RequestParameter.PATIENT_TREATMENT);
        if (!mapService.checkDiagnosis(fields)) {
            isValid = false;
        }
        Treatment treatment = null;
        try {
            treatment = Treatment.valueOf(stringTreatment.toUpperCase());
        } catch (IllegalArgumentException e) {
            isValid = false;
            LOGGER.log(Level.DEBUG, "Treatment is not valid");
        }
        if (isValid) {
            String diagnosis = fields.get(RequestParameter.PATIENT_DIAGNOSIS);
            PatientRecord record = new PatientRecord(patientId, doctorId, treatment, diagnosis);
            Entity.Status newPatientStatus = Entity.Status.WAITING_FOR_CURING;
            try {
                result = recordDao.addAndUpdatePatient(record, newPatientStatus);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            LOGGER.log(Level.DEBUG, "Some fields are not valid");
        }
        return result;
    }

    @Override
    public List<SpecifiedRecord> findByPatientId(int patientId, int start, int end, String sortBy, boolean reverse)
            throws ServiceException {
        List<SpecifiedRecord> result;
        if (patientId > 0) {
            if (start >= 0 && end > start) {
                if (checkSortingTagForSpecifiedRecord(sortBy)) {
                    try {
                        result = recordDao.findByPatientId(patientId, start, end, sortBy, reverse);
                    } catch (DaoException e) {
                        throw new ServiceException(e);
                    }
                } else {
                    result = List.of();
                    LOGGER.log(Level.ERROR, "Invalid sorting tag");
                }
            } else {
                result = List.of();
                LOGGER.log(Level.ERROR, "Invalid start or end values");
            }
        } else {
            result = List.of();
            LOGGER.log(Level.DEBUG, "Patient id is not valid");
        }
        return result;
    }

    @Override
    public Map<String, String> findDataForNewRecord(int patientId, int userId) throws ServiceException {
        Map<String, String> result = new HashMap<>();
        boolean isValid = false;
        Patient patient;
        Employee employee = null;
        PatientService patientService = PatientServiceImpl.INSTANCE;
        Optional<Entity.Status> optionalStatus = patientService.findStatus(patientId);
        if (optionalStatus.isPresent()) {
            Entity.Status patientStatus = optionalStatus.get();
            if (patientStatus == Entity.Status.ARCHIVE || patientStatus == Entity.Status.WAITING_FOR_DECISION) {
                EmployeeService employeeService = EmployeeServiceImpl.INSTANCE;
                Optional<Employee> optionalEmployee = employeeService.findByUserId(userId);
                if (optionalEmployee.isPresent()) {
                    employee = optionalEmployee.get();
                    if (employee.getPosition() == Employee.Position.DOCTOR) {
                        isValid = true;
                    } else {
                        LOGGER.log(Level.DEBUG, "Employee is not allowed to create a new record");
                    }
                } else {
                    LOGGER.log(Level.ERROR, "Employee has not been found");
                }
            } else {
                LOGGER.log(Level.DEBUG, "Patient has incorrect status");
            }
        } else {
            LOGGER.log(Level.DEBUG, "Patient has not been found");
        }
        if (isValid) {
            patient = patientService.findById(patientId).orElseThrow();
            String patientName = patient.getName();
            String patientSurname = patient.getSurname();
            String doctorId = String.valueOf(employee.getId());
            String doctorName = employee.getName();
            String doctorSurname = employee.getSurname();
            result.put(RequestParameter.ATTENDING_DOCTOR_ID, doctorId);
            result.put(RequestParameter.ATTENDING_DOCTOR_NAME, doctorName);
            result.put(RequestParameter.ATTENDING_DOCTOR_SURNAME, doctorSurname);
            result.put(RequestParameter.PATIENT_ID, String.valueOf(patientId));
            result.put(RequestParameter.PATIENT_NAME, patientName);
            result.put(RequestParameter.PATIENT_SURNAME, patientSurname);
            LOGGER.log(Level.DEBUG, "Creating record data has been created");
        }
        return result;
    }

    @Override
    public Optional<Treatment> findTreatment(int recordId) throws ServiceException {
        Optional<Treatment> result;
        try {
            if (recordId > 0) {
                result = recordDao.findTreatment(recordId);
            } else {
                LOGGER.log(Level.DEBUG, "Incorrect record id value");
                result = Optional.empty();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean executeProcedure(int patientId, int executorUserId) throws ServiceException {
        boolean result = false;
        try {
            if (patientId > 0 && executorUserId > 0) {
                Optional<PatientRecord> optional = recordDao.findLast(patientId);
                if (optional.isPresent()) {
                    PatientRecord record = optional.get();
                    int recordId = record.getId();
                    if (record.getExecutorId() == 0) {
                        EmployeeDao employeeDao = EmployeeDaoImpl.INSTANCE;
                        Optional<Employee> optionalEmployee = employeeDao.findByUserId(executorUserId);
                        if (optionalEmployee.isPresent()) {
                            Employee employee = optionalEmployee.get();
                            int doctorId = employee.getId();
                            if (record.getTreatment() == Treatment.PROCEDURE) {
                                result = recordDao.setExecutorAndPatientStatus(recordId, doctorId,
                                        patientId, Entity.Status.WAITING_FOR_DECISION);
                            } else {
                                LOGGER.log(Level.DEBUG, "Patient should be made a surgery instead");
                            }
                        }
                    } else {
                        LOGGER.log(Level.DEBUG, "The record already contains an executor id");
                    }
                } else {
                    LOGGER.log(Level.DEBUG, "No record found, cannot execute a procedure");
                }
            } else {
                LOGGER.log(Level.DEBUG, "Record id or executor id are invalid");
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean executeSurgery(int patientId, int executorUserId) throws ServiceException {
        boolean result = false;
        try {
            if (patientId > 0 && executorUserId > 0) {
                Optional<PatientRecord> optional = recordDao.findLast(patientId);
                if (optional.isPresent()) {
                    PatientRecord record = optional.get();
                    int recordId = record.getId();
                    if (record.getExecutorId() == 0) {
                        if (record.getTreatment() == Treatment.SURGERY) {
                            EmployeeDao employeeDao = EmployeeDaoImpl.INSTANCE;
                            Optional<Employee> optionalEmployee = employeeDao.findByUserId(executorUserId);
                            if (optionalEmployee.isPresent()) {
                                Employee employee = optionalEmployee.get();
                                Employee.Position position = employee.getPosition();
                                if (position == Employee.Position.DOCTOR) {
                                    int doctorId = employee.getId();
                                    result = recordDao.setExecutorAndPatientStatus(recordId, doctorId,
                                            patientId, Entity.Status.WAITING_FOR_DECISION);
                                } else {
                                    LOGGER.log(Level.DEBUG, "Employee doesn't have an access to make a surgery");
                                }
                            }
                        } else {
                            LOGGER.log(Level.DEBUG, "Patient should be made a surgery instead");
                        }
                    } else {
                        LOGGER.log(Level.DEBUG, "The record already contains an executor id");
                    }
                } else {
                    LOGGER.log(Level.DEBUG, "No record found, cannot execute a procedure");
                }
            } else {
                LOGGER.log(Level.DEBUG, "Record id or executor id are invalid");
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public int count(int patientId) throws ServiceException {
        int result = 0;
        try {
            if (patientId > 0) {
                result = recordDao.count(patientId);
            } else {
                LOGGER.log(Level.DEBUG, "Patient id value is invalid");
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Optional<PatientRecord> findById(int recordId) throws ServiceException {
        Optional<PatientRecord> result;
        try {
            if (recordId > 0) {
                result = recordDao.findById(recordId);
            } else {
                result = Optional.empty();
                LOGGER.log(Level.DEBUG, "Invalid record id: " + recordId);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Map<String, String> packRecordIntoMap(PatientRecord record) {
        Map<String, String> fields = new HashMap<>();
        if (record != null) {
            int recordId = record.getId();
            String stringRecordId = recordId != 0 ? String.valueOf(recordId) : "";
            fields.put(RequestParameter.RECORD_ID, stringRecordId);
            int patientId = record.getPatientId();
            String stringPatientId = patientId != 0 ? String.valueOf(patientId) : "";
            fields.put(RequestParameter.PATIENT_ID, stringPatientId);
            int attendingDoctorId = record.getDoctorId();
            String stringDoctorId = attendingDoctorId != 0 ? String.valueOf(attendingDoctorId) : "";
            fields.put(RequestParameter.ATTENDING_DOCTOR_ID, stringDoctorId);
            fields.put(RequestParameter.PATIENT_TREATMENT, record.getTreatment().toString());
            int executorId = record.getExecutorId();
            String stringExecutorId = executorId != 0 ? String.valueOf(executorId) : "";
            fields.put(RequestParameter.EXECUTOR_ID, stringExecutorId);
            fields.put(RequestParameter.PATIENT_DIAGNOSIS, record.getDiagnosis());
        }
        return fields;
    }

    private boolean checkSortingTagForSpecifiedRecord(String sortBy) {
        boolean result = false;
        if (sortBy != null) {
            result = sortBy.equals(ColumnName.RECORD_ID)
                    || sortBy.equals(ColumnName.DOCTOR_NAME)
                    || sortBy.equals(ColumnName.EXECUTOR_NAME)
                    || sortBy.equals(ColumnName.TREATMENT_NAME)
                    || sortBy.equals(ColumnName.DIAGNOSIS);
        } else {
            LOGGER.log(Level.DEBUG, "Sorting tag is null");
        }
        return result;
    }
}
