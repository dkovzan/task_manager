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

public class UpdateTaskCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		
		TaskDao taskDao = new TaskDao();
		Task taskFromRequest;
		try {
			taskFromRequest = TaskUtils.buildTask(request);
		} catch (ValidationException e) {
			
			ProjectDao projectDao = new ProjectDao();
			EmployeeDao employeeDao = new EmployeeDao();
			
			request.setAttribute(ProjectParams.PRINTED_PROJECTS, projectDao.findAll());
			request.setAttribute(EmployeeParams.PRINTED_EMPLOYEES, employeeDao.findAll());
			request.setAttribute(TaskParams.PRINTED_STATUSES, Arrays.asList(TaskStatus.values()));
			
			request.setAttribute(UtilParams.VALIDATION_EXCEPTION, e);
			request.setAttribute(UtilParams.IS_ADD_FORM, false);
			return PageConstant.EDIT_TASK_PAGE;
		}
		taskDao.update(taskFromRequest);
		return CommandEnum.PRINT_TASKS.getCommand().execute(request);
	}
}
