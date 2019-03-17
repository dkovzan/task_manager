package com.kovzan.task_manager.service;

import com.kovzan.task_manager.dao.impl.ProjectDAOImpl;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.exception.DAOException;
import com.kovzan.task_manager.logger.LogConstant;

import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class ProjectService {

	public static final ProjectService instance = new ProjectService();

	private ProjectService() {}

	public static ProjectService getInstance() {
		return instance;
	}

	public static List<Project> findAllProjects() throws DAOException {
		return ProjectDAOImpl.getInstance().findAll();
	}

	public static Project findProjectById(int id) throws DAOException {
		Project project = null;
		try {
			project = ProjectDAOImpl.getInstance().findById(id);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
		}
		return project;
	}

	public static void addProject(Project project) throws DAOException {
		ProjectDAOImpl.getInstance().add(project);
	}

	public static void updateProject(Project project) throws DAOException {
		ProjectDAOImpl.getInstance().update(project);
	}

	public static void removeProject(Project project) throws DAOException {
		ProjectDAOImpl.getInstance().remove(project);
	}

}
