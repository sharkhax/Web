package com.drobot.web.model.service;

import com.drobot.web.exception.ServiceException;

import java.util.Map;

public interface UserEmployeeService {

    boolean register(Map<String, String> fields, Map<String, String> existingFields) throws ServiceException;

    Map<String, String> loadUserData(int userId) throws ServiceException;
}
