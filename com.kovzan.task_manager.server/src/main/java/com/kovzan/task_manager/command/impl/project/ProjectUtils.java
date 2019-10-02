package com.kovzan.task_manager.command.impl.project;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.kovzan.task_manager.command.ValidationException;
import com.kovzan.task_manager.command.impl.parameters.ProjectParams;
import com.kovzan.task_manager.dao.DaoException;
import com.kovzan.task_manager.dao.ProjectDao;
import com.kovzan.task_manager.entity.Project;

public class ProjectUtils {

	private static final String INCORRECT_DATA_MESSAGE = "Incorrect data are entered.";

	public static Project createProjectFromRequest(HttpServletRequest request) {
		
		Project project = new Project();
		project.setId(Integer.parseInt(request.getParameter(ProjectParams.PROJECT_ID)));
		project.setName(request.getParameter(ProjectParams.PROJECT_NAME));
		project.setShortName(request.getParameter(ProjectParams.PROJECT_SHORTNAME));
		project.setDescription(request.getParameter(ProjectParams.PROJECT_DESCRIPTION));
		return project;
	}

	public static Project buildProject(HttpServletRequest request) throws ValidationException, DaoException {
		
		Project project = new Project();
		HashMap<String, String> invalidFields = new HashMap<>();
		
		project.setId(Integer.parseInt(request.getParameter(ProjectParams.PROJECT_ID)));
		
		String name = request.getParameter(ProjectParams.PROJECT_NAME).trim();
		if (ProjectValidator.isProjectNameValid(name)) {
			project.setName(name);
		} else {
			invalidFields.put(ProjectParams.PROJECT_NAME, name);
		}
		
		String shortName = request.getParameter(ProjectParams.PROJECT_SHORTNAME).trim();
		if (ProjectDao.isProjectShortNameUnique(project.getId(), shortName)) {
			if (ProjectValidator.isProjectShortNameValid(shortName)) {
				project.setShortName(shortName);
			} else {
				invalidFields.put(ProjectParams.PROJECT_SHORTNAME, shortName);
				
			}
		} else {
			invalidFields.put(ProjectParams.PROJECT_SHORTNAME, shortName);
			invalidFields.put(ProjectParams.PROJECT_SHORTNAME_NOT_UNIQUE, shortName);
		}
		
		String description = request.getParameter(ProjectParams.PROJECT_DESCRIPTION).trim();
		if (ProjectValidator.isProjectDescriptionValid(description)) {
			project.setDescription(description);
		} else {
			invalidFields.put(ProjectParams.PROJECT_DESCRIPTION, description);
		}
		
		if (!invalidFields.isEmpty()) {
			throw new ValidationException(INCORRECT_DATA_MESSAGE, project, invalidFields);
		}
		
		return project;
	}

}
