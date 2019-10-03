package com.kovzan.task_manager.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionService {
	
	private static final Logger logger = Logger.getLogger(ConnectionService.class.getName());
	
	private static final String DB_DRIVER = "org.hsqldb.jdbcDriver";
	private static final String DB_URL = "jdbc:hsqldb:hsql://localhost:9001/database";
	private static final String DB_USER = "sa";
	private static final String DB_PASS = "";
	
	private static final String DRIVER_INSTALLED_SUCCESSFULLY = "Driver installed successfully";
	private static final String DRIVER_INSTALLATION_FAILED = "Driver installation failed";
	private static final String CONNECTION_INITIALIZATION_FAILED = "Connection initialization failed";
	
	private static ConnectionService instance;
	
	public static ConnectionService getInstance() {
		if (instance == null) {
			synchronized (ConnectionService.class) {
				if (instance == null) {
					instance = new ConnectionService();
				}
			}
		}
		return instance;
	}
	
	static {
		try {
			Class.forName(DB_DRIVER);
			logger.info(DRIVER_INSTALLED_SUCCESSFULLY);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, DRIVER_INSTALLATION_FAILED, e);
		}
	}
	
	private ConnectionService() {
		this.getConnection();
	}
	
	public Connection getConnection() {
		Connection dbConnection = null;
		try {
			dbConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			return dbConnection;
		}
		catch (SQLException e) {
			logger.log(Level.SEVERE, CONNECTION_INITIALIZATION_FAILED, e);
		}
		return dbConnection;
	}
}
