package com.kovzan.task_manager.command.impl.task.runtime;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ValidationException;
import com.kovzan.task_manager.command.impl.parameters.EmployeeParams;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.command.impl.service.CommandService;
import com.kovzan.task_manager.command.impl.task.TaskUtils;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.entity.TaskStatus;
import com.kovzan.task_manager.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class UpdateRuntimeTask implements Command {
	
	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		try {
			List<Task> runtimeTasks = RuntimeTasksUtils.getRuntimeTasks(request);
			Task taskFromRequest = TaskUtils.buildTask(request);
			RuntimeTasksUtils.updateRuntimeTask(runtimeTasks, taskFromRequest);
			request.getSession().setAttribute(TaskParams.PRINTED_RUNTIME_TASKS, runtimeTasks);
			CommandService.setEditProjectModeByProjectId(request, taskFromRequest.getProjectId());
		} catch (ValidationException e) {
			request.setAttribute(UtilParams.VALIDATION_EXCEPTION, e);
			request.setAttribute(EmployeeParams.PRINTED_EMPLOYEES, EmployeeService.findAllEmployees());
			request.setAttribute(TaskParams.PRINTED_STATUSES, Arrays.asList(TaskStatus.values()));
			request.setAttribute(UtilParams.IS_ADD_FORM, 0);
			return PageConstant.EDIT_RUNTIME_TASK_PAGE;
		}
		return null;
	}
}