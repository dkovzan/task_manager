package com.kovzan.task_manager.dao.impl;

import com.kovzan.task_manager.connection.DBConnection;
import com.kovzan.task_manager.dao.DaoBase;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.entity.TaskStatus;
import com.kovzan.task_manager.logger.LogConstant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskDao extends DaoBase<Task> {

	private final static Logger logger = Logger.getLogger(TaskDao.class.getName());

	private static final String addTask =
			"INSERT INTO task " +
			"(name, work, begindate, enddate, projectid, employeeid, status) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String updateTask =
			"UPDATE task " +
			"SET name = ?, work = ?, begindate = ?, enddate = ?, projectid = ?, employeeid = ?, status = ? " +
				"WHERE id = ?";
	private static final String removeTask =
			"UPDATE task SET isdeleted = 1 " +
				"WHERE id = ?";
	private static final String removeTasksByProjectId =
			"UPDATE task SET isdeleted = 1 " +
				"WHERE projectid = ?";
	private static final String selectAllTasksWithRefs =
			"SELECT t.id, t.name, t.begindate, t.work, p.shortname, t.status, t.enddate, CONCAT(e.firstname, ' ', e.lastname) AS fullname, t.employeeid, t.projectid " +
				"FROM task t " +
				"LEFT JOIN project p ON p.id = t.projectid " +
				"LEFT JOIN employee e ON e.id = t.employeeid WHERE t.isdeleted = 0 ORDER BY t.id";
	private static final String selectTaskById =
			"SELECT t.id, t.name, t.begindate, t.work, p.shortname, t.status, t.enddate, CONCAT(e.firstname, ' ', e.lastname) AS fullname, t.employeeid, t.projectid " +
					"FROM task t " +
					"LEFT JOIN project p ON p.id = t.projectid " +
					"LEFT JOIN employee e ON e.id = t.employeeid WHERE t.id = ?";
	private static final String selectTasksByEmployeeId =
			"SELECT t.id, t.name, t.begindate, t.work, p.shortname, t.status, t.enddate, CONCAT(e.firstname, ' ', e.lastname) AS fullname, t.employeeid, t.projectid FROM task t " +
				"LEFT JOIN project p ON p.id = t.projectid " +
				"LEFT JOIN employee e ON e.id = t.employeeid " +
					"WHERE t.employeeid = ? AND t.isdeleted = 0 ORDER BY t.id";
	private static final String selectTasksByProjectId =
			"SELECT t.id, t.name, t.begindate, t.work, p.shortname, t.status, t.enddate, CONCAT(e.firstname, ' ', e.lastname) AS fullname, t.employeeid, t.projectid FROM task t " +
				"LEFT JOIN project p ON p.id = t.projectid " +
				"LEFT JOIN employee e ON e.id = t.employeeid " +
					"WHERE t.projectid = ? AND t.isdeleted = 0 ORDER BY t.id";

	public void add(Task task) throws SQLException {
		add(task, addTask);
	}

	public void update(Task task) throws SQLException {
		update(task, updateTask);
	}

	public void remove(Task task) throws SQLException {
		remove(task, removeTask);
	}

	public List<Task> findAll() throws SQLException {
		return findAll(selectAllTasksWithRefs);
	}

	public Task findById(int id) throws SQLException {
		return findById(id, selectTaskById);
	}

	@Override
	protected List<Task> createEntitiesFromResultSet(ResultSet resultSet) throws SQLException {
		ArrayList<Task> tasks = new ArrayList<>();
		do {
			Task task = new Task();
			task.setId(resultSet.getInt(1));
			task.setName(resultSet.getString(2));
			task.setBeginDate(resultSet.getDate(3).toLocalDate());
			task.setEndDate(resultSet.getDate(7).toLocalDate());
			task.setWork(resultSet.getInt(4));
			task.setProjectShortName(resultSet.getString(5));
			task.setStatus(TaskStatus.valueOf(resultSet.getString(6)));
			task.setEmployeeFullName(resultSet.getString(8));
			task.setEmployeeId(resultSet.getInt(9));
			task.setProjectId(resultSet.getInt(10));
			tasks.add(task);
		}
		while (resultSet.next());
		return tasks;
	}

	@Override
	protected PreparedStatement fillPreparedStatementForAdd(Task task, PreparedStatement statement) throws SQLException {
		statement.setString(1, task.getName());
		statement.setInt(2, task.getWork());
		statement.setDate(3, Date.valueOf(task.getBeginDate()));
		statement.setDate(4, Date.valueOf(task.getEndDate()));
		statement.setInt(5, task.getProjectId());
		statement.setInt(6, task.getEmployeeId());
		statement.setString(7, task.getStatus().toString());
		return statement;
	}

	@Override
	protected PreparedStatement fillPreparedStatementForUpdate(Task task, PreparedStatement statement) throws SQLException {
		statement.setString(1, task.getName());
		statement.setInt(2, task.getWork());
		statement.setDate(3, Date.valueOf(task.getBeginDate()));
		statement.setDate(4, Date.valueOf(task.getEndDate()));
		statement.setInt(5, task.getProjectId());
		statement.setInt(6, task.getEmployeeId());
		statement.setString(7, task.getStatus().toString());
		statement.setInt(8, task.getId());
		return statement;
	}

	public String getAddTaskSQLQuery() {
		return addTask;
	}

	public String getUpdateTaskSQLQuery() {
		return updateTask;
	}

	public String getRemoveTaskSQLQuery() {
		return removeTask;
	}
	
	public String getRemoveTasksByProjectIdQuery() {
		return removeTasksByProjectId;
	}

	public List<Task> findTasksBy(Integer id, String selectStatement) throws SQLException {
		List<Task> tasks = new ArrayList<>();
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(selectStatement);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				tasks = createEntitiesFromResultSet(resultSet);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			}
		}
		return tasks;
	}

	public List<Task> findTasksByEmployeeId(int employeeId) throws SQLException {
		return findTasksBy(employeeId, selectTasksByEmployeeId);
	}

	public List<Task> findTasksByProjectId (int projectId) throws SQLException {
		return findTasksBy(projectId, selectTasksByProjectId);
	}

}
