package com.drobot.web.model.dao;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface CloseableDao {

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
}