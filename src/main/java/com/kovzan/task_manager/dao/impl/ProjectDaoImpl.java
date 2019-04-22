package com.kovzan.task_manager.dao.impl;

import com.kovzan.task_manager.connection.DBConnection;
import com.kovzan.task_manager.dao.DaoBase;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.logger.LogConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class ProjectDaoImpl implements DaoBase<Project> {

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

	private static ProjectDaoImpl instance = new ProjectDaoImpl();

	public static ProjectDaoImpl getInstance() {
		return instance;
	}

	@Override
	public int add(Project element) throws SQLException {
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(addProject);
			statement.setString(1, element.getName());
			statement.setString(2, element.getShortName());
			statement.setString(3, element.getDescription());
			statement.executeUpdate();
			ResultSet keys = statement.getGeneratedKeys();
			int result;
			if(keys.next()) {
				result = keys.getInt(1);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
				return result;
			}
		}
		return -1;
	}

	@Override
	public int update(Project element) throws SQLException {
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(updateProject);
			statement.setString(1, element.getName());
			statement.setString(2, element.getShortName());
			statement.setString(3, element.getDescription());
			statement.setInt(4, element.getId());
			statement.executeUpdate();
			int result;
			ResultSet keys = statement.getGeneratedKeys();
			if(keys.next()) {
				result = keys.getInt(1);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
				return result;
			}
		}
		return -1;
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
		List<Project> projects = null;
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
}
