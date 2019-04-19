package com.kovzan.task_manager.service;

import com.kovzan.task_manager.command.impl.task.TaskValidator;
import com.kovzan.task_manager.dao.impl.TaskDaoImpl;
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
		return TaskDaoImpl.getInstance().findAll();
	}
	
	public static Task findTaskById(int id) throws SQLException {
		Task task = TaskDaoImpl.getInstance().findById(id);
		if (task == null) {
			String paramName = "Id";
			//throw new Exception(String.format(LogConstant.TASK_NOT_FOUND, paramName, id));
			logger.log(Level.WARNING, String.format(LogConstant.TASK_NOT_FOUND, paramName, id));
		}
		return task;
	}
	
	public static void addTask(Task task) throws SQLException {
		TaskDaoImpl.getInstance().add(task);
	}
	
	public static void updateTask(Task task) throws SQLException {
		TaskDaoImpl.getInstance().update(task);
	}
	
	public static void removeTask(Task task) throws SQLException {
		TaskDaoImpl.getInstance().remove(task);
	}
}
