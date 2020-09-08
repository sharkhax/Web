package com.drobot.web.model.connection;

import com.drobot.web.exception.ConnectionPoolException;
import com.drobot.web.exception.ConnectionPoolRuntimeException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public enum ConnectionPool {
    INSTANCE;

    private final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private BlockingQueue<ProxyConnection> freeConnections;
    private Queue<ProxyConnection> givenConnections;
    private static final int DEFAULT_POOL_SIZE = 8;

    ConnectionPool() {
        try {
            Class.forName(DRIVER);
            freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
            givenConnections = new ArrayDeque<>(DEFAULT_POOL_SIZE);
            for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.offer(proxyConnection);
            }
            LOGGER.log(Level.INFO, "Connection pool has been filled");
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.FATAL, "Error during connection pool creating");
            throw new ConnectionPoolRuntimeException(e);
        }
    }

    public Connection getConnection() throws ConnectionPoolException {
        ProxyConnection connection;
        try {
            connection = freeConnections.take();
            givenConnections.offer(connection);
            LOGGER.log(Level.DEBUG, "Connection has been given");
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, e);
            throw new ConnectionPoolException(e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection
                && givenConnections.remove(connection)) {
            freeConnections.offer((ProxyConnection) connection);
            LOGGER.log(Level.DEBUG, "Connection has been released");
        } else {
            LOGGER.log(Level.WARN, "Invalid connection to release");
        }
    }

    public void destroyPool() throws ConnectionPoolException {
        try {
            for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                ProxyConnection proxyConnection = freeConnections.take();
                proxyConnection.reallyClose();
            }
            LOGGER.log(Level.INFO, "Connection pool has been destroyed");
            deregisterDrivers();
        } catch (InterruptedException | SQLException e) {
            LOGGER.log(Level.ERROR, e);
            throw new ConnectionPoolException(e);
        }
    }

    private void deregisterDrivers() throws ConnectionPoolException {
        try {
            while (DriverManager.getDrivers().hasMoreElements()) {
                Driver driver = DriverManager.getDrivers().nextElement();
                DriverManager.deregisterDriver(driver);
            }
            LOGGER.log(Level.INFO, "Drivers have been deregistered");
        } catch (SQLException e) {
            throw new ConnectionPoolException(e);
        }
    }
}
