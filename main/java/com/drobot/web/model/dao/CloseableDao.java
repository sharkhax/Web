package com.drobot.web.model.dao;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

/**
 * Interface used for common Dao operations, f.e. closing Connection.
 *
 * @author Vladislav Drobot
 */
public interface CloseableDao {

    /**
     * Wrapper method of Statement's close() with catching an exception and a logger inside.
     *
     * @param statement Statement object to be closed.
     */
    default void close(Statement statement) {
        final Logger logger = LogManager.getLogger(this.getClass());
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Statement hasn't been closed");
            }
        }
    }

    /**
     * Wrapper method of Connection's close() with catching an exception and a logger inside.
     *
     * @param connection Connection object to be closed.
     */
    default void close(Connection connection) {
        final Logger logger = LogManager.getLogger(this.getClass());
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Statement hasn't been closed");
            }
        }
    }

    /**
     * Wrapper method of Connection's rollback(Savepoint) with catching an exception and a logger inside.
     *
     * @param connection Connection object which contains operations to be rollbacked.
     * @param savepoint  Savepoint object.
     */
    default void rollback(Connection connection, Savepoint savepoint) {
        final Logger logger = LogManager.getLogger(this.getClass());
        if (connection != null && savepoint != null) {
            try {
                connection.rollback(savepoint);
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Rollback failed");
            }
        }
    }

    /**
     * Wrapper method of Connection's rollback() with catching an exception and a logger inside.
     *
     * @param connection Connection object which contains operations to be rollbacked.
     */
    default void rollback(Connection connection) {
        final Logger logger = LogManager.getLogger(this.getClass());
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Rollback failed");
            }
        }
    }
}
