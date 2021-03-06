package com.drobot.web.model.dao.impl;

import com.drobot.web.exception.ConnectionPoolException;
import com.drobot.web.exception.DaoException;
import com.drobot.web.model.dao.UserEmployeeDao;
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
import java.sql.Savepoint;
import java.sql.Statement;

/**
 * UserEmployeeDao implementation.
 *
 * @author Vladislav Drobot
 */
public enum UserEmployeeDaoImpl implements UserEmployeeDao {

    /**
     * Represents a singleton pattern realization.
     */
    INSTANCE;

    private final Logger LOGGER = LogManager.getLogger(UserEmployeeDaoImpl.class);
    private final String USER_ADDING_STATEMENT =
            "INSERT INTO hospital.users(login, email, password, role) VALUES(?, ?, ?, ?);";
    private final String EMPLOYEE_ADDING_STATEMENT = "INSERT INTO hospital.employees(employee_name, " +
            "employee_surname, employee_age, employee_gender, position, hire_date) VALUES(?, ?, ?, ?, ?, ?);";
    private final String USER_EMPLOYEE_ADD_STATEMENT =
            "INSERT INTO hospital.user_employee(inter_user_id, inter_employee_id) VALUES(?, ?);";
    private final String USER_EMPLOYEE_DATA_STATEMENT = new StringBuilder(
            "SELECT login, email, role, employee_name, employee_surname, employee_age, employee_gender, ")
            .append("position, hire_date, status_name, employee_id FROM hospital.users ")
            .append("INNER JOIN hospital.user_employee ON user_id = inter_user_id ")
            .append("INNER JOIN hospital.employees ON inter_employee_id = employee_id ")
            .append("INNER JOIN hospital.statuses ON employee_status = status_id WHERE user_id = ?;")
            .toString();
    private final String EMPLOYEE_UPDATING_STATEMENT = new StringBuilder(
            "UPDATE hospital.employees SET employee_name = ?, employee_surname = ?, employee_age = ?, ")
            .append("employee_gender = ?, position = ?, hire_date = ? WHERE employee_id = ?;")
            .toString();
    private final String USER_ROLE_UPDATING_STATEMENT = "UPDATE hospital.users SET role = ? WHERE user_id = ?";
    private final String UPDATE_EMPLOYEE_STATUS_STATEMENT =
            "UPDATE hospital.employees SET employee_status = ? WHERE employee_id = ?;";
    private final String UPDATE_USER_STATUS_STATEMENT =
            "UPDATE hospital.users SET user_status = ? WHERE user_id = ?;";

