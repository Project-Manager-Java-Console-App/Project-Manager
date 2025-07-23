package org.example.dataBase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseManager implements Database {

    private static DatabaseManager instance;
    private Connection conn;
    private static final Logger logger = LogManager.getLogger(DatabaseManager.class);

    private DatabaseManager() {
        connect();
    }

    private void connect() {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");
        if (url == null || user == null || password == null) {
            logger.fatal("Invalid details in Database URL , User and Password are null!");
            return;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            logger.info("Database connection established!");
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Failed to connect to database", e);
        }

    }

    @Override
    public Connection getConnection() {
        return conn;
    }

    @Override
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                logger.info("Closing database connection!");
            } catch (SQLException e) {
                logger.warn("Failed to close database connection", e);
            }
        }
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        } else {
            try {
                if (instance.conn == null || instance.conn.isClosed() || !instance.conn.isValid(2)) {
                    instance.connect();
                    logger.info("Reconnecting to the database...");
                }
            } catch (SQLException e) {
                logger.error("Error while validating database connection.", e);
            }
        }
        return instance;
    }
}
