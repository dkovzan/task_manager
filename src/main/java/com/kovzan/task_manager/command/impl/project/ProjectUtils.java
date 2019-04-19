package com.kovzan.task_manager.command.impl.project;

import static com.kovzan.task_manager.logger.Log.logger;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;

import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.command.ValidationException;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.logger.LogConstant;

public class ProjectUtils {
	
	private static final String INCORRECT_DATA_MESSAGE = "Incorrect data are entered.";
	
	public static Project buildProject(HttpServletRequest request) throws ValidationException, SQLException {
		
		Project project = new Project();
		HashMap<String, String> invalidFields = new HashMap<>();
		Integer id;
		if (request.getParameter(ParameterNameConstant.PROJECT_ID) != null) {
			id = Integer.parseInt(request.getParameter(ParameterNameConstant.PROJECT_ID));
			project.setId(id);
		}
		String name = request.getParameter(ParameterNameConstant.PROJECT_NAME);
		if (ProjectValidator.isProjectNameValid(name)) {
			project.setName(name);
		} else {
			invalidFields.put(ParameterNameConstant.PROJECT_NAME, name);
		}
		String shortName = request.getParameter(ParameterNameConstant.PROJECT_SHORTNAME);
		if (ProjectValidator.isProjectShortNameValid(shortName)) {
			project.setShortName(shortName);
		} else {
			invalidFields.put(ParameterNameConstant.PROJECT_SHORTNAME, shortName);
		}
		String description = request.getParameter(ParameterNameConstant.PROJECT_DESCRIPTION);
		if (ProjectValidator.isProjectDescriptionValid(description)) {
			project.setDescription(description);
		} else {
			invalidFields.put(ParameterNameConstant.PROJECT_DESCRIPTION, description);
		}
		if (!invalidFields.isEmpty()) {
			throw new ValidationException(INCORRECT_DATA_MESSAGE, project, invalidFields);
		}
		logger.log(Level.INFO, LogConstant.OBJECT_CREATED + project.toString());
		return project;
	}

}
