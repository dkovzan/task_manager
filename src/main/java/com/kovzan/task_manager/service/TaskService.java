package com.kovzan.task_manager.service;

import com.kovzan.task_manager.dao.impl.TaskDAOImpl;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.exception.DAOException;
import com.kovzan.task_manager.logger.LogConstant;
import com.kovzan.task_manager.validator.TaskValidator;

import java.util.List;

public class TaskService {

	public static final TaskService instance = new TaskService();

	private TaskService() {}

	public static TaskService getInstance() {
		return instance;
	}

	public static List<Task> findAllTasks() throws DAOException {
		return TaskDAOImpl.getInstance().findAll();
	}

	public static Task findTaskById(int id) throws DAOException {
		Task task = TaskDAOImpl.getInstance().findById(id);
		if (task == null) {
			String paramName = "Id";
			throw new DAOException(String.format(LogConstant.TASK_NOT_FOUND, paramName, id));
		}
		return task;
	}

	public static void addTask(Task task) throws DAOException {
		TaskDAOImpl.getInstance().add(task);
	}

	public static void updateTask(Task task) throws DAOException {
		TaskDAOImpl.getInstance().update(task);
	}

	public static void removeTask(Task task) throws DAOException {
			TaskDAOImpl.getInstance().remove(task);
	}

	public static boolean validateTask(Task task) {
		return TaskValidator.validateTask(task);
	}

	public static Task getTaskWithValidFields(Task task) {
		return TaskValidator.getTaskWithValidFields(task);
	}
}
