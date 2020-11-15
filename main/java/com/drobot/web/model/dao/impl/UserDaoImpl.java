package com.drobot.web.model.dao.impl;

import com.drobot.web.exception.ConnectionPoolException;
import com.drobot.web.exception.DaoException;
import com.drobot.web.model.entity.Entity;
import com.drobot.web.model.pool.ConnectionPool;
import com.drobot.web.model.dao.UserDao;
import com.drobot.web.model.entity.User;
import com.drobot.web.model.util.Encrypter;
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

public enum UserDaoImpl implements UserDao {

    INSTANCE;

    private final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
    private final String ADD_STATEMENT =
            "INSERT INTO hospital.users(login, email, password, role) VALUES(?, ?, ?, ?);";
    private final String CONTAINS_ID_STATEMENT =
            "SELECT COUNT(*) AS label FROM hospital.users WHERE user_id = ?;";
    private final String CONTAINS_EMAIL_STATEMENT =
            "SELECT COUNT(*) as label FROM hospital.users WHERE email = ?;";
    private final String CONTAINS_LOGIN_STATEMENT =
            "SELECT COUNT(*) as label FROM hospital.users WHERE login = ?;";
    private final String DELETE_STATEMENT =
            "DELETE FROM hospital.users WHERE user_id = ?;";
    private final String FIND_ALL_STATEMENT = new StringBuilder(
            "SELECT user_id, login, email, role, status_name, inter_employee_id FROM hospital.users ")
            .append("INNER JOIN hospital.statuses ON user_status = status_id ")
            .append("INNER JOIN hospital.user_employee ON user_id = inter_user_id ORDER BY ")
            .toString();
    private final String FIND_BY_ID_STATEMENT = new StringBuilder(
            "SELECT user_id, login, email, role, status_name, inter_employee_id FROM hospital.users ")
            .append("INNER JOIN hospital.statuses ON user_status = status_id ")
            .append("INNER JOIN hospital.user_employee ON user_id = inter_user_id WHERE user_id = ?;")
            .toString();
    private final String FIND_BY_LOGIN_STATEMENT = new StringBuilder(
            "SELECT user_id, login, email, role, status_name, inter_employee_id FROM hospital.users ")
            .append("INNER JOIN hospital.statuses ON user_status = status_id ")
            .append("INNER JOIN hospital.user_employee ON user_id = inter_user_id WHERE login = ?;")
            .toString();
    private final String FIND_BY_EMAIL_STATEMENT = new StringBuilder(
            "SELECT user_id, login, email, role, status_name, inter_employee_id FROM hospital.users ")
            .append("INNER JOIN hospital.statuses ON user_status = status_id ")
            .append("INNER JOIN hospital.user_employee ON user_id = inter_user_id WHERE email = ?;")
            .toString();
    private final String FIND_BY_ROLE_STATEMENT = new StringBuilder(
            "SELECT user_id, login, email, role, status_name, inter_employee_id FROM hospital.users ")
            .append("INNER JOIN hospital.statuses ON user_status = status_id ")
            .append("INNER JOIN hospital.user_employee ON user_id = inter_user_id WHERE role = ? ORDER BY ")
            .toString();
    private final String FIND_BY_STATUS_STATEMENT = new StringBuilder(
            "SELECT user_id, login, email, role, status_name, inter_employee_id FROM hospital.users ")
            .append("INNER JOIN hospital.statuses ON user_status = status_id ")
            .append("INNER JOIN hospital.user_employee ON user_id = inter_user_id WHERE user_status = ? ORDER BY ")
            .toString();
    private final String UPDATE_STATEMENT =
            "UPDATE hospital.users SET login = ?, email = ? WHERE user_id = ?;";
    private final String DEFINE_ROLE_STATEMENT =
            "SELECT user_id, password, role, status_name FROM hospital.users " +
                    "INNER JOIN hospital.statuses ON user_status = status_id WHERE login = ?;";
    private final String GET_STATUS_STATEMENT =
            "SELECT status_name FROM hospital.users INNER JOIN hospital.statuses ON user_status = status_id" +
                    " WHERE user_id = ?;";
    private final String UPDATE_PASSWORD_STATEMENT =
            "UPDATE hospital.users SET password = ? WHERE user_id = ?;";
    private final String FIND_ALL_LIMIT_STATEMENT = new StringBuilder(
            "SELECT user_id, login, email, role, status_name, inter_employee_id FROM hospital.users ")
            .append("INNER JOIN hospital.statuses ON user_status = status_id ")
            .append("INNER JOIN hospital.user_employee ON user_id = inter_user_id ORDER BY * LIMIT ?, ?;")
            .toString();
    private final String COUNT_STATEMENT = "SELECT COUNT(*) AS label FROM hospital.users;";
    private final String UPDATE_STATUS_STATEMENT = "UPDATE hospital.users SET user_status = ? WHERE user_id = ?;";
    private final String FIND_STATUS_STATEMENT = "SELECT status_name FROM hospital.users INNER JOIN hospital.statuses " +
            "ON user_status = status_id WHERE user_id = ?;";

