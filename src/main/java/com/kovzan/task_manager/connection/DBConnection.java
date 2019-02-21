package com.kovzan.task_manager.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.kovzan.task_manager.connection.DBConstants.*;

public class DBConnection {

    public static Connection getDBConnection() {

        Connection dbConnection = null;

        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            dbConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return dbConnection;
    }
}
