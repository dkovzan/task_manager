package com.kovzan.task_manager.command.impl.project;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.impl.parameters.ProjectParams;
import com.kovzan.task_manager.dao.impl.ProjectDao;
import com.kovzan.task_manager.entity.Project;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class PrintProjectsCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		
		ProjectDao projectDao = new ProjectDao();
		List<Project> projects = projectDao.findAll();
		request.setAttribute(ProjectParams.PRINTED_PROJECTS, projects);
		return PageConstant.PROJECTS_PAGE;
	}
}