    @Override
    public boolean exists(int userId) throws DaoException {
        boolean result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(CONTAINS_ID_STATEMENT);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            result = resultSet.getInt(1) != 0;
            String log = result ? "User with id " + userId + " exists" : "User with id " + userId + " does not exist";
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
    public boolean existsEmail(String email) throws DaoException {
        boolean result = exists(email, CONTAINS_EMAIL_STATEMENT);
        String log = result ? "User with such email exists" : "User with such email does not exist";
        LOGGER.log(Level.DEBUG, log);
        return result;
    }

    @Override
    public boolean existsLogin(String login) throws DaoException {
        boolean result = exists(login, CONTAINS_LOGIN_STATEMENT);
        String log = result ? "User with such login exists" : "User with such login does not exist";
        LOGGER.log(Level.DEBUG, log);
        return result;
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        return findBy(login, FIND_BY_LOGIN_STATEMENT);
    }

    @Override
    public Optional<User> findByEmail(String email) throws DaoException {
        return findBy(email, FIND_BY_EMAIL_STATEMENT);
    }

    @Override
    public List<User> findByRole(User.Role role, String sortBy) throws DaoException {
        return findByAndSort(role.toString(), sortBy, FIND_BY_ROLE_STATEMENT);
    }

    @Override
    public List<User> findByStatus(boolean isActive, String sortBy) throws DaoException {
        List<User> result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            String sql = FIND_BY_STATUS_STATEMENT + sortBy + SEMICOLON;
            statement = connection.prepareStatement(sql);
            byte status = (byte) (isActive ? Entity.Status.ACTIVE.getStatusId()
                            : Entity.Status.BLOCKED.getStatusId());
            statement.setByte(1, status);
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
    public Optional<User> checkPassword(String login, String password) throws DaoException {
        Optional<User> result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            if (existsLogin(login)) {
                connection = ConnectionPool.INSTANCE.getConnection();
                statement = connection.prepareStatement(DEFINE_ROLE_STATEMENT);
                statement.setString(1, login);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                int userId = resultSet.getInt(1);
                String actualEncPassword = resultSet.getString(2);
                if (Encrypter.check(password, actualEncPassword)) {
                    String stringRole = resultSet.getString(3);
                    User.Role role = User.Role.valueOf(stringRole);
                    String stringStatus = resultSet.getString(4);
                    Entity.Status status = Entity.Status.valueOf(stringStatus);
                    User user = new User(userId, role, status);
                    result = Optional.of(user);
                    LOGGER.log(Level.DEBUG, "Password is correct, login info has been created");
                } else {
                    LOGGER.log(Level.DEBUG, "Password is not correct");
                    result = Optional.empty();
                }
            } else {
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
    public boolean add(User entity) {
        LOGGER.log(Level.ERROR, "It is not allowed to use this method to add a new user");
        throw new UnsupportedOperationException("It is not allowed to use this method to add a new user");
    }

    @Override
    public boolean add(User user, String encPassword) throws DaoException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            String login = user.getLogin();
            String email = user.getEmail();
            if (!existsLogin(login) && !existsEmail(email)) {
                connection = ConnectionPool.INSTANCE.getConnection();
                statement = connection.prepareStatement(ADD_STATEMENT);
                fillStatement(user, encPassword, statement);
                statement.execute();
                result = true;
                LOGGER.log(Level.DEBUG, "User has been successfully added");
            } else {
                LOGGER.log(Level.DEBUG, "Cannot add user: such email or login already exist");
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
    public List<User> findAll(String sortBy, boolean reverse) throws DaoException {
        List<User> result;
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
    public List<User> findAll(int start, int end, String sortBy, boolean reverse) throws DaoException {
        List<User> result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            if (reverse) {
                sortBy = sortBy + SPACE + DESC;
            }
            StringBuilder sqlBuilder = new StringBuilder(FIND_ALL_LIMIT_STATEMENT);
            int indexOfAsterisk = sqlBuilder.lastIndexOf(ASTERISK);
            String sql = sqlBuilder.replace(indexOfAsterisk, indexOfAsterisk + 1, sortBy).toString();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, start);
            statement.setInt(2, end);
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
    public Optional<User> findById(int userId) throws DaoException {
        Optional<User> result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(FIND_BY_ID_STATEMENT);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            List<User> userList = createUserListFromResultSet(resultSet);
            if (!userList.isEmpty()) {
                User user = userList.get(0);
                result = Optional.of(user);
            } else {
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

    public boolean update(User user) throws DaoException { // FIXME: 10.11.2020
        /*boolean result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UPDATE_STATEMENT);
            String login = user.getLogin();
            String email = user.getEmail();
            String role = user.getRole().toString();
            Entity.Status status = user.getStatus();
            int statusId = status.getStatusId();
            int userId = user.getId();
            statement.setString(1, login);
            statement.setString(2, email);
            statement.setString(3, role);
            statement.setByte(4, (byte) statusId);
            statement.setInt(5, userId);
            statement.execute();
            result = true;
            LOGGER.log(Level.DEBUG, "User has been updated");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;*/
        return false;
    }

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
            LOGGER.log(Level.DEBUG, "Users have been counted");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public boolean updatePassword(int userId, String newEncPassword) throws DaoException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            if (exists(userId)) {
                connection = ConnectionPool.INSTANCE.getConnection();
                statement = connection.prepareStatement(UPDATE_PASSWORD_STATEMENT);
                statement.setString(1, newEncPassword);
                statement.setInt(2, userId);
                statement.execute();
                result = true;
                LOGGER.log(Level.DEBUG, "Password has been updated");
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
    public boolean updateStatus(int userId, Entity.Status newStatus) throws DaoException {
        boolean result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UPDATE_STATUS_STATEMENT);
            statement.setInt(1, newStatus.getStatusId());
            statement.setInt(2, userId);
            statement.execute();
            LOGGER.log(Level.DEBUG, "User's " + userId + " status has been updated");
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
    public boolean update(int userId, String newLogin, String newEmail)
            throws DaoException {
        boolean result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(UPDATE_STATEMENT);
            statement.setString(1, newLogin);
            statement.setString(2, newEmail);
            statement.setInt(3, userId);
            statement.execute();
            result = true;
            LOGGER.log(Level.DEBUG, "User has been updated");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    @Override
    public Optional<Entity.Status> findStatus(int userId) throws DaoException {
        Optional<Entity.Status> result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(FIND_STATUS_STATEMENT);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Entity.Status status = Entity.Status.valueOf(resultSet.getString(1));
                result = Optional.of(status);
                LOGGER.log(Level.DEBUG, "Status has been found");
            } else {
                LOGGER.log(Level.DEBUG, "User hasn't been found");
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

    private void fillStatement(User user, String encPassword, PreparedStatement statement) throws SQLException {
        if (statement != null) {
            String login = user.getLogin();
            String email = user.getEmail();
            User.Role role = user.getRole();
            statement.setString(1, login);
            statement.setString(2, email);
            statement.setString(3, encPassword);
            statement.setString(4, role.toString());
            LOGGER.log(Level.DEBUG, "Statement has been filled");
        } else {
            LOGGER.log(Level.ERROR, "Cannot fill null statement");
        }
    }

    private boolean exists(String value, String sql) throws DaoException {
        boolean result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, value);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            result = resultSet.getInt(1) != 0;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    private List<User> createUserListFromResultSet(ResultSet resultSet) throws SQLException {
        List<User> result = new ArrayList<>();
        if (resultSet != null && resultSet.next()) {
            do {
                int userId = resultSet.getInt(1);
                String login = resultSet.getString(2);
                String email = resultSet.getString(3);
                String stringRole = resultSet.getString(4);
                String stringStatus = resultSet.getString(5);
                int employeeId = resultSet.getInt(6);
                Entity.Status status = Entity.Status.valueOf(stringStatus);
                User.Role role = User.Role.valueOf(stringRole);
                User user = new User(userId, login, email, role, status, employeeId);
                result.add(user);
            } while (resultSet.next());
            LOGGER.log(Level.DEBUG, result.size() + " users have been found");
        } else {
            LOGGER.log(Level.WARN, "Result set is null or empty");
        }
        return result;
    }

    private Optional<User> findBy(String value, String sql) throws DaoException {
        Optional<User> result = Optional.empty();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, value);
            ResultSet resultSet = statement.executeQuery();
            List<User> userList = createUserListFromResultSet(resultSet);
            if (!userList.isEmpty()) {
                result = Optional.ofNullable(userList.get(0));
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
        return result;
    }

    private List<User> findByAndSort(String value, String sortBy, String sql) throws DaoException {
        List<User> result;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            sql = sql + sortBy + SEMICOLON;
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

    // FIXME: 13.11.2020
    private Optional<Entity.Status> findAndGetStatus(int userId, Connection connection)
            throws SQLException {
        Optional<Entity.Status> result;
        if (connection != null) {
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(GET_STATUS_STATEMENT);
                statement.setInt(1, userId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String stringStatus = resultSet.getString(1);
                    Entity.Status status = Entity.Status.valueOf(stringStatus);
                    result = Optional.of(status);
                    LOGGER.log(Level.DEBUG, "User has been found, status: " + status);
                } else {
                    result = Optional.empty();
                    LOGGER.log(Level.DEBUG, "User hasn't been found");
                }
            } finally {
                close(statement);
            }
        } else {
            LOGGER.log(Level.ERROR, "Connection is null");
            result = Optional.empty();
        }
        return result;
    }
}
