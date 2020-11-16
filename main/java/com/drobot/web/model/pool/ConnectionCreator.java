package com.drobot.web.model.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class used to create Connection objects.
 *
 * @author Vladislav Drobot
 */
public class ConnectionCreator {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionCreator.class);
    private static final Properties PROPERTIES = new Properties();
    private static final String PROPERTIES_PATH = "E:\\Epam Training\\Web\\src\\main\\resources\\config.properties";
    private static final String DRIVER_PROPERTY = "db.driver";
    private static final String URL_PROPERTY = "db.url";

    static {
        try {
            PROPERTIES.load(new FileReader(PROPERTIES_PATH));
            String driver = PROPERTIES.getProperty(DRIVER_PROPERTY);
            Class.forName(driver);
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.FATAL, "Error during connection creation", e);
        }
    }

    private ConnectionCreator() {
    }

    /**
     * Creates a Connection object.
     *
     * @return Connection object.
     * @throws SQLException if a database access error occurs or the url is null.
     */
    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(PROPERTIES.getProperty(URL_PROPERTY), PROPERTIES);
    }
}
