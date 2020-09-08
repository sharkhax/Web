package com.drobot.web.model.dao.impl;

import com.drobot.web.exception.ConnectionPoolException;
import com.drobot.web.model.connection.ConnectionPool;
import com.drobot.web.model.dao.ColumnName;
import com.drobot.web.model.dao.UserDao;
import com.drobot.web.model.entity.User;
import com.drobot.web.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
    private static final String ADD_STATEMENT =
            "INSERT INTO hospital.user_table(login, email, password, role, status) VALUES(?, ?, ?, ?, ?, ?);";
    private static final String REMOVE_STATEMENT = "DELETE FROM hospital.user_table WHERE userId = ?;";
    private static final String CONTAINS_STATEMENT = "SELECT userId FROM hospital.user_table " +
            "WHERE(login = ? AND email = ? AND password = ? AND role = ? AND status = ?);";
    private static final StringBuilder FIND_ALL_STATEMENT = new StringBuilder(
            "SELECT userId, login, email, password, role, status FROM hospital.user_table").append(
            "WHERE (login = ? AND email ? AND password = ? AND role = ? AND status = ?) ORDER BY ");
    private static final String FIND_BY_ID_STATEMENT =
            "SELECT userId, login, email, password, role, status FROM hospital.user_table WHERE userId = ?";
    private static final String CONTAINS_EMAIL_STATEMENT =
            "SELECT userId FROM hospital.user_table WHERE (email = ?)";
    private static final String CONTAINS_LOGIN_STATEMENT =
            "SELECT userId FROM hospital.user_table WHERE (login = ?)";
    private static final String FIND_BY_STATEMENT_PART1 =
            "SELECT userId, login, email, password, role, status FROM hospital.user_table WHERE ";
    private static final String FIND_BY_STATEMENT_PART2 = " = ? ORDER BY ";
    private static final String UPDATE_STATEMENT =
            "UPDATE hospital.user_table SET login = ?, email = ?, password = ?, role = ?, status = ? WHERE userId = ?;";
    private static final String SEMICOLON = ";";

    @Override
    public boolean add(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result = false;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            if (!userContains(user, connection)) {
                statement = connection.prepareStatement(ADD_STATEMENT);
                fillStatement(user, statement);
                LOGGER.log(Level.INFO, "User has been add");
                result = true;
            } else {
                LOGGER.log(Level.DEBUG, "User already exists");
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
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result = false;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            Optional<User> optional = findById(id, connection);
            if (optional.isPresent()) {
                statement = connection.prepareStatement(REMOVE_STATEMENT);
                statement.setInt(1, id);
                statement.execute();
                result = true;
                LOGGER.log(Level.INFO, "User id " + id + " was removed");
            } else {
                LOGGER.log(Level.DEBUG, "No user with such id to be removed: " + id);
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
    public List<User> findAll(String sortBy) throws DaoException {
        Connection connection = null;
        Statement statement = null;
        List<User> result;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            String sql = FIND_ALL_STATEMENT.append(sortBy).append(";").toString();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            result = createUserListFromResultSet(resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public Optional<User> findById(int id) throws DaoException {
        Connection connection = null;
        Optional<User> result;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            result = findById(id, connection);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(connection);
        }
        return result;
    }

    @Override
    public List<User> findBy(String value, String column, String sortBy) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        List<User> result;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            String sql = FIND_BY_STATEMENT_PART1 + column + FIND_BY_STATEMENT_PART2 + sortBy + ";";
            statement = connection.prepareStatement(sql);
            statement.setString(1, value);
            ResultSet resultSet = statement.executeQuery();
            result = createUserListFromResultSet(resultSet);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public List<User> findBy(String value, String column) throws DaoException {
        return findBy(value, column, ColumnName.USER_ID);
    }

    @Override
    public boolean update(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result = false;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            int userId = user.getId();
            statement = connection.prepareStatement(UPDATE_STATEMENT);
            fillStatement(user, statement);
            statement.setInt(6, userId);
            statement.execute();
            result = true;
            LOGGER.log(Level.INFO, "User id " + userId + " was updated");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean exists(User user) throws DaoException {
        Connection connection = null;
        boolean result;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            result = userContains(user, connection);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(connection);
        }
        return result;
    }

    @Override
    public boolean existsEmail(String email) throws DaoException {
        return userContains(email, CONTAINS_EMAIL_STATEMENT);
    }

    @Override
    public boolean existsLogin(String login) throws DaoException {
        return userContains(login, CONTAINS_LOGIN_STATEMENT);
    }

    private void fillStatement(User user, PreparedStatement statement) throws DaoException {
        String login = user.getLogin();
        String email = user.getEmail();
        String password = user.getEncPassword();
        User.Role role = user.getRole();
        int status = user.getStatus();
        try {
            statement.setString(1, login);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, role.toString());
            statement.setString(5, String.valueOf(status));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private boolean userContains(User user, Connection connection) throws DaoException {
        PreparedStatement statement = null;
        boolean result;
        try {
            statement = connection.prepareStatement(CONTAINS_STATEMENT);
            fillStatement(user, statement);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            result = resultSet.getRow() != 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        if (result) {
            LOGGER.log(Level.DEBUG, "User is contained");
        } else {
            LOGGER.log(Level.DEBUG, "User is not contained");
        }
        return result;
    }

    private boolean userContains(String value, String sql) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean result;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, value);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            result = resultSet.getRow() != 0;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        if (result) {
            LOGGER.log(Level.DEBUG, "User is contained");
        } else {
            LOGGER.log(Level.DEBUG, "User is not contained");
        }
        return result;
    }

    private List<User> createUserListFromResultSet(ResultSet resultSet) throws DaoException {
        List<User> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int userId = resultSet.getInt(1);
                String login = resultSet.getString(2);
                String email = resultSet.getString(3);
                String password = resultSet.getString(4);
                User.Role role = User.Role.valueOf(resultSet.getString(5));
                int status = resultSet.getInt(6);
                User user = new User(userId, login, email, password, role, status);
                result.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    private Optional<User> findById(int id, Connection connection) throws DaoException {
        PreparedStatement statement = null;
        Optional<User> result = Optional.empty();
        try {
            statement = connection.prepareStatement(FIND_BY_ID_STATEMENT);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<User> userList = createUserListFromResultSet(resultSet);
            if (!userList.isEmpty()) {
                User user = userList.get(0);
                result = Optional.of(user);
                LOGGER.log(Level.DEBUG, "User id: " + id + " has been found");
            } else {
                LOGGER.log(Level.DEBUG, "User id: " + id + " hasn't been found");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return result;
    }
}
