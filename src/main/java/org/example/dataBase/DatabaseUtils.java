package org.example.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {

    private static DatabaseUtils instance;
    private final Connection conn;

    private DatabaseUtils() throws SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            throw new ExceptionInInitializerError("JDBC driver not found");
        }

        String url = "jdbc:mysql://localhost:3306/project_manager";
        String password = "P020373p";
        String user = "root";
        conn = DriverManager.getConnection(url, user, password);
    }

    public static synchronized DatabaseUtils getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseUtils();
        }
        return instance;
    }

    public  Connection getConnection()  {
        return conn;
    }
}
