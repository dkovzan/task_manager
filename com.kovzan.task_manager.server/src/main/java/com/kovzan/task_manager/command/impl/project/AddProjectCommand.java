package com.kovzan.task_manager.command.impl.project;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ValidationException;
import com.kovzan.task_manager.command.impl.parameters.ProjectParams;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.command.impl.service.CommandService;
import com.kovzan.task_manager.command.impl.task.runtime.RuntimeTasksUtils;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.logger.LogConstant;
import com.kovzan.task_manager.service.ProjectService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class AddProjectCommand implements Command {
	
	private static final String PROJECT_SHORTNAME_IS_NOT_UNIQUE = "Project shortname should be unique.";

	@Override
	public String execute(HttpServletRequest request) throws SQLException {

		Project projectFromRequest;
		List<Task> runtimeTasks = new ArrayList<>();
		if (request.getSession().getAttribute(TaskParams.PRINTED_RUNTIME_TASKS) != null) {
			runtimeTasks = RuntimeTasksUtils.getRuntimeTasks(request);
		}
		try {
			projectFromRequest = ProjectUtils.buildProject(request);
		} catch (ValidationException e) {
			request.setAttribute(UtilParams.VALIDATION_EXCEPTION, e);
			request.setAttribute(UtilParams.IS_ADD_FORM, 1);
			return PageConstant.EDIT_PROJECT_PAGE;
		}
		
		if (ProjectService.isProjectShortNameUnique(projectFromRequest)) {
			ProjectService.addProjectWithTasks(projectFromRequest, runtimeTasks);
			CommandService.cleanSession(request);
			List<Project> projects = ProjectService.findAllProjects();
			request.setAttribute(ProjectParams.PRINTED_PROJECTS, projects);
			logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			return PageConstant.PROJECTS_PAGE;
		} else {
			request.setAttribute(UtilParams.IS_ADD_FORM, 1);
			request.setAttribute(ProjectParams.PRINTED_EDIT_PROJECT, projectFromRequest);
			request.setAttribute(UtilParams.ERROR, PROJECT_SHORTNAME_IS_NOT_UNIQUE);
			return PageConstant.EDIT_PROJECT_PAGE;
		}
		
	}
}
