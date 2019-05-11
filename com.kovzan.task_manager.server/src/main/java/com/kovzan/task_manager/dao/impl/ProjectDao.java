package com.kovzan.task_manager.dao.impl;

import com.kovzan.task_manager.connection.DBConnection;
import com.kovzan.task_manager.dao.DaoBase;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.logger.LogConstant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class ProjectDao implements DaoBase<Project> {

	private static final String addProject =
			"INSERT INTO project (name, shortname, description) " +
			"VALUES (?, ?, ?)";
	private static final String updateProject =
			"UPDATE project SET name = ?, shortname = ?, description = ? " +
			"WHERE id = ?";
	private static final String removeProject =
			"DELETE FROM project " +
			"WHERE id = ?";
	private static final String selectAllProjects =
			"SELECT id, name, shortname, description FROM project ORDER BY id";
	private static final String selectProjectById =
			"SELECT id, name, shortname, description FROM project " +
			"WHERE id = ?";
	
	public static boolean isProjectShortNameUnique(int projectId, String projectShortName) throws SQLException {
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
	
	@Override
	public int add(Project element) throws SQLException {
		int result = -1;
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(addProject, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, element.getName());
			statement.setString(2, element.getShortName());
			statement.setString(3, element.getDescription());
			statement.executeUpdate();
			ResultSet keys = statement.getGeneratedKeys();
			if(keys.next()) {
				result = keys.getInt(1);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			}
		}
		return result;
	}

	@Override
	public void update(Project element) throws SQLException {
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(updateProject);
			statement.setString(1, element.getName());
			statement.setString(2, element.getShortName());
			statement.setString(3, element.getDescription());
			statement.setInt(4, element.getId());
			statement.executeUpdate();
			logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		}
	}

	@Override
	public void remove(Project element) throws SQLException {
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(removeProject);
			statement.setInt(1, element.getId());
			statement.execute();
			logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		}
	}

	@Override
	public List<Project> findAll() throws SQLException {
		List<Project> projects = new ArrayList<>();
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(selectAllProjects);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				projects = DaoCreator.createProjects(resultSet);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			}
		}
		return projects;
	}

	@Override
	public Project findById(int projectId) throws SQLException {
		Project project = null;
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(selectProjectById);
			statement.setInt(1, projectId);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				project = DaoCreator.createProjects(resultSet).get(0);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			}
		}
		return project;
	}
	
	public void updateProjectWithTasks(Project projectFromRequest, List<Task> runtimeTasks) throws SQLException {
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
	}
	
	public int addProjectWithTasks(Project projectFromRequest, List<Task> runtimeTasks) throws SQLException {
		int projectId = add(projectFromRequest);
		addTasksToAddedProject(runtimeTasks, projectId);
		return projectId;
	}
	
	private void addTasksToAddedProject(List<Task> runtimeTasks, int projectId) throws SQLException {
		for (Task runtimeTask : runtimeTasks) {
			runtimeTask.setProjectId(projectId);
			TaskDao.getInstance().add(runtimeTask);
		}
	}
}
