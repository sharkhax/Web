package com.drobot.web.model.dao.impl;

import com.drobot.web.exception.ConnectionPoolException;
import com.drobot.web.exception.DaoException;
import com.drobot.web.model.dao.EmployeeDao;
import com.drobot.web.model.entity.Employee;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.entity.User;
import com.drobot.web.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.drobot.web.model.dao.ColumnName.SEMICOLON;

public class EmployeeDaoImpl implements EmployeeDao {

    private static final Logger LOGGER = LogManager.getLogger(EmployeeDaoImpl.class);
    private static final String EXISTS_STATEMENT =
            "SELECT COUNT(*) as label FROM hospital.employees WHERE employee_id = ?;";
    private static final String ADD_STATEMENT =
            "INSERT INTO hospital.employees(employee_name, employee_surname, employee_age, " +
                    "employee_gender, position, hire_date, user_id_fk) VALUES(?, ?, ?, ?, ?, ?, ?);";
    private static final String SET_STATUS_TO_ARCHIVE_STATEMENT =
            "UPDATE hospital.employees SET employee_status = " + Entity.Status.ARCHIVE.getStatusId()
                    + " WHERE employee_id = ?;";
    private static final String FIND_ALL_STATEMENT =
            "SELECT employee_id, employee_name, employee_surname, employee_age, employee_gender, position, " +
                    "hire_date, dismiss_date, status_name, user_id_fk FROM hospital.employees " +
                    "INNER JOIN hospital.statuses ON employee_status = status_id ORDER BY ";
    private static final String FIND_BY_ID_STATEMENT =
            "SELECT employee_id, employee_name, employee_surname, employee_age, employee_gender, position, " +
                    "hire_date, dismiss_date, status_name, user_id_fk FROM hospital.employees " +
                    "INNER JOIN hospital.statuses ON employee_status = status_id WHERE employee_id = ?;";
    private static final String UPDATE_STATEMENT =
            "UPDATE hospital.employees SET employee_name = ?, employee_surname = ?, employee_age = ?, " +
                    "employee_gender = ?, position = ?, hire_date = ?, dismiss_date = ?, employee_status = ?, " +
                    "user_id_fk = ? WHERE employee_id = ?;";

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
    public Optional<User> findUserAccount(int employeeId) throws DaoException {
        return Optional.empty();
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
    public boolean remove(int employeeId) throws DaoException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            if (exists(employeeId, connection)) {
                statement = connection.prepareStatement(SET_STATUS_TO_ARCHIVE_STATEMENT);
                statement.setInt(1, employeeId);
                statement.execute();
                result = true;
                LOGGER.log(Level.INFO, "Employee " + employeeId + " has been sent to archive");
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
    public List<Employee> findAll(String sortBy) throws DaoException {
        List<Employee> result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            String sql = FIND_ALL_STATEMENT + sortBy + SEMICOLON;
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

    @Override
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
    }

    private boolean exists(int employeeId, Connection connection) throws SQLException {
        boolean result = false;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(EXISTS_STATEMENT);
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("label") == 0;
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
            int userAccountId = employee.getUserAccountId();
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setByte(3, age);
            statement.setString(4, gender);
            statement.setString(5, position);
            statement.setLong(6, hireDateMillis);
            statement.setInt(7, userAccountId);
            LOGGER.log(Level.DEBUG, "Statement has been filled");
        } else {
            LOGGER.log(Level.ERROR, "Statement is null, can't be filled");
        }
    }

    private void fillUpdateStatement(PreparedStatement statement, Employee employee) throws SQLException {
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
            int userAccountId = employee.getUserAccountId();
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setByte(3, age);
            statement.setString(4, gender);
            statement.setString(5, position);
            statement.setLong(6, hireDateMillis);
            statement.setLong(7, dismissDateMillis);
            statement.setByte(8, status);
            statement.setInt(9, userAccountId);
            statement.setInt(10, employeeId);
            LOGGER.log(Level.DEBUG, "Statement has been filled");
        } else {
            LOGGER.log(Level.ERROR, "Statement is null, can't be filled");
        }
    }

    private List<Employee> createEmployeeListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Employee> result = new ArrayList<>();
        while (resultSet.next()) {
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
            Employee employee =
                    new Employee(id, name, surname, age, gender, position,
                            hireDateMillis, dismissDateMillis, status, userId);
            result.add(employee);
        }
        return result;
    }
}
