package com.kovzan.task_manager.service;

import com.kovzan.task_manager.dao.impl.TaskDaoImpl;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.dao.DaoException;
import com.kovzan.task_manager.logger.LogConstant;
import com.kovzan.task_manager.validator.TaskValidator;

import java.util.List;

public class TaskService {

	public static final TaskService instance = new TaskService();

	private TaskService() {}

	public static TaskService getInstance() {
		return instance;
	}

	public static List<Task> findAllTasks() throws DaoException {
		return TaskDaoImpl.getInstance().findAll();
	}

	public static Task findTaskById(int id) throws DaoException {
		Task task = TaskDaoImpl.getInstance().findById(id);
		if (task == null) {
			String paramName = "Id";
			throw new DaoException(String.format(LogConstant.TASK_NOT_FOUND, paramName, id));
		}
		return task;
	}

	public static void addTask(Task task) throws DaoException {
		TaskDaoImpl.getInstance().add(task);
	}

	public static void updateTask(Task task) throws DaoException {
		TaskDaoImpl.getInstance().update(task);
	}

	public static void removeTask(Task task) throws DaoException {
			TaskDaoImpl.getInstance().remove(task);
	}

	public static boolean validateTask(Task task) {
		return TaskValidator.validateTask(task);
	}

	public static Task getTaskWithValidFields(Task task) {
		return TaskValidator.getTaskWithValidFields(task);
	}
}
