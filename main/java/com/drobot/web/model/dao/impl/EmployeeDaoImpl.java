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
import java.util.List;
import java.util.Optional;

public class EmployeeDaoImpl implements EmployeeDao {

    private static final Logger LOGGER = LogManager.getLogger(EmployeeDaoImpl.class);
    private static final String EXISTS_STATEMENT =
            "SELECT COUNT(*) as label FROM hospital.employees WHERE employee_id = ?;";
    private static final String ADD_STATEMENT =
            "INSERT INTO hospital.employees(employee_name, employee_surname, employee_age, " +
                    "employee_gender, position, hire_date, user_id_fk) VALUES(?, ?, ?, ?, ?, ?, ?);";

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
            if (exists(employee.getId(), connection)) {
                statement = connection.prepareStatement(ADD_STATEMENT);
                String name = employee.getName();
                String surname = employee.getSurname();
                byte age = employee.getAge();
                char gender = employee.getGender();
                String position = employee.getPosition().toString();
                long hireDateMillis = employee.getHireDateMillis();
                int userAccountId = employee.getUserAccountId();
                fillStatement(statement, name, surname, age, gender, position, hireDateMillis, userAccountId);
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
    public boolean remove(int id) throws DaoException {
        return false;
    }

    @Override
    public List<Employee> findAll(String sortBy) throws DaoException {
        return null;
    }

    @Override
    public Optional<Employee> findById(int id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean update(Employee entity) throws DaoException {
        return false;
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
            String log = result ? "Employee with such id exists" : "Employee with such id does not exist";
            LOGGER.log(Level.DEBUG, log);
        } finally {
            close(statement);
        }
        return result;
    }

    private void fillStatement(PreparedStatement statement, String name,
                               String surname, int age, char gender,
                               String position, long hireDateMillis,
                               int userAccountId) throws SQLException {
        if (statement != null) {
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setInt(3, age);
            statement.setString(4, String.valueOf(gender));
            statement.setString(5, position);
            statement.setLong(6, hireDateMillis);
            statement.setInt(7, userAccountId);
            LOGGER.log(Level.DEBUG, "Statement has been filled");
        } else {
            LOGGER.log(Level.ERROR, "Statement is null, can't be filled");
        }
    }
}
