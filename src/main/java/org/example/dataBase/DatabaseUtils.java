package org.example.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {
    private static final String url = "jdbc:mysql://localhost:3306/project_manager";
    private static final String user = "root";
    private static final String password = "P020373p";

    static {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            throw new ExceptionInInitializerError("JDBC driver not found");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
