package com.kovzan.task_manager.command.impl.project;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.CommandEnum;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ValidationException;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.command.impl.task.runtime.RuntimeTasksUtils;
import com.kovzan.task_manager.dao.impl.ProjectDao;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.entity.Task;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdateProjectCommand implements Command {
	
	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		
		try {
			updateProject(request);
			request.getSession().invalidate();
		} catch (ValidationException e) {
			request.setAttribute(UtilParams.VALIDATION_EXCEPTION, e);
			return PageConstant.EDIT_PROJECT_PAGE;
		}
		return CommandEnum.PRINT_PROJECTS.getCommand().execute(request);
	}
	
	private void updateProject(HttpServletRequest request) throws SQLException, ValidationException {
		List<Task> runtimeTasks = RuntimeTasksUtils.getRuntimeTasks(request);
		Project projectFromRequest = ProjectUtils.buildProject(request);
		ProjectDao projectDao = new ProjectDao();
		projectDao.updateProjectWithTasks(projectFromRequest, runtimeTasks);
	}
	
}
