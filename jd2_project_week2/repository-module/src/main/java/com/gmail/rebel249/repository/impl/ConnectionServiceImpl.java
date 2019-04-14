package com.gmail.rebel249.repository.impl;

import com.gmail.rebel249.repository.ConnectionService;
import com.gmail.rebel249.repository.config.ConfigurationManager;
import com.gmail.rebel249.repository.exception.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

@Component
public class ConnectionServiceImpl implements ConnectionService {

    private static final Logger logger = LogManager.getLogger(ConnectionServiceImpl.class);

    private final ConfigurationManager configurationManager;

    private ConnectionServiceImpl(ConfigurationManager configurationManager) {
        logger.info("Starting connection");
        try {
            Class.forName(configurationManager.getDriver());
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        this.configurationManager = configurationManager;
    }

    @Override
    public Connection getConnection() {
        Properties property = new Properties();
        property.setProperty("user", configurationManager.getUsername());
        property.setProperty("password", configurationManager.getPassword());
        property.setProperty("useUnicode", "true");
        property.setProperty("characterEncoding", "cp1251");
        Connection connection;
        try {
            connection = DriverManager.getConnection(configurationManager.getUrl(), property);
            return connection;

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException(e.getMessage());
        }
    }

    @PostConstruct
    public void createDatabaseTables() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS document (" +
                "id BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "description VARCHAR(255), " +
                "unique_number VARCHAR(45) NOT NULL UNIQUE)";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                statement.execute(createTableQuery);
                connection.commit();
            } catch (Exception e) {
                logger.error("Connection failed", e);
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error("Connection failed", e);
            throw new DatabaseException("Connection failed");
        }
    }
}