    @Override
    public boolean register(User user, String password, Employee employee) throws DaoException {
        boolean result;
        Connection connection = null;
        PreparedStatement statement = null;
        Savepoint savepoint = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();
            statement = connection.prepareStatement(USER_ADDING_STATEMENT, Statement.RETURN_GENERATED_KEYS);
            fillUserAddingStatement(statement, user, password);
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            int userId = 0;
            if (resultSet.next()) {
                userId = resultSet.getInt(1);
            }
            statement = connection.prepareStatement(EMPLOYEE_ADDING_STATEMENT, Statement.RETURN_GENERATED_KEYS);
            fillEmployeeAddingStatement(statement, employee);
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            int employeeId = 0;
            if (resultSet.next()) {
                employeeId = resultSet.getInt(1);
            }
            connection.commit();
            statement = connection.prepareStatement(USER_EMPLOYEE_ADD_STATEMENT, Statement.NO_GENERATED_KEYS);
            statement.setInt(1, userId);
            statement.setInt(2, employeeId);
            statement.execute();
            connection.commit();
            result = true;
            LOGGER.log(Level.DEBUG, "Registration complete");
        } catch (SQLException | ConnectionPoolException e) {
            rollback(connection, savepoint);
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean loadUserEmployeeData(int userId, User user, Employee employee) throws DaoException {
        boolean result = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(USER_EMPLOYEE_DATA_STATEMENT)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String login = resultSet.getString(1);
                String email = resultSet.getString(2);
                String stringRole = resultSet.getString(3);
                String employeeName = resultSet.getString(4);
                String employeeSurname = resultSet.getString(5);
                int age = resultSet.getInt(6);
                String gender = resultSet.getString(7);
                String stringPosition = resultSet.getString(8);
                long hireDateMillis = resultSet.getLong(9);
                String stringStatus = resultSet.getString(10);
                int employeeId = resultSet.getInt(11);
                user.setLogin(login);
                user.setEmail(email);
                User.Role role = User.Role.valueOf(stringRole);
                user.setRole(role);
                user.setEmployeeId(employeeId);
                employee.setName(employeeName);
                employee.setSurname(employeeSurname);
                employee.setAge(age);
                employee.setGender(gender.charAt(0));
                Employee.Position position = Employee.Position.valueOf(stringPosition);
                employee.setPosition(position);
                employee.setHireDateMillis(hireDateMillis);
                Entity.Status status = Entity.Status.valueOf(stringStatus);
                employee.setStatus(status);
                employee.setUserId(userId);
                result = true;
                LOGGER.log(Level.DEBUG, "User-employee data has been loaded");
            } else {
                LOGGER.log(Level.DEBUG, "There is no user with id: " + userId);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public boolean updateEmployee(Employee employee, User.Role newUserRole) throws DaoException {
        boolean result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            int userId = employee.getUserId();
            statement = connection.prepareStatement(EMPLOYEE_UPDATING_STATEMENT);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getSurname());
            statement.setInt(3, employee.getAge());
            statement.setString(4, String.valueOf(employee.getGender()));
            statement.setString(5, employee.getPosition().toString());
            statement.setLong(6, employee.getHireDateMillis());
            statement.setInt(7, employee.getId());
            statement.execute();
            LOGGER.log(Level.DEBUG, "Employee has been updated...");
            statement = connection.prepareStatement(USER_ROLE_UPDATING_STATEMENT);
            statement.setString(1, newUserRole.toString());
            statement.setInt(2, userId);
            statement.execute();
            LOGGER.log(Level.DEBUG, "User role has been updated, employee updating complete");
            connection.commit();
            result = true;
        } catch (SQLException | ConnectionPoolException e) {
            rollback(connection);
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean updateStatuses(int employeeId, int userId, Entity.Status newEmployeeStatus,
                                  Entity.Status newUserStatus) throws DaoException {
        boolean result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_EMPLOYEE_STATUS_STATEMENT);
            statement.setInt(1, newEmployeeStatus.getStatusId());
            statement.setInt(2, employeeId);
            statement.execute();
            LOGGER.log(Level.DEBUG, "Employee's status has been updated");
            statement = connection.prepareStatement(UPDATE_USER_STATUS_STATEMENT);
            statement.setInt(1, newUserStatus.getStatusId());
            statement.setInt(2, userId);
            statement.execute();
            connection.commit();
            LOGGER.log(Level.DEBUG, "User's status has been updated, updating statuses complete");
            result = true;
        } catch (SQLException | ConnectionPoolException e) {
            rollback(connection);
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    private void fillUserAddingStatement(PreparedStatement statement, User user, String password)
            throws SQLException {
        String login = user.getLogin();
        String email = user.getEmail();
        String role = user.getRole().toString();
        statement.setString(1, login);
        statement.setString(2, email);
        statement.setString(3, password);
        statement.setString(4, role);
    }

    private void fillEmployeeAddingStatement(PreparedStatement statement, Employee employee)
            throws SQLException {
        String name = employee.getName();
        String surname = employee.getSurname();
        int age = employee.getAge();
        char gender = employee.getGender();
        String position = employee.getPosition().toString();
        long hireDateMillis = employee.getHireDateMillis();
        statement.setString(1, name);
        statement.setString(2, surname);
        statement.setInt(3, age);
        statement.setString(4, String.valueOf(gender));
        statement.setString(5, position);
        statement.setLong(6, hireDateMillis);
    }
}
