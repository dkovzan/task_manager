package com.kovzan.task_manager.command.impl.project;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.CommandEnum;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ValidationException;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.command.impl.service.CommandService;
import com.kovzan.task_manager.command.impl.task.runtime.RuntimeTasksUtils;
import com.kovzan.task_manager.dao.impl.ProjectDao;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.entity.Task;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddProjectCommand implements Command {
	
	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		
		try {
			addProject(request);
			CommandService.cleanSession(request);
		} catch (ValidationException e) {
			request.setAttribute(UtilParams.VALIDATION_EXCEPTION, e);
//			request.setAttribute(UtilParams.IS_ADD_FORM, 1);
			return PageConstant.EDIT_PROJECT_PAGE;
		}
		return CommandEnum.PRINT_PROJECTS.getCommand().execute(request);
	}
	
	private void addProject(HttpServletRequest request) throws SQLException, ValidationException {
		List<Task> runtimeTasks = new ArrayList<>();
		if (request.getSession().getAttribute(TaskParams.PRINTED_RUNTIME_TASKS) != null) {
			runtimeTasks = RuntimeTasksUtils.getRuntimeTasks(request);
		}
		Project projectFromRequest = ProjectUtils.buildProject(request);
		ProjectDao projectDao = new ProjectDao();
		projectDao.addProjectWithTasks(projectFromRequest, runtimeTasks);
	}
}
