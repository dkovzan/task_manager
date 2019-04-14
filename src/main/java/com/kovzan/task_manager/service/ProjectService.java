package com.kovzan.task_manager.service;

import com.kovzan.task_manager.dao.impl.ProjectDaoImpl;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.logger.LogConstant;
import com.kovzan.task_manager.validator.ProjectValidator;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class ProjectService {

	public static final ProjectService instance = new ProjectService();

	private ProjectService() {}

	public static ProjectService getInstance() {
		return instance;
	}

	public static List<Project> findAllProjects() throws SQLException {
		return ProjectDaoImpl.getInstance().findAll();
	}

	public static Project findProjectById(int id) throws SQLException {
		Project project = null;
		project = ProjectDaoImpl.getInstance().findById(id);
		if (project == null) {
			String paramName = "Id";
			//throw new Exception(String.format(LogConstant.PROJECT_NOT_FOUND, paramName, id));
			logger.log(Level.WARNING, String.format(LogConstant.PROJECT_NOT_FOUND, paramName, id));
		}
		return project;
	}

	public static void addProject(Project project) throws SQLException {
		ProjectDaoImpl.getInstance().add(project);
	}

	public static void updateProject(Project project) throws SQLException {
		ProjectDaoImpl.getInstance().update(project);
	}

	public static void removeProject(Project project) throws SQLException {
		ProjectDaoImpl.getInstance().remove(project);
	}
	
	public static boolean isProjectValid(Project project) throws SQLException {
		return ProjectValidator.isProjectValid(project);
	}
	
	public static Project getProjectWithValidFields(Project project) throws SQLException {
		return ProjectValidator.getProjectWithValidFields(project);
	}
	
	public static boolean isProjectShortNameUnique(Project project) throws SQLException {
		return ProjectValidator.isProjectShortNameUnique(project);
	}

}
