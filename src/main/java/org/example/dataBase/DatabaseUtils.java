package org.example.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {

    private static final String url = "jdbc:mysql://localhost:3306/project_manager";
    private static final String password = "P020373p";
    private static final String user = "root";
    private static DatabaseUtils instance;
    private static Connection conn;

    private DatabaseUtils() throws SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            throw new ExceptionInInitializerError("JDBC driver not found");
        }

        conn = DriverManager.getConnection(url, user, password);
    }

    public static DatabaseUtils getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseUtils();
        }
        return instance;
    }

    public  Connection getConnection()  {
        return conn;
    }
    public static void closeConnection()throws SQLException  {
        if(conn != null&& conn.isClosed()){
            conn.close();
        }
    }
}
