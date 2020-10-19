package com.drobot.web.model.service;

import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.entity.Employee;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeeService extends Creatable<Employee> {

    boolean add(Map<String, String> fields) throws ServiceException;

    boolean remove(int employeeId) throws ServiceException;

    boolean exists(String name, String surname) throws ServiceException;

    List<Employee> findAll(String sortBy) throws ServiceException;

    Optional<Employee> findById(int employeeId) throws ServiceException;

    boolean update(int employeeId, Map<String, String> fields) throws ServiceException;
}
