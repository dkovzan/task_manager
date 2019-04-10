package com.kovzan.task_manager.dao.impl;

import com.kovzan.task_manager.connection.DBConnection;
import com.kovzan.task_manager.dao.DaoBase;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.dao.DaoException;
import com.kovzan.task_manager.logger.LogConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class TaskDaoImpl implements DaoBase<Task> {

	private static final String addTask =
			"INSERT INTO tasks " +
			"(name, estimate, createdon, finishedon, projectid, employeeid, status) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String updateTask =
			"UPDATE tasks " +
			"SET name = ?, estimate = ?, createdon = ?, finishedon = ?, projectid = ?, employeeid = ?, status = ? " +
			"WHERE id = ?";
	private static final String removeTask =
			"DELETE FROM tasks " +
			"WHERE id = ?";
	private static final String selectAllTasksWithRefs =
			"SELECT t.id, t.name, t.createdon, t.estimate, p.shortname, t.status, t.finishedon, CONCAT(e.firstname, ' ', e.lastname) AS fullname FROM tasks t " +
			"LEFT JOIN projects p ON p.id = t.projectid " +
			"LEFT JOIN employees e ON e.id = t.employeeid ORDER BY t.id";
	private static final String selectTaskById =
			"SELECT id, name, createdon, estimate, projectid, status, finishedon, employeeid " +
			"FROM tasks WHERE id = ?";
	private static final String selectTasksByEmployeeId =
			"SELECT t.id, t.name, t.createdon, t.estimate, p.shortname, t.status, t.finishedon, CONCAT(e.firstname, ' ', e.lastname) AS fullname FROM tasks t " +
			"LEFT JOIN projects p ON p.id = t.projectid " +
			"LEFT JOIN employees e ON e.id = t.employeeid " +
			"WHERE t.employeeid = ? ORDER BY t.id";

	private static TaskDaoImpl instance = new TaskDaoImpl();

	public static TaskDaoImpl getInstance() {
		return instance;
	}

	@Override
	public int add(Task task) throws DaoException {
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(addTask);
			statement.setString(1, task.getName());
			statement.setInt(2, task.getEstimate());
			statement.setString(3, task.getCreatedOn().toString());
			statement.setString(4, task.getFinishedOn().toString());
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
			} catch (SQLException e) {
				throw new DaoException(e);
			}
		return -1;
	}

	@Override
	public int update(Task task) throws DaoException {
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(updateTask);
			statement.setString(1, task.getName());
			statement.setInt(2, task.getEstimate());
			statement.setString(3, task.getCreatedOn().toString());
			statement.setString(4, task.getFinishedOn().toString());
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
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return -1;
	}

	@Override
	public void remove(Task task) throws DaoException {
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(removeTask);
			statement.setInt(1, task.getId());
			statement.execute();
			logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public List<Task> findAll() throws DaoException {
		List<Task> tasks = new ArrayList<>();
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(selectAllTasksWithRefs);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				tasks = DaoCreator.createTasksWithRefs(resultSet);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return tasks;
	}

	@Override
	public Task findById(int taskId) throws DaoException {
		Task task = null;
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(selectTaskById);
			statement.setInt(1, taskId);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				task = DaoCreator.createTasks(resultSet).get(0);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return task;
	}

	public List<Task> findTasksByEmployeeId(int employeeId) throws DaoException {
		return findTasksBy(employeeId, selectTasksByEmployeeId);
	}

	public List<Task> findTasksBy(Integer id, String selectStatement) throws DaoException {
		List<Task> tasks = new ArrayList<>();
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(selectStatement);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				tasks = DaoCreator.createTasksWithRefs(resultSet);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return tasks;
	}
}
