package com.kovzan.task_manager.command.impl.project;

import java.sql.SQLException;

import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.service.ProjectService;

public class ProjectValidator {
	
	private static final String CHECK_PROJECT_NAME_REGEX = "[\\D\\d]{1,255}";
	private static final String CHECK_PROJECT_SHORTNAME_REGEX = "[\\D\\d]{1,255}";
	private static final String CHECK_PROJECT_DESCRIPTION_REGEX = "[\\D\\d]{0,4000}";
	
	public static boolean isProjectValid(Project project) throws SQLException {
		return (ProjectService.isProjectShortNameUnique(project) && isProjectNameValid(project.getName()) && isProjectShortNameValid(project.getShortName()) && isProjectDescriptionValid(project.getDescription()));
	}
	
	public static Project getProjectWithValidFields(Project project) throws SQLException {
		Project projectWithValidFields = project;
		if (!isProjectNameValid(project.getName())) {
			projectWithValidFields.setName(null);
		}
		if (!isProjectShortNameValid(project.getShortName()) || !ProjectService.isProjectShortNameUnique(project)) {
			projectWithValidFields.setShortName(null);
		}
		if (!isProjectDescriptionValid(project.getDescription())) {
			projectWithValidFields.setDescription(null);
		}
		return projectWithValidFields;
		
	}
	
	public static boolean isProjectNameValid(String name) {
		return name.matches(CHECK_PROJECT_NAME_REGEX);
	}
	
	public static boolean isProjectShortNameValid(String shortName) {
		return shortName.matches(CHECK_PROJECT_SHORTNAME_REGEX);
	}
	
	public static boolean isProjectDescriptionValid(String description) {
		return description.matches(CHECK_PROJECT_DESCRIPTION_REGEX);
	}

}
