package com.drobot.web.model.dao;

import com.drobot.web.model.entity.Entity;
import com.drobot.web.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public interface Dao<E extends Entity> {
    boolean add(E entity) throws DaoException;

    boolean remove(int id) throws DaoException;

    List<E> findAll(String sortBy) throws DaoException;

    Optional<E> findById(int id) throws DaoException;

    boolean update(E entity) throws DaoException;

    default void close(Statement statement) {
        final Logger logger = LogManager.getLogger(Dao.class);
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Statement hasn't been closed");
            }
        }
    }

    default void close(Connection connection) {
        final Logger logger = LogManager.getLogger(Dao.class);
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Statement hasn't been closed");
            }
        }
    }
}
