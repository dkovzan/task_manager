package com.kovzan.task_manager.command.impl.project;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.command.service.EntityCreatorFromRequest;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.logger.LogConstant;
import com.kovzan.task_manager.service.ProjectService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class AddProjectCommand implements Command {
	
	private static final String PROJECT_SHORTNAME_IS_NOT_UNIQUE = "Project shortname should be unique.";
	private static final String INCORRECT_DATA = "Incorrect data is entered.";

	@Override
	public String execute(HttpServletRequest request) throws SQLException {

		Project projectFromRequest = EntityCreatorFromRequest.createProjectFromRequest(request);
		if (ProjectService.isProjectValid(projectFromRequest)) {
			ProjectService.addProject(projectFromRequest);
			List<Project> projects = ProjectService.findAllProjects();
			request.setAttribute(ParameterNameConstant.PRINTED_PROJECTS, projects);
			logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			return PageConstant.PROJECTS_PAGE;
		} else if (!ProjectService.isProjectShortNameUnique(projectFromRequest)) {
			Project projectWithValidFields = ProjectService.getProjectWithValidFields(projectFromRequest);
			request.setAttribute(ParameterNameConstant.IS_ADD_FORM, 1);
			request.setAttribute(ParameterNameConstant.PRINTED_EDIT_PROJECT, projectWithValidFields);
			request.setAttribute(ParameterNameConstant.ERROR, PROJECT_SHORTNAME_IS_NOT_UNIQUE);
			return PageConstant.EDIT_PROJECT_PAGE;
		} else {
			Project projectWithValidFields = ProjectService.getProjectWithValidFields(projectFromRequest);
			request.setAttribute(ParameterNameConstant.IS_ADD_FORM, 1);
			request.setAttribute(ParameterNameConstant.ERROR, INCORRECT_DATA);
			request.setAttribute(ParameterNameConstant.PRINTED_EDIT_PROJECT, projectWithValidFields);
			return PageConstant.EDIT_PROJECT_PAGE;
		}
		
	}
}
