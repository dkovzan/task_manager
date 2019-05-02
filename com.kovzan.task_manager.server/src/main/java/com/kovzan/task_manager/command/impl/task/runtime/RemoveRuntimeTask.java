package com.kovzan.task_manager.command.impl.task.runtime;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ValidationException;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.command.impl.project.ProjectUtils;
import com.kovzan.task_manager.command.impl.service.CommandService;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.entity.Task;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class RemoveRuntimeTask implements Command {
	
	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		Project project;
		try {
			project = ProjectUtils.buildProject(request);
			int runtimeTaskId = Integer.parseInt(request.getParameter(TaskParams.TASK_ID));
			List<Task> runtimeTasks = RuntimeTasksUtils.getRuntimeTasks(request);
			RuntimeTasksUtils.removeRuntimeTask(runtimeTasks, runtimeTaskId);
			request.getSession().setAttribute(TaskParams.PRINTED_RUNTIME_TASKS, runtimeTasks);
			
		} catch (ValidationException e) {
			request.setAttribute(UtilParams.VALIDATION_EXCEPTION, e);
			project = (Project) e.getEntity();
		}
		CommandService.setEditProjectModeByProjectId(request, project.getId());
		return PageConstant.EDIT_PROJECT_PAGE;
	}
}