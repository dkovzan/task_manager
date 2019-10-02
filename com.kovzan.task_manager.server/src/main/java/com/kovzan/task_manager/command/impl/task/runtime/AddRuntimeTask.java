package com.kovzan.task_manager.command.impl.task.runtime;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ValidationException;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.command.impl.task.TaskUtils;
import com.kovzan.task_manager.dao.DaoException;
import com.kovzan.task_manager.entity.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AddRuntimeTask implements Command {
	
	private static int runtimeTaskId = -1;
	
	@Override
	public String execute(HttpServletRequest request) throws DaoException {
		try {
			List<Task> runtimeTasks = RuntimeTasksUtils.getRuntimeTasks(request);
			Task taskFromRequest = TaskUtils.buildTask(request);
			if (request.getParameter(TaskParams.TASK_ID) == null) {
				taskFromRequest.setId(getNextRuntimeTaskId());
			}
			addRuntimeTask(runtimeTasks, taskFromRequest);
			request.getSession().setAttribute(TaskParams.PRINTED_RUNTIME_TASKS, runtimeTasks);
		} catch (ValidationException e) {

			TaskUtils.setEmployeesAndTaskStatusesAttributes(request);
			
			request.setAttribute(UtilParams.VALIDATION_EXCEPTION, e);
			request.setAttribute(UtilParams.IS_ADD_FORM, true);
			return PageConstant.EDIT_RUNTIME_TASK_PAGE;
		}
		
		return PageConstant.EDIT_PROJECT_PAGE;
	}
	
	private List<Task> addRuntimeTask(List<Task> runtimeTasks, Task taskFromRequest) {
		runtimeTasks.add(taskFromRequest);
		return runtimeTasks;
	}
	
	private int getNextRuntimeTaskId() {
		return runtimeTaskId--;
	}
}
