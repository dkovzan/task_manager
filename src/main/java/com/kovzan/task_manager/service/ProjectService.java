package com.kovzan.task_manager.service;

import com.kovzan.task_manager.dao.impl.ProjectDaoImpl;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.dao.DaoException;
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

	public static List<Project> findAllProjects() throws DaoException {
		return ProjectDaoImpl.getInstance().findAll();
	}

	public static Project findProjectById(int id) throws DaoException {
		Project project = null;
		try {
			project = ProjectDaoImpl.getInstance().findById(id);
		} catch (DaoException e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
		}
		return project;
	}

	public static void addProject(Project project) throws DaoException {
		ProjectDaoImpl.getInstance().add(project);
	}

	public static void updateProject(Project project) throws DaoException {
		ProjectDaoImpl.getInstance().update(project);
	}

	public static void removeProject(Project project) throws DaoException {
		ProjectDaoImpl.getInstance().remove(project);
	}

}
