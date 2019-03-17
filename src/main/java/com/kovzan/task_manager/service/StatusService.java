package com.kovzan.task_manager.service;

import com.kovzan.task_manager.dao.impl.StatusDAOImpl;
import com.kovzan.task_manager.entity.Status;
import com.kovzan.task_manager.dao.DaoException;

import java.util.List;

public class StatusService {

	public static final StatusService instance = new StatusService();

	private StatusService() {}

	public static StatusService getInstance() {
		return instance;
	}

	public static List<Status> findAllStatuses() throws DaoException {
		return StatusDAOImpl.getInstance().findAll();
	}
}
