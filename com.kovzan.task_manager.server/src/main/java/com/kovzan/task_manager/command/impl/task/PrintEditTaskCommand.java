package com.kovzan.task_manager.command.impl.task;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.CommandEnum;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.impl.parameters.EmployeeParams;
import com.kovzan.task_manager.command.impl.parameters.ProjectParams;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.dao.impl.EmployeeDao;
import com.kovzan.task_manager.dao.impl.ProjectDao;
import com.kovzan.task_manager.dao.impl.TaskDao;
import com.kovzan.task_manager.entity.TaskStatus;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.logger.LogConstant;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class PrintEditTaskCommand implements Command {
	
	private static final String TASK_CANNOT_BE_ADDED = "Task cannot be added when projects or employees list is empty.";

	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		
		TaskDao taskDao = new TaskDao();

		if (!canTaskBeCreated()) {
			request.setAttribute(UtilParams.ERROR, TASK_CANNOT_BE_ADDED);
			return CommandEnum.PRINT_TASKS.getCommand().execute(request);
		} else {
			boolean isAddForm = Boolean.parseBoolean(request.getParameter(UtilParams.IS_ADD_FORM));
			if (isAddForm) {
				TaskUtils.setProjectsEmployeesTaskStatusesAttributes(request);
				request.setAttribute(UtilParams.IS_ADD_FORM, true);
			} else {
				Task task;
				int taskId = Integer.parseInt(request.getParameter(TaskParams.TASK_ID));
				task = taskDao.findById(taskId);
				request.setAttribute(TaskParams.PRINTED_EDIT_TASK, task);
				TaskUtils.setProjectsEmployeesTaskStatusesAttributes(request);
				request.setAttribute(UtilParams.IS_ADD_FORM, false);
			}
			logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			return PageConstant.EDIT_TASK_PAGE;
		}
	}
	
	private static boolean canTaskBeCreated() throws SQLException {
		ProjectDao projectDao = new ProjectDao();
		EmployeeDao employeeDao = new EmployeeDao();
		if (projectDao.findAll() == null || employeeDao.findAll() == null) {
			return false;
		} else {
			return !projectDao.findAll().isEmpty() && !employeeDao.findAll().isEmpty();
		}
	}
}
