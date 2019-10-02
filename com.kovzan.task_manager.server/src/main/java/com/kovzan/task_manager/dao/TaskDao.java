package com.kovzan.task_manager.dao;

import com.kovzan.task_manager.connection.ConnectionService;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.entity.TaskStatus;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TaskDao extends DaoBase<Task> {

	private final static Logger logger = Logger.getLogger(TaskDao.class.getName());

	private final String addTask =
			"INSERT INTO task " +
			"(name, work, begindate, enddate, projectid, employeeid, status) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?)";
	private final String updateTask =
			"UPDATE task " +
			"SET name = ?, work = ?, begindate = ?, enddate = ?, projectid = ?, employeeid = ?, status = ? " +
			"WHERE id = ?";
	private final String removeTask =
			"UPDATE task SET isdeleted = 1 " +
			"WHERE id = ?";
	private final String removeTasksByProjectId =
			"UPDATE task SET isdeleted = 1 " +
			"WHERE projectid = ?";
	private final String selectAllTasksWithRefs =
			"SELECT t.id, t.name, t.begindate, t.work, p.shortname, t.status, t.enddate, " +
				"CONCAT(e.firstname, ' ', e.lastname) AS fullname, t.employeeid, t.projectid " +
			"FROM task t " +
			"LEFT JOIN project p ON p.id = t.projectid " +
			"LEFT JOIN employee e ON e.id = t.employeeid WHERE t.isdeleted = 0 ORDER BY t.id";
	private final String selectTaskById =
			"SELECT t.id, t.name, t.begindate, t.work, p.shortname, t.status, t.enddate, " +
				"CONCAT(e.firstname, ' ', e.lastname) AS fullname, t.employeeid, t.projectid " +
			"FROM task t " +
			"LEFT JOIN project p ON p.id = t.projectid " +
			"LEFT JOIN employee e ON e.id = t.employeeid WHERE t.id = ?";
	private final String selectTasksByEmployeeId =
			"SELECT t.id, t.name, t.begindate, t.work, p.shortname, t.status, t.enddate, " +
				"CONCAT(e.firstname, ' ', e.lastname) AS fullname, t.employeeid, t.projectid FROM task t " +
			"LEFT JOIN project p ON p.id = t.projectid " +
			"LEFT JOIN employee e ON e.id = t.employeeid " +
			"WHERE t.employeeid = ? AND t.isdeleted = 0 ORDER BY t.id";
	private final String selectTasksByProjectId =
			"SELECT t.id, t.name, t.begindate, t.work, p.shortname, t.status, t.enddate, " +
				"CONCAT(e.firstname, ' ', e.lastname) AS fullname, t.employeeid, t.projectid FROM task t " +
			"LEFT JOIN project p ON p.id = t.projectid " +
			"LEFT JOIN employee e ON e.id = t.employeeid " +
			"WHERE t.projectid = ? AND t.isdeleted = 0 ORDER BY t.id";

	@Override
	protected List<Task> createEntitiesFromResultSet(ResultSet resultSet) throws DaoException {
		ArrayList<Task> tasks = new ArrayList<>();
		try {
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
		} catch (SQLException e) {
			throw new DaoException("Error while creating tasks from result set: ", e);
		}
		return tasks;
	}

	@Override
	protected PreparedStatement fillPreparedStatementForAdd(Task task, PreparedStatement statement) throws DaoException {
		try {
			statement.setString(1, task.getName());
			statement.setInt(2, task.getWork());
			statement.setDate(3, Date.valueOf(task.getBeginDate()));
			statement.setDate(4, Date.valueOf(task.getEndDate()));
			statement.setInt(5, task.getProjectId());
			statement.setInt(6, task.getEmployeeId());
			statement.setString(7, task.getStatus().toString());
		} catch (SQLException e) {
			throw new DaoException("Error while filling prepared statement for adding task: ", e);
		}
		return statement;
	}

	@Override
	protected PreparedStatement fillPreparedStatementForUpdate(Task task, PreparedStatement statement) throws DaoException {
		try {
			statement.setString(1, task.getName());
			statement.setInt(2, task.getWork());
			statement.setDate(3, Date.valueOf(task.getBeginDate()));
			statement.setDate(4, Date.valueOf(task.getEndDate()));
			statement.setInt(5, task.getProjectId());
			statement.setInt(6, task.getEmployeeId());
			statement.setString(7, task.getStatus().toString());
			statement.setInt(8, task.getId());
		} catch (SQLException e) {
			throw new DaoException("Error while filling prepared statement for updating task: ", e);
		}
		return statement;
	}
	
	@Override
	protected String getAddSqlStatement() {
		return addTask;
	}
	
	@Override
	protected String getRemoveSqlStatement() {
		return removeTask;
	}
	
	@Override
	protected String getUpdateSqlStatement() {
		return updateTask;
	}
	
	@Override
	protected String getFindAllSqlStatement() {
		return selectAllTasksWithRefs;
	}
	
	@Override
	protected String getFindByIdSqlStatement() {
		return selectTaskById;
	}
	
	public void removeTasksByProjectId(int id) throws DaoException {
		try (Connection connection = ConnectionService.getInstance().getConnection()) {
			PreparedStatement statement = connection.prepareStatement(removeTasksByProjectId);
			statement.setInt(1, id);
			statement.executeUpdate();
			logger.info("Tasks of project with id " + id + " successfully deleted");
		} catch (SQLException e) {
			throw new DaoException("Error while removing tasks by project id: ", e);
		}
	}

	public List<Task> findTasksBy(Integer id, String selectQuery) throws DaoException {
		List<Task> tasks = new ArrayList<>();
		try (Connection connection = ConnectionService.getInstance().getConnection()) {
			PreparedStatement statement = connection.prepareStatement(selectQuery);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				tasks = createEntitiesFromResultSet(resultSet);
			}
			logger.info(tasks.size() + " tasks are successfully found by employee or project id.");
		} catch (SQLException e) {
			throw new DaoException("Error while getting tasks by employeeId or projectId: \n query: " + selectQuery, e);
		}
		return tasks;
	}

	public List<Task> findTasksByEmployeeId(int employeeId) throws DaoException {
		return findTasksBy(employeeId, selectTasksByEmployeeId);
	}

	public List<Task> findTasksByProjectId (int projectId) throws DaoException {
		return findTasksBy(projectId, selectTasksByProjectId);
	}

}
