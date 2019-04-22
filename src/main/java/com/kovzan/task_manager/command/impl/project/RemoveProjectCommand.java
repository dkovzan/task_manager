package com.kovzan.task_manager.command.impl.project;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.impl.parameters.ProjectParams;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.logger.LogConstant;
import com.kovzan.task_manager.service.ProjectService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class RemoveProjectCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) throws SQLException {

		int projectId = Integer.parseInt(request.getParameter(ProjectParams.PROJECT_ID));
		Project project = new Project();
		project.setId(projectId);
		ProjectService.removeProject(project);
		List<Project> projects = ProjectService.findAllProjects();
		request.setAttribute(ProjectParams.PRINTED_PROJECTS, projects);
		logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		return PageConstant.PROJECTS_PAGE;
	}
}
