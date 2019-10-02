package com.kovzan.task_manager.command.impl.task;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.Commands;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ValidationException;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.dao.DaoException;
import com.kovzan.task_manager.dao.TaskDao;
import com.kovzan.task_manager.entity.Task;

import javax.servlet.http.HttpServletRequest;

public class UpdateTaskCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) throws DaoException {
		
		TaskDao taskDao = new TaskDao();
		Task taskFromRequest;
		try {
			taskFromRequest = TaskUtils.buildTask(request);
		} catch (ValidationException e) {
			TaskUtils.setProjectsEmployeesTaskStatusesAttributes(request);
			request.setAttribute(UtilParams.VALIDATION_EXCEPTION, e);
			request.setAttribute(UtilParams.IS_ADD_FORM, false);
			return PageConstant.EDIT_TASK_PAGE;
		}
		taskDao.update(taskFromRequest);
		return Commands.PRINT_TASKS.getCommand().execute(request);
	}
}
