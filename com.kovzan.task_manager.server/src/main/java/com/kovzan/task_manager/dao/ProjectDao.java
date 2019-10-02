package com.kovzan.task_manager.dao;

import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.entity.Task;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProjectDao extends DaoBase<Project> {

	private final static Logger logger = Logger.getLogger(ProjectDao.class.getName());

	private final String addProject =
			"INSERT INTO project (name, shortname, description) " +
			"VALUES (?, ?, ?)";
	private final String updateProject =
			"UPDATE project SET name = ?, shortname = ?, description = ? " +
			"WHERE id = ?";
	private final String removeProject =
			"UPDATE project SET isdeleted = 1 " +
			"WHERE id = ?";
	private final String selectAllProjects =
			"SELECT id, name, shortname, description FROM project WHERE isdeleted = 0 ORDER BY id";
	private final String selectProjectById =
			"SELECT id, name, shortname, description FROM project " +
			"WHERE id = ?";

	@Override
	public void remove(Project project) throws DaoException {
		super.remove(project);
		TaskDao taskDao = new TaskDao();
		taskDao.removeTasksByProjectId(project.getId());
	}
	
	@Override
	protected List<Project> createEntitiesFromResultSet(ResultSet resultSet) throws DaoException {
		ArrayList<Project> projects = new ArrayList<>();
		try {
			do {
				Project project = new Project();
				project.setId(resultSet.getInt(1));
				project.setName(resultSet.getString(2));
				project.setShortName(resultSet.getString(3));
				project.setDescription(resultSet.getString(4));
				projects.add(project);
			}
			while(resultSet.next());
		} catch (SQLException e) {
			throw new DaoException("Error while creating project from result set: ", e);
		}
		return projects;
	}
	
	@Override
	protected PreparedStatement fillPreparedStatementForAdd(Project element, PreparedStatement statement) throws DaoException {
		try {
			statement.setString(1, element.getName());
			statement.setString(2, element.getShortName());
			statement.setString(3, element.getDescription());
		} catch (SQLException e) {
			throw new DaoException("Error while filling prepared statement for adding project: ", e);
		}
		return statement;
	}
	
	@Override
	protected PreparedStatement fillPreparedStatementForUpdate(Project element, PreparedStatement statement) throws DaoException {
		try {
			fillPreparedStatementForAdd(element, statement);
			statement.setInt(4, element.getId());
		} catch (SQLException e) {
			throw new DaoException("Error while filling prepared statement for updating project: ", e);
		}
		return statement;
	}
	
	@Override
	protected String getAddSqlStatement() {
		return addProject;
	}
	
	@Override
	protected String getRemoveSqlStatement() {
		return removeProject;
	}
	
	@Override
	protected String getUpdateSqlStatement() {
		return updateProject;
	}
	
	@Override
	protected String getFindAllSqlStatement() {
		return selectAllProjects;
	}
	
	@Override
	protected String getFindByIdSqlStatement() {
		return selectProjectById;
	}
	
	public void updateProjectWithTasks(Project projectFromRequest, List<Task> runtimeTasks) throws DaoException {
		update(projectFromRequest);
		TaskDao taskDao = new TaskDao();
		List<Task> tasksOfProjectFromDB = taskDao.findTasksByProjectId(projectFromRequest.getId());
		List<Task> tasksToDelete = new ArrayList<>(tasksOfProjectFromDB);
		for (Task runtimeTask : runtimeTasks) {
			if (runtimeTask.getId().compareTo(0) >= 0) {
				for (Task taskOfProjectFromDB : tasksOfProjectFromDB) {
					if (taskOfProjectFromDB.getId().equals(runtimeTask.getId())) {
						if (!taskOfProjectFromDB.equals(runtimeTask)) {
							runtimeTask.setProjectId(projectFromRequest.getId());
							taskDao.update(runtimeTask);
						}
						tasksToDelete.remove(taskOfProjectFromDB);
					}
				}
			} else {
				runtimeTask.setProjectId(projectFromRequest.getId());
				taskDao.add(runtimeTask);
			}
		}
		for (Task taskToDelete : tasksToDelete) {
			taskDao.remove(taskToDelete);
		}
		logger.info(projectFromRequest.toString() + " is successfully updated and contains " + runtimeTasks.size() + " tasks");
	}

	public int addProjectWithTasks(Project projectFromRequest, List<Task> runtimeTasks) throws DaoException {
		int projectId = add(projectFromRequest);
		try {
			addTasksToAddedProject(runtimeTasks, projectId);
			logger.info(projectFromRequest.toString() + " is successfully added with id " + projectId + " and " + runtimeTasks.size() + " tasks");
		} catch (SQLException e) {
			throw new DaoException("Error while adding project with tasks: ", e);
		}
		return projectId;
	}

	private void addTasksToAddedProject(List<Task> runtimeTasks, int projectId) throws DaoException {
		for (Task runtimeTask : runtimeTasks) {
			TaskDao taskDao = new TaskDao();
			runtimeTask.setProjectId(projectId);
			taskDao.add(runtimeTask);
		}
	}

	public static boolean isProjectShortNameUnique(int projectId, String projectShortName) throws DaoException {
		ProjectDao projectDao = new ProjectDao();
		List<Project> projects = projectDao.findAll();
		if (projects != null) {
			projects.removeIf(p -> p.getId().equals(projectId));
			for (Project projectFromDB : projects) {
				if (projectFromDB.getShortName().equals(projectShortName)) {
					return false;
				}
			}
		}
		return true;
	}

}
