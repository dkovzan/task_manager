package com.kovzan.task_manager.command.impl.project;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.CommandEnum;
import com.kovzan.task_manager.command.impl.parameters.ProjectParams;
import com.kovzan.task_manager.dao.impl.ProjectDao;
import com.kovzan.task_manager.entity.Project;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class RemoveProjectCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) throws SQLException {

		int projectId = Integer.parseInt(request.getParameter(ProjectParams.PROJECT_ID));
		Project project = new Project();
		project.setId(projectId);
		ProjectDao projectDao = new ProjectDao();
		projectDao.remove(project);
		return CommandEnum.PRINT_PROJECTS.getCommand().execute(request);
	}
}
