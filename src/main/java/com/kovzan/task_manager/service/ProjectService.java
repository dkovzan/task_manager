package com.kovzan.task_manager.service;

import com.kovzan.task_manager.dao.impl.ProjectDAOImpl;
import com.kovzan.task_manager.entities.Project;
import com.kovzan.task_manager.logger.LogConstant;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class ProjectService {

	public static List<Project> findAllProjects() throws SQLException {
		List<Project> projects = null;
		try {
			projects = ProjectDAOImpl.getInstance().findAll();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
		}
		return projects;
	}

	public static Project findProjectById(int id) throws SQLException {
		Project project = null;
		try {
			project = ProjectDAOImpl.getInstance().findProjectById(id);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
		}
		return project;
	}

	public static void addProject(Project project) throws SQLException {
		try {
			ProjectDAOImpl.getInstance().add(project);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
		}
	}

	public static void updateProject(Project project) throws SQLException {
		try {
			ProjectDAOImpl.getInstance().update(project);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
		}
	}

	public static void removeProject(Project project) throws SQLException {
		try {
			ProjectDAOImpl.getInstance().remove(project);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
		}
	}

}
