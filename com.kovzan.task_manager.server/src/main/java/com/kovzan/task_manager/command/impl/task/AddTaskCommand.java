package com.kovzan.task_manager.command.impl.task;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.CommandEnum;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ValidationException;
import com.kovzan.task_manager.command.impl.parameters.EmployeeParams;
import com.kovzan.task_manager.command.impl.parameters.ProjectParams;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.dao.impl.EmployeeDao;
import com.kovzan.task_manager.dao.impl.ProjectDao;
import com.kovzan.task_manager.dao.impl.TaskDao;
import com.kovzan.task_manager.entity.TaskStatus;
import com.kovzan.task_manager.entity.Task;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Arrays;

public class AddTaskCommand implements Command {
	
	@Override
	public String execute(HttpServletRequest request) throws SQLException {

		Task taskFromRequest;
		
		try {
			taskFromRequest = TaskUtils.buildTask(request);
		} catch (ValidationException e) {
			TaskUtils.setProjectsEmployeesTaskStatusesAttributes(request);
			request.setAttribute(UtilParams.VALIDATION_EXCEPTION, e);
			request.setAttribute(UtilParams.IS_ADD_FORM, true);
			return PageConstant.EDIT_TASK_PAGE;
		}
		TaskDao taskDao = new TaskDao();
		taskDao.add(taskFromRequest);
		return CommandEnum.PRINT_TASKS.getCommand().execute(request);
	}
	
}
