package com.drobot.web.model.service;

import com.drobot.web.exception.ServiceException;
import com.drobot.web.model.creator.Creator;
import com.drobot.web.model.entity.Employee;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeeService {

    boolean add(Map<String, String> fields) throws ServiceException;

    boolean remove(int employeeId) throws ServiceException;

    boolean exists(String name, String surname) throws ServiceException;

    List<Employee> findAll(String sortBy, boolean reverse) throws ServiceException;

    List<Employee> findAll(int start, int length, String sortBy, boolean reverse) throws ServiceException;

    Optional<Employee> findById(int employeeId) throws ServiceException;

    boolean update(int employeeId, Map<String, String> fields) throws ServiceException;

    int count() throws ServiceException;
}
