package com.kovzan.task_manager.dao.impl;

import com.kovzan.task_manager.connection.DBConnection;
import com.kovzan.task_manager.dao.DaoBase;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.logger.LogConstant;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class TaskDao implements DaoBase<Task> {

	private static final String addTask =
			"INSERT INTO task " +
			"(name, work, begindate, enddate, projectid, employeeid, status) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String updateTask =
			"UPDATE task " +
			"SET name = ?, work = ?, begindate = ?, enddate = ?, projectid = ?, employeeid = ?, status = ? " +
				"WHERE id = ?";
	private static final String removeTask =
			"DELETE FROM task " +
				"WHERE id = ?";
	private static final String selectAllTasksWithRefs =
			"SELECT t.id, t.name, t.begindate, t.work, p.shortname, t.status, t.enddate, CONCAT(e.firstname, ' ', e.lastname) AS fullname " +
				"FROM task t " +
				"LEFT JOIN project p ON p.id = t.projectid " +
				"LEFT JOIN employee e ON e.id = t.employeeid ORDER BY t.id";
	private static final String selectTaskById =
			"SELECT id, name, begindate, work, projectid, status, enddate, employeeid " +
			"FROM task " +
				"WHERE id = ?";
	private static final String selectTasksByEmployeeId =
			"SELECT t.id, t.name, t.begindate, t.work, p.shortname, t.status, t.enddate, CONCAT(e.firstname, ' ', e.lastname) AS fullname FROM task t " +
				"LEFT JOIN project p ON p.id = t.projectid " +
				"LEFT JOIN employee e ON e.id = t.employeeid " +
					"WHERE t.employeeid = ? ORDER BY t.id";
	private static final String selectTasksByProjectId =
			"SELECT t.id, t.name, t.begindate, t.work, p.shortname, t.status, t.enddate, CONCAT(e.firstname, ' ', e.lastname) AS fullname FROM task t " +
				"LEFT JOIN project p ON p.id = t.projectid " +
				"LEFT JOIN employee e ON e.id = t.employeeid " +
					"WHERE t.projectid = ? ORDER BY t.id";

	private static TaskDao instance = new TaskDao();

	public static TaskDao getInstance() {
		return instance;
	}

	@Override
	public int add(Task task) throws SQLException {
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(addTask);
			statement.setString(1, task.getName());
			statement.setInt(2, task.getWork());
			statement.setDate(3, Date.valueOf(task.getBeginDate()));
			statement.setDate(4, Date.valueOf(task.getEndDate()));
			statement.setInt(5, task.getProjectId());
			statement.setInt(6, task.getEmployeeId());
			statement.setString(7, task.getStatus().toString());
			statement.executeUpdate();
			ResultSet keys = statement.getGeneratedKeys();
			int result;
			if (keys.next()) {
				result = keys.getInt(1);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
				return result;
			}
		}
		return -1;
	}

	@Override
	public int update(Task task) throws SQLException {
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(updateTask);
			statement.setString(1, task.getName());
			statement.setInt(2, task.getWork());
			statement.setDate(3, Date.valueOf(task.getBeginDate()));
			statement.setDate(4, Date.valueOf(task.getEndDate()));
			statement.setInt(5, task.getProjectId());
			statement.setInt(6, task.getEmployeeId());
			statement.setString(7, task.getStatus().toString());
			statement.setInt(8, task.getId());
			statement.executeUpdate();
			int result;
			ResultSet keys = statement.getGeneratedKeys();
			if (keys.next()) {
				result = keys.getInt(1);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
				return result;
			}
		}
		return -1;
	}

	@Override
	public void remove(Task task) throws SQLException {
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(removeTask);
			statement.setInt(1, task.getId());
			statement.execute();
			logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		}
	}

	@Override
	public List<Task> findAll() throws SQLException {
		List<Task> tasks = new ArrayList<>();
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(selectAllTasksWithRefs);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				tasks = DaoCreator.createTasksWithRefs(resultSet);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			}
		}
		return tasks;
	}

	@Override
	public Task findById(int taskId) throws SQLException {
		Task task = null;
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(selectTaskById);
			statement.setInt(1, taskId);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				task = DaoCreator.createTasks(resultSet).get(0);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			}
		}
		return task;
	}

	public List<Task> findTasksByEmployeeId(int employeeId) throws SQLException {
		return findTasksBy(employeeId, selectTasksByEmployeeId);
	}
	
	public List<Task> findTasksByProjectId (int projectId) throws SQLException {
		return findTasksBy(projectId, selectTasksByProjectId);
	}

	public List<Task> findTasksBy(Integer id, String selectStatement) throws SQLException {
		List<Task> tasks = new ArrayList<>();
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(selectStatement);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				tasks = DaoCreator.createTasksWithRefs(resultSet);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			}
		}
		return tasks;
	}
}
