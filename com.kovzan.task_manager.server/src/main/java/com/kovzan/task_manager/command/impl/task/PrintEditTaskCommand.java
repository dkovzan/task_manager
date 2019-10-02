package com.kovzan.task_manager.command.impl.task;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.Commands;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.dao.DaoException;
import com.kovzan.task_manager.dao.EmployeeDao;
import com.kovzan.task_manager.dao.ProjectDao;
import com.kovzan.task_manager.dao.TaskDao;
import com.kovzan.task_manager.entity.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

public class PrintEditTaskCommand implements Command {
	
	private static final Logger logger = Logger.getLogger(PrintEditTaskCommand.class.getName());
	
	private static final String TASK_CANNOT_BE_ADDED = "Task cannot be added when projects or employees list is empty.";
	
	@Override
	public String execute(HttpServletRequest request) throws DaoException {
		
		TaskDao taskDao = new TaskDao();
		
		boolean isAddForm = Boolean.parseBoolean(request.getParameter(UtilParams.IS_ADD_FORM));
		if (isAddForm) {
			if (!canTaskBeCreated()) {
				request.setAttribute(UtilParams.ERROR, TASK_CANNOT_BE_ADDED);
				return Commands.PRINT_TASKS.getCommand().execute(request);
			}
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
		return PageConstant.EDIT_TASK_PAGE;
	}
	
	private boolean canTaskBeCreated() throws DaoException {
		ProjectDao projectDao = new ProjectDao();
		EmployeeDao employeeDao = new EmployeeDao();
		if (projectDao.findAll() == null || employeeDao.findAll() == null) {
			return false;
		} else {
			return !projectDao.findAll().isEmpty() && !employeeDao.findAll().isEmpty();
		}
	}
}
