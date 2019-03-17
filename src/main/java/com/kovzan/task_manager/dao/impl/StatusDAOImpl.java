package com.kovzan.task_manager.dao.impl;

import com.kovzan.task_manager.connection.DBConnection;
import com.kovzan.task_manager.entity.Status;
import com.kovzan.task_manager.dao.DaoException;
import com.kovzan.task_manager.logger.LogConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class StatusDAOImpl {

	private static final String SELECT_ALL_STATUSES = "SELECT * FROM TASKSTATUSES";

	private static StatusDAOImpl instance = new StatusDAOImpl();

	public static StatusDAOImpl getInstance() {
		return instance;
	}

	public List<Status> findAll() throws DaoException {
		List<Status> statuses = null;
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(SELECT_ALL_STATUSES);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				statuses = DaoCreator.createStatuses(resultSet);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			}
			else {
				return statuses;
			}
		}
		catch (SQLException e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
		}
		return statuses;
	}
}
