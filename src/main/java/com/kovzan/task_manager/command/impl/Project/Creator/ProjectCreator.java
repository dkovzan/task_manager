package com.kovzan.task_manager.command.impl.Project.Creator;

import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.entities.Project;
import com.kovzan.task_manager.logger.LogConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class ProjectCreator {

	public static Project createProjectFromRequest(HttpServletRequest request) {

		String projectName = request.getParameter(ParameterNameConstant.PROJECT_NAME);
		String projectShortName = request.getParameter(ParameterNameConstant.PROJECT_SHORTNAME);
		String projectDescription = request.getParameter(ParameterNameConstant.PROJECT_DESCRIPTION);
		Project project = new Project(projectName, projectShortName, projectDescription);
		logger.log(Level.INFO, LogConstant.OBJECT_CREATED + project.toString());
		return project;
	}

	public static Project createProjectWithIdFromRequest(HttpServletRequest request) {

		Project project = createProjectFromRequest(request);
		try {
			int projectId = Integer.parseInt(request.getParameter(ParameterNameConstant.PROJECT_ID));
			project.setId(projectId);
		} catch (Exception e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
		}
		logger.log(Level.INFO, LogConstant.OBJECT_CREATED + project.toString());
		return project;
	}
}
