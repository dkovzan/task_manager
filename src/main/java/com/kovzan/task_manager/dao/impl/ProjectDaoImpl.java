package com.kovzan.task_manager.dao.impl;

import com.kovzan.task_manager.connection.DBConnection;
import com.kovzan.task_manager.dao.DaoBase;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.dao.DaoException;
import com.kovzan.task_manager.logger.LogConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class ProjectDaoImpl implements DaoBase<Project> {

	private static final String ADD_PROJECT =
			"INSERT INTO PROJECTS (NAME, SHORTNAME, DESCRIPTION) " +
			"VALUES (?, ?, ?)";
	private static final String UPDATE_PROJECT =
			"UPDATE PROJECTS SET NAME = ?, SHORTNAME = ?, DESCRIPTION = ? " +
			"WHERE ID = ?";
	private static final String REMOVE_PROJECT =
			"DELETE FROM PROJECTS " +
			"WHERE ID = ?";
	private static final String SELECT_ALL_PROJECTS =
			"SELECT * FROM PROJECTS";
	private static final String SELECT_PROJECT_BY_ID =
			"SELECT * FROM PROJECTS " +
			"WHERE ID = ?";

	private static ProjectDaoImpl instance = new ProjectDaoImpl();

	public static ProjectDaoImpl getInstance() {
		return instance;
	}

	@Override
	public int add(Project element) throws DaoException {
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(ADD_PROJECT);
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
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return -1;
	}

	@Override
	public int update(Project element) throws DaoException {
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(UPDATE_PROJECT);
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
		catch (SQLException e) {
			throw new DaoException(e);
		}
		return -1;
	}

	@Override
	public void remove(Project element) throws DaoException {
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(REMOVE_PROJECT);
			statement.setInt(1, element.getId());
			statement.execute();
			logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		}
		catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public List<Project> findAll() throws DaoException {
		List<Project> projects = null;
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PROJECTS);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				projects = DaoCreator.createProjects(resultSet);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			}
			else {
				return projects;
			}

		}
		catch (SQLException e) {
			throw new DaoException(e);
		}
		return projects;
	}

	@Override
	public Project findById(int projectId) throws DaoException {
		Project project = null;
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(SELECT_PROJECT_BY_ID);
			statement.setInt(1, projectId);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				project = DaoCreator.createProjects(resultSet).get(0);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			}
			else {
				return project;
			}
		}
		catch (SQLException e) {
			throw new DaoException(e);
		}
		return project;
	}
}
