package com.kovzan.task_manager.service;

import com.kovzan.task_manager.dao.impl.StatusDAOImpl;
import com.kovzan.task_manager.entities.Status;
import com.kovzan.task_manager.logger.LogConstant;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class StatusService {

	public static final StatusService instance = new StatusService();

	private StatusService() {}

	public static StatusService getInstance() {
		return instance;
	}

	public static List<Status> findAllStatuses() throws SQLException {
		List<Status> statuses = null;
		try {
			statuses = StatusDAOImpl.getInstance().findAll();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
		}
		return statuses;
	}

	public static Status findStatusById(int id) throws SQLException {
		Status status = null;
		try {
			status = StatusDAOImpl.getInstance().findStatusById(id);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
		}
		return status;
	}
}
