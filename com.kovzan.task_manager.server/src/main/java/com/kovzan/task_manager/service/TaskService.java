package com.kovzan.task_manager.service;

import com.kovzan.task_manager.dao.impl.TaskDao;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.logger.LogConstant;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class TaskService {
	
	private TaskService() {
	}
	
	public static List<Task> findAllTasks() throws SQLException {
		return TaskDao.getInstance().findAll();
	}
	
	public static Task findTaskById(int id) throws SQLException {
		Task task = TaskDao.getInstance().findById(id);
		if (task == null) {
			String paramName = "Id";
			//throw new Exception(String.format(LogConstant.TASK_NOT_FOUND, paramName, id));
			logger.log(Level.WARNING, String.format(LogConstant.TASK_NOT_FOUND, paramName, id));
		}
		return task;
	}
	
	public static List<Task> findTasksByEmployeeId(int employeeId) throws SQLException {
		return TaskDao.getInstance().findTasksByEmployeeId(employeeId);
	}
	
	public static List<Task> findTasksByProjectId(int projectId) throws SQLException {
		return TaskDao.getInstance().findTasksByProjectId(projectId);
	}
	
	public static void addTask(Task task) throws SQLException {
		TaskDao.getInstance().add(task);
	}
	
	public static void updateTask(Task task) throws SQLException {
		TaskDao.getInstance().update(task);
	}
	
	public static void removeTask(Task task) throws SQLException {
		TaskDao.getInstance().remove(task);
	}
}
