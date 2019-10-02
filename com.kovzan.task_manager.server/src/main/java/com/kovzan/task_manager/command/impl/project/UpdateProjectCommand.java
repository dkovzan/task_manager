package com.kovzan.task_manager.command.impl.project;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.Commands;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ValidationException;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.command.impl.task.runtime.RuntimeTasksUtils;
import com.kovzan.task_manager.dao.DaoException;
import com.kovzan.task_manager.dao.ProjectDao;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.entity.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UpdateProjectCommand implements Command {
	
	@Override
	public String execute(HttpServletRequest request) throws DaoException {
		
		try {
			updateProject(request);
			request.getSession().invalidate();
		} catch (ValidationException e) {
			request.setAttribute(UtilParams.VALIDATION_EXCEPTION, e);
			return PageConstant.EDIT_PROJECT_PAGE;
		}
		return Commands.PRINT_PROJECTS.getCommand().execute(request);
	}
	
	private void updateProject(HttpServletRequest request) throws DaoException, ValidationException {
		List<Task> runtimeTasks = RuntimeTasksUtils.getRuntimeTasks(request);
		Project projectFromRequest = ProjectUtils.buildProject(request);
		ProjectDao projectDao = new ProjectDao();
		projectDao.updateProjectWithTasks(projectFromRequest, runtimeTasks);
	}
	
}
