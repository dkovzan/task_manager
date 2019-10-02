package com.kovzan.task_manager.command.impl.project;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.Commands;
import com.kovzan.task_manager.command.impl.parameters.ProjectParams;
import com.kovzan.task_manager.dao.DaoException;
import com.kovzan.task_manager.dao.ProjectDao;
import com.kovzan.task_manager.entity.Project;

import javax.servlet.http.HttpServletRequest;

public class RemoveProjectCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) throws DaoException {

		int projectId = Integer.parseInt(request.getParameter(ProjectParams.PROJECT_ID));
		Project project = new Project();
		project.setId(projectId);
		ProjectDao projectDao = new ProjectDao();
		projectDao.remove(project);
		return Commands.PRINT_PROJECTS.getCommand().execute(request);
	}
}
