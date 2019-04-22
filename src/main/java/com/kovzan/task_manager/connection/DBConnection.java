package com.kovzan.task_manager.connection;

import com.kovzan.task_manager.logger.LogConstant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.kovzan.task_manager.connection.DBConstants.*;
import static com.kovzan.task_manager.logger.Log.logger;

public class DBConnection {

	public static Connection getDBConnection() {

		Connection dbConnection = null;

		try {
			Class.forName(DB_DRIVER);
			logger.log(Level.INFO, LogConstant.DRIVER_INSTALLED_SUCCESSFULLY);
		}
		catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, LogConstant.DRIVER_INSTALLATION_FAILED);
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
		}

		try {
			dbConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			return dbConnection;
		}
		catch (SQLException e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
		}
		return dbConnection;
	}
}
