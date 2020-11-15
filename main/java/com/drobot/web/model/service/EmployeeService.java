package com.drobot.web.model.service;

import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.model.entity.Entity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeeService {

    boolean exists(String name, String surname) throws ServiceException;

    boolean exists(int employeeId) throws ServiceException;

    List<Employee> findAll(String sortBy, boolean reverse) throws ServiceException;

    List<Employee> findAll(int start, int end, String sortBy, boolean reverse) throws ServiceException;

    Optional<Employee> findById(int employeeId) throws ServiceException;

    boolean update(Map<String, String> newFields, Map<String, String> existingFields,
                   Map<String, String> currentFields) throws ServiceException;

    int count() throws ServiceException;

    Map<String, String> packEmployeeIntoMap(Employee employee);

    Optional<Entity.Status> findStatus(int employeeId) throws ServiceException;

    boolean fireEmployee(int employeeId, int userId) throws ServiceException;

    boolean restoreEmployee(int employeeId) throws ServiceException;

    boolean sendToVacation(int employeeId) throws ServiceException;

    boolean returnFromVacation(int employeeId) throws ServiceException;

    Optional<Employee> findByUserId(int userId) throws ServiceException;
}
