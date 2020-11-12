package com.drobot.web.model.dao.impl;

import com.drobot.web.exception.ConnectionPoolException;
import com.drobot.web.exception.DaoException;
import com.drobot.web.model.dao.EmployeeDao;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum EmployeeDaoImpl implements EmployeeDao {

    INSTANCE;

    private final Logger LOGGER = LogManager.getLogger(EmployeeDaoImpl.class);
    private final String EXISTS_STATEMENT =
            "SELECT COUNT(*) as label FROM hospital.employees WHERE employee_id = ?;";
    private final String ADD_STATEMENT =
            "INSERT INTO hospital.employees(employee_name, employee_surname, employee_age, " +
                    "employee_gender, position, hire_date) VALUES(?, ?, ?, ?, ?, ?);";
    private final String SET_STATUS_TO_ARCHIVE_STATEMENT =
            "UPDATE hospital.employees SET employee_status = " + Entity.Status.ARCHIVE.getStatusId()
                    + " WHERE employee_id = ?;";
    private final String FIND_ALL_STATEMENT =
            "SELECT employee_id, employee_name, employee_surname, employee_age, employee_gender, position, " +
                    "hire_date, dismiss_date, status_name, inter_user_id FROM hospital.employees " +
                    "INNER JOIN hospital.statuses ON employee_status = status_id " +
                    "INNER JOIN hospital.user_employee ON employee_id = inter_employee_id ORDER BY ";
    private final StringBuilder FIND_ALL_LIMIT_STATEMENT = new StringBuilder(
            "SELECT employee_id, employee_name, employee_surname, employee_age, employee_gender, position, ")
                    .append("hire_date, dismiss_date, status_name, inter_user_id FROM hospital.employees ")
                    .append("INNER JOIN hospital.statuses ON employee_status = status_id ")
                    .append("INNER JOIN hospital.user_employee ON employee_id = inter_employee_id ORDER BY  LIMIT ?, ?;");
    private final String FIND_BY_ID_STATEMENT =
            "SELECT employee_id, employee_name, employee_surname, employee_age, employee_gender, position, " +
                    "hire_date, dismiss_date, status_name, inter_user_id FROM hospital.employees " +
                    "INNER JOIN hospital.statuses ON employee_status = status_id " +
                    "INNER JOIN hospital.user_employee ON employee_id = inter_employee_id WHERE employee_id = ?;";
    private final String EXISTS_NAME_STATEMENT =
            "SELECT COUNT(*) as label FROM hospital.employees WHERE employee_name = ? AND employee_surname = ?;";
    private final String COUNT_STATEMENT = "SELECT COUNT(*) as label FROM hospital.employees;";
    private final String FIND_STATUS_STATEMENT =
            "SELECT status_name FROM hospital.employees " +
                    "INNER JOIN hospital.statuses ON employee_status = status_id WHERE employee_id = ?;";
    private final String UPDATE_STATUS_STATEMENT =
            "UPDATE hospital.employees SET employee_status = ? WHERE employee_id = ?;";
    private final String SET_DISMISS_DATE_STATEMENT =
            "UPDATE hospital.employees SET dismiss_date = ? WHERE employee_id = ?;";

    @Override
    public boolean exists(String name, String surname) throws DaoException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(EXISTS_NAME_STATEMENT);
            statement.setString(1, name);
            statement.setString(2, surname);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            result = resultSet.getInt("label") != 0;
            String log = result ? "Employee " + name + " " + surname + " exists"
                    : "Employee " + name + " " + surname + " does not exist";
            LOGGER.log(Level.DEBUG, log);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean exists(int employeeId) throws DaoException {
        boolean result;
        Connection connection = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            result = exists(employeeId, connection);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(connection);
        }
        return result;
    }

    @Override
    public List<Employee> findAll(int start, int end, String sortBy, boolean reverse) throws DaoException {
        List<Employee> result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            if (reverse) {
                sortBy = sortBy + SPACE + DESC;
            }
            String sql = new StringBuilder(FIND_ALL_LIMIT_STATEMENT).insert(308, sortBy).toString();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, start);
            statement.setInt(2, end);
            ResultSet resultSet = statement.executeQuery();
            result = createEmployeeListFromResultSet(resultSet);
            LOGGER.log(Level.DEBUG, "Employee list has been got");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public List<Employee> findByName(String name, String surname, String sortBy) throws DaoException {
        return null;
    }

    @Override
    public List<Employee> findByAge(int age, String sortBy) throws DaoException {
        return null;
    }

    @Override
    public List<Employee> findByGender(char gender, String sortBy) throws DaoException {
        return null;
    }

    @Override
    public List<Employee> findByPosition(Employee.Position position, String sortBy) throws DaoException {
        return null;
    }

    @Override
    public List<Employee> findByHireDateInterval(long firstDateMillis, long secondDateMillis) throws DaoException {
        return null;
    }

    @Override
    public List<Employee> findByStatus(Entity.Status status, String sortBy) throws DaoException {
        return null;
    }

    @Override
    public Optional<Employee> findByUserId(int userId) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<Entity.Status> findStatus(int employeeId) throws DaoException {
        Optional<Entity.Status> result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(FIND_STATUS_STATEMENT);
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String stringStatus = resultSet.getString(1);
                Entity.Status status = Entity.Status.valueOf(stringStatus);
                result = Optional.of(status);
                LOGGER.log(Level.DEBUG, "Status has been found");
            } else {
                LOGGER.log(Level.DEBUG, "Employee with id " + employeeId + " hasn't been found");
                result = Optional.empty();
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean updateStatus(int employeeId, Entity.Status newStatus) throws DaoException {
        boolean result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UPDATE_STATUS_STATEMENT);
            statement.setInt(1, newStatus.getStatusId());
            statement.setInt(2, employeeId);
            statement.execute();
            LOGGER.log(Level.DEBUG, "Employee's status has been updated");
            result = true;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean setDismissDate(int employeeId, long dismissDateMillis) throws DaoException {
        boolean result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SET_DISMISS_DATE_STATEMENT);
            statement.setLong(1, dismissDateMillis);
            statement.setInt(2, employeeId);
            statement.execute();
            LOGGER.log(Level.DEBUG, "Dismiss date has been set");
            result = true;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean add(Employee employee) throws DaoException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            if (!exists(employee.getId(), connection)) {
                statement = connection.prepareStatement(ADD_STATEMENT);
                fillAddStatement(statement, employee);
                statement.execute();
                result = true;
                LOGGER.log(Level.DEBUG, "Employee has been add");
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public List<Employee> findAll(String sortBy, boolean reverse) throws DaoException {
        List<Employee> result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            StringBuilder sqlBuilder = new StringBuilder(FIND_ALL_STATEMENT).append(sortBy);
            if (reverse) {
                sqlBuilder.append(SPACE).append(DESC);
            }
            String sql = sqlBuilder.append(SEMICOLON).toString();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            result = createEmployeeListFromResultSet(resultSet);
            LOGGER.log(Level.DEBUG, "Employees list has been created");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public Optional<Employee> findById(int employeeId) throws DaoException {
        Optional<Employee> result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(FIND_BY_ID_STATEMENT);
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();
            List<Employee> employeeList = createEmployeeListFromResultSet(resultSet);
            if (!employeeList.isEmpty()) {
                Employee employee = employeeList.get(0);
                result = Optional.of(employee);
                LOGGER.log(Level.DEBUG, "Employee id " + employeeId + " has been found");
            } else {
                LOGGER.log(Level.DEBUG, "Employee id " + employeeId + " has not been found");
                result = Optional.empty();
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    // FIXME: 10.11.2020
    /*@Override
    public boolean update(Employee employee) throws DaoException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            int employeeId = employee.getId();
            if (exists(employeeId, connection)) {
                statement = connection.prepareStatement(UPDATE_STATEMENT);
                fillUpdateStatement(statement, employee);
                statement.execute();
                result = true;
                LOGGER.log(Level.DEBUG, "Employee " + employeeId + " has been updated");
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }*/

    @Override
    public int count() throws DaoException {
        int result;
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(COUNT_STATEMENT);
            resultSet.next();
            result = resultSet.getInt(1);
            LOGGER.log(Level.DEBUG, "Employees have been counted");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    private boolean exists(int employeeId, Connection connection) throws SQLException {
        boolean result = false;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(EXISTS_STATEMENT);
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("label") != 0;
            }
            String log = result ? "Employee id " + employeeId + " exists"
                    : "Employee id " + employeeId + " does not exist";
            LOGGER.log(Level.DEBUG, log);
        } finally {
            close(statement);
        }
        return result;
    }

    private void fillAddStatement(PreparedStatement statement, Employee employee) throws SQLException {
        if (statement != null) {
            String name = employee.getName();
            String surname = employee.getSurname();
            byte age = (byte) employee.getAge();
            String gender = String.valueOf(employee.getGender());
            String position = employee.getPosition().toString();
            long hireDateMillis = employee.getHireDateMillis();
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setByte(3, age);
            statement.setString(4, gender);
            statement.setString(5, position);
            statement.setLong(6, hireDateMillis);
            LOGGER.log(Level.DEBUG, "Statement has been filled");
        } else {
            LOGGER.log(Level.ERROR, "Statement is null, can't be filled");
        }
    }

    // FIXME: 10.11.2020
    /*private void fillUpdateStatement(PreparedStatement statement, Employee employee) throws SQLException {
        if (statement != null) {
            int employeeId = employee.getId();
            String name = employee.getName();
            String surname = employee.getSurname();
            byte age = (byte) employee.getAge();
            String gender = String.valueOf(employee.getGender());
            String position = employee.getPosition().toString();
            long hireDateMillis = employee.getHireDateMillis();
            long dismissDateMillis = employee.getDismissDateMillis();
            byte status = (byte) employee.getStatus().getStatusId();
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setByte(3, age);
            statement.setString(4, gender);
            statement.setString(5, position);
            statement.setLong(6, hireDateMillis);
            statement.setLong(7, dismissDateMillis);
            statement.setByte(8, status);
            statement.setInt(9, employeeId);
            LOGGER.log(Level.DEBUG, "Statement has been filled");
        } else {
            LOGGER.log(Level.ERROR, "Statement is null, can't be filled");
        }
    }*/

    private List<Employee> createEmployeeListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Employee> result = new ArrayList<>();
        if (resultSet != null && resultSet.next()) {
            do {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String surname = resultSet.getString(3);
                int age = resultSet.getByte(4);
                char gender = resultSet.getString(5).charAt(0);
                String stringPosition = resultSet.getString(6);
                long hireDateMillis = resultSet.getLong(7);
                long dismissDateMillis = resultSet.getLong(8);
                String stringStatus = resultSet.getString(9);
                int userId = resultSet.getInt(10);
                Employee.Position position = Employee.Position.valueOf(stringPosition);
                Entity.Status status = Entity.Status.valueOf(stringStatus);
                Employee employee = new Employee(id, name, surname, age, gender, position,
                        hireDateMillis, dismissDateMillis, status, userId);
                result.add(employee);
            } while (resultSet.next());
            LOGGER.log(Level.DEBUG, result.size() + " employees have been found");
        } else {
            LOGGER.log(Level.WARN, "Result set is null or empty");
        }
        return result;
    }
}
