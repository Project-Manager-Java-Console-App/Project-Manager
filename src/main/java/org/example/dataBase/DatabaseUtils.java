package org.example.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseUtils implements Database {

    private static DatabaseUtils instance;

    private Connection conn;

    public DatabaseUtils() throws SQLException {
        connect();
    }

    private void connect() throws SQLException {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");
        if (url == null || user == null || password == null) {
            throw new SQLException("Environment variables DB_URL, DB_USER, or DB_PASSWORD not set.");
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC driver not found", e);
        }
    }

    @Override
    public Connection getConnection() {
        return conn;
    }

    @Override
    public void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    public static DatabaseUtils getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseUtils();
        } else if (instance.conn == null || instance.conn.isClosed() || !instance.conn.isValid(2)) {
            instance.connect();
        }
        return instance;
    }
}
