package com.kovzan.task_manager.validator;

import java.sql.SQLException;
import java.util.List;

import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.service.ProjectService;

public class ProjectValidator {
	
	private static final String CHECK_PROJECT_NAME_REGEX = "[\\D\\d]{1,255}";
	private static final String CHECK_PROJECT_SHORTNAME_REGEX = "[\\D\\d]{1,255}";
	private static final String CHECK_PROJECT_DESCRIPTION_REGEX = "[\\D\\d]{0,4000}";
	
	public static boolean isProjectValid(Project project) throws SQLException {
		return (isProjectShortNameUnique(project) && validateProjectName(project.getName()) && validateProjectShortName(project.getShortName()) && validateProjectDescription(project.getDescription()));
	}
	
	public static Project getProjectWithValidFields(Project project) throws SQLException {
		Project projectWithValidFields = project;
		if (!validateProjectName(project.getName())) {
			projectWithValidFields.setName(null);
		}
		if (!validateProjectShortName(project.getShortName()) || !isProjectShortNameUnique(project)) {
			projectWithValidFields.setShortName(null);
		}
		if (!validateProjectDescription(project.getDescription())) {
			projectWithValidFields.setDescription(null);
		}
		return projectWithValidFields;
		
	}
	
	public static boolean isProjectShortNameUnique(Project project) throws SQLException {
		List<Project> projects = ProjectService.findAllProjects();
		boolean result = true;
		if (projects != null) {
			if (project.getId() != null) {
				projects.removeIf(p -> p.getId().equals(project.getId()));
			}
			for (Project projectFromDB : projects) {
				if (projectFromDB.getShortName().equals(project.getShortName())) {
					result = false;
				}
			}
		}
		return result;
	}
	
	public static boolean validateProjectName(String name) {
		return name.matches(CHECK_PROJECT_NAME_REGEX);
	}
	
	public static boolean validateProjectShortName(String shortName) throws SQLException {
		return shortName.matches(CHECK_PROJECT_SHORTNAME_REGEX);
	}
	
	public static boolean validateProjectDescription(String description) {
		return description.matches(CHECK_PROJECT_DESCRIPTION_REGEX);
	}

}
