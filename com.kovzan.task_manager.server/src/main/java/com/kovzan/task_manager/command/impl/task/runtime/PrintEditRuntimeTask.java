package com.kovzan.task_manager.command.impl.task.runtime;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ValidationException;
import com.kovzan.task_manager.command.impl.parameters.EmployeeParams;
import com.kovzan.task_manager.command.impl.parameters.ProjectParams;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.command.impl.project.ProjectUtils;
import com.kovzan.task_manager.dao.impl.EmployeeDao;
import com.kovzan.task_manager.entity.Employee;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.entity.TaskStatus;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrintEditRuntimeTask implements Command {
	
	public static final String TASK_CANNOT_BE_CREATED_WITHOUT_EMPLOYEES = "Task cannot be created without employees";
	
	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		
		EmployeeDao employeeDao = new EmployeeDao();
		List<Employee> employees = employeeDao.findAll();
		
		if (employees.isEmpty()) {
			request.setAttribute(UtilParams.ERROR, TASK_CANNOT_BE_CREATED_WITHOUT_EMPLOYEES);
			return PageConstant.EDIT_PROJECT_PAGE;
		}
		
		try {
			request.getSession().setAttribute(ProjectParams.PRINTED_EDIT_PROJECT, ProjectUtils.buildProject(request));
			request.setAttribute(EmployeeParams.PRINTED_EMPLOYEES, employees);
			request.setAttribute(TaskParams.PRINTED_STATUSES, Arrays.asList(TaskStatus.values()));
			setAttributesForCurrentMode(request, Boolean.parseBoolean(request.getParameter(UtilParams.IS_ADD_FORM)));
		} catch (ValidationException e) {
			request.setAttribute(UtilParams.VALIDATION_EXCEPTION, e);
			request.getSession().setAttribute(ProjectParams.PRINTED_EDIT_PROJECT, e.getEntity());
			return PageConstant.EDIT_PROJECT_PAGE;
		}
		return PageConstant.EDIT_RUNTIME_TASK_PAGE;
	}
	
	private void setAttributesForCurrentMode(HttpServletRequest request, boolean isAddForm) {
		if (isAddForm) {
			request.setAttribute(UtilParams.IS_ADD_FORM, true);
		} else {
			List<Task> runtimeTasks = new ArrayList<>();
			if (request.getSession().getAttribute(TaskParams.PRINTED_RUNTIME_TASKS) != null) {
				runtimeTasks = (List<Task>) request.getSession().getAttribute(TaskParams.PRINTED_RUNTIME_TASKS);
			}
			int taskId = Integer.parseInt(request.getParameter(TaskParams.TASK_ID));
			Task runtimeTask = RuntimeTasksUtils.findRuntimeTaskById(runtimeTasks, taskId);
			request.setAttribute(UtilParams.IS_ADD_FORM, false);
			request.setAttribute(TaskParams.PRINTED_EDIT_RUNTIME_TASK, runtimeTask);
		}
	}
}
