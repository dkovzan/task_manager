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
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class TaskDaoImpl implements DaoBase<Task> {

	private static final String ADD_TASK =
			"INSERT INTO TASKS " +
			"(NAME, ESTIMATE, CREATEDON, FINISHEDON, PROJECTID, EMPLOYEEID, STATUS) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_TASK =
			"UPDATE TASKS " +
			"SET NAME = ?, ESTIMATE = ?, CREATEDON = ?, FINISHEDON = ?, PROJECTID = ?, EMPLOYEEID = ?, STATUS = ? " +
			"WHERE ID = ?";
	private static final String REMOVE_TASK =
			"DELETE FROM TASKS " +
			"WHERE ID = ?";
	private static final String SELECT_ALL_TASKS_WITH_REFS =
			"SELECT T.ID, T.NAME, T.CREATEDON, T.ESTIMATE, P.SHORTNAME, T.STATUS, T.FINISHEDON, CONCAT(E.FIRSTNAME, ' ', E.LASTNAME) AS FULLNAME FROM TASKS T " +
			"LEFT JOIN PROJECTS P ON P.ID = T.PROJECTID " +
			"LEFT JOIN EMPLOYEES E ON E.ID = T.EMPLOYEEID ORDER BY T.ID";
	private static final String SELECT_TASK_BY_ID =
			"SELECT ID, NAME, CREATEDON, ESTIMATE, PROJECTID, STATUS, FINISHEDON, EMPLOYEEID " +
			"FROM TASKS WHERE ID = ?";

	private static TaskDaoImpl instance = new TaskDaoImpl();

	public static TaskDaoImpl getInstance() {
		return instance;
	}

	@Override
	public int add(Task task) throws DaoException {
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(ADD_TASK);
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
			PreparedStatement statement = connection.prepareStatement(UPDATE_TASK);
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
			PreparedStatement statement = connection.prepareStatement(REMOVE_TASK);
			statement.setInt(1, task.getId());
			statement.execute();
			logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);

		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public List<Task> findAll() throws DaoException {
		List<Task> tasks = null;
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(SELECT_ALL_TASKS_WITH_REFS);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				tasks = DaoCreator.createTasksWithRefs(resultSet);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			}
			else {
				return tasks;
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
			PreparedStatement statement = connection.prepareStatement(SELECT_TASK_BY_ID);
			statement.setInt(1, taskId);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				task = DaoCreator.createTasks(resultSet).get(0);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			} else {
				return task;
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return task;
	}
}
