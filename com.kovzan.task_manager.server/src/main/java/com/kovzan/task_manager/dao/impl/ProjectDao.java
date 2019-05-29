package com.kovzan.task_manager.dao.impl;

import com.kovzan.task_manager.dao.DaoBase;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.logger.LogConstant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectDao extends DaoBase<Project> {

	private final static Logger logger = Logger.getLogger(ProjectDao.class.getName());

	private static final String addProject =
			"INSERT INTO project (name, shortname, description) " +
			"VALUES (?, ?, ?)";
	private static final String updateProject =
			"UPDATE project SET name = ?, shortname = ?, description = ? " +
			"WHERE id = ?";
	private static final String removeProject =
			"UPDATE project SET isdeleted = 1 " +
			"WHERE id = ?";
	private static final String selectAllProjects =
			"SELECT id, name, shortname, description FROM project WHERE isdeleted = 0 ORDER BY id";
	private static final String selectProjectById =
			"SELECT id, name, shortname, description FROM project " +
			"WHERE id = ?";

	public void add(Project project) throws SQLException {
		add(project, addProject);
	}
	
	public void remove(Project project) throws SQLException {
		TaskDao taskDao = new TaskDao();
		remove(project, removeProject);
		remove(project, taskDao.getRemoveTasksByProjectIdQuery());
	}

	public List<Project> findAll() throws SQLException {
		return findAll(selectAllProjects);
	}

	public Project findById(int id) throws SQLException {
		return findById(id, selectProjectById);
	}

	public void updateProjectWithTasks(Project projectFromRequest, List<Task> runtimeTasks) throws SQLException {
		update(projectFromRequest, updateProject);
		TaskDao taskDao = new TaskDao();
		List<Task> tasksOfProjectFromDB = taskDao.findTasksByProjectId(projectFromRequest.getId());
		List<Task> tasksToDelete = new ArrayList<>(tasksOfProjectFromDB);
		for (Task runtimeTask : runtimeTasks) {
			if (runtimeTask.getId().compareTo(0) >= 0) {
				for (Task taskOfProjectFromDB : tasksOfProjectFromDB) {
					if (taskOfProjectFromDB.getId().equals(runtimeTask.getId())) {
						if (!taskOfProjectFromDB.equals(runtimeTask)) {
							runtimeTask.setProjectId(projectFromRequest.getId());
							taskDao.update(runtimeTask, taskDao.getUpdateTaskSQLQuery());
						}
						tasksToDelete.remove(taskOfProjectFromDB);
					}
				}
			} else {
				runtimeTask.setProjectId(projectFromRequest.getId());
				taskDao.add(runtimeTask, taskDao.getAddTaskSQLQuery());
			}
		}
		for (Task taskToDelete : tasksToDelete) {
			taskDao.remove(taskToDelete, taskDao.getRemoveTaskSQLQuery());
		}
		logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
	}

	public int addProjectWithTasks(Project projectFromRequest, List<Task> runtimeTasks) throws SQLException {
		int projectId = add(projectFromRequest, addProject);
		addTasksToAddedProject(runtimeTasks, projectId);
		logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		return projectId;
	}

	private void addTasksToAddedProject(List<Task> runtimeTasks, int projectId) throws SQLException {
		for (Task runtimeTask : runtimeTasks) {
			TaskDao taskDao = new TaskDao();
			runtimeTask.setProjectId(projectId);
			taskDao.add(runtimeTask, taskDao.getAddTaskSQLQuery());
		}
		logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
	}

	public static boolean isProjectShortNameUnique(int projectId, String projectShortName) throws SQLException {
		ProjectDao projectDao = new ProjectDao();
		List<Project> projects = projectDao.findAll(selectAllProjects);
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

	@Override
	protected List<Project> createEntitiesFromResultSet(ResultSet resultSet) throws SQLException {
		ArrayList<Project> projects = new ArrayList<>();
		do {
			Project project = new Project();
			project.setId(resultSet.getInt(1));
			project.setName(resultSet.getString(2));
			project.setShortName(resultSet.getString(3));
			project.setDescription(resultSet.getString(4));
			projects.add(project);
		}
		while(resultSet.next());
		return projects;
	}

	@Override
	protected PreparedStatement fillPreparedStatementForAdd(Project element, PreparedStatement statement) throws SQLException {
		statement.setString(1, element.getName());
		statement.setString(2, element.getShortName());
		statement.setString(3, element.getDescription());
		return statement;
	}

	@Override
	protected PreparedStatement fillPreparedStatementForUpdate(Project element, PreparedStatement statement) throws SQLException {
		statement.setString(1, element.getName());
		statement.setString(2, element.getShortName());
		statement.setString(3, element.getDescription());
		statement.setInt(4, element.getId());
		return statement;
	}

}
