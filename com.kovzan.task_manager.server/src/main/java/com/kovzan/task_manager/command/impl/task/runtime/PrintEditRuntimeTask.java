package com.kovzan.task_manager.command.impl.task.runtime;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ValidationException;
import com.kovzan.task_manager.command.impl.parameters.EmployeeParams;
import com.kovzan.task_manager.command.impl.parameters.ProjectParams;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.command.impl.project.ProjectUtils;
import com.kovzan.task_manager.command.impl.service.CommandService;
import com.kovzan.task_manager.entity.Employee;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.entity.TaskStatus;
import com.kovzan.task_manager.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrintEditRuntimeTask implements Command {
	
	public static final String TASK_CANNOT_BE_CREATED_WITHOUT_EMPLOYEES = "Task cannot be created without employees";
	
	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		List<Employee> employees = EmployeeService.findAllEmployees();
		
		if (employees.isEmpty()) {
			request.setAttribute(UtilParams.ERROR, TASK_CANNOT_BE_CREATED_WITHOUT_EMPLOYEES);
			return PageConstant.EDIT_PROJECT_PAGE;
		}
		
		try {
			request.getSession().setAttribute(ProjectParams.PRINTED_EDIT_PROJECT, ProjectUtils.buildProject(request));
			request.setAttribute(EmployeeParams.PRINTED_EMPLOYEES, employees);
			List<TaskStatus> statuses = Arrays.asList(TaskStatus.values());
			int isAddForm = Integer.parseInt(request.getParameter(UtilParams.IS_ADD_FORM));
			if (isAddForm == 1) {
				request.setAttribute(UtilParams.IS_ADD_FORM, 1);
				request.setAttribute(TaskParams.PRINTED_STATUSES, statuses);
			} else {
				List<Task> runtimeTasks = new ArrayList<>();
				if (request.getSession().getAttribute(TaskParams.PRINTED_RUNTIME_TASKS) != null) {
					runtimeTasks = (List<Task>) request.getSession().getAttribute(TaskParams.PRINTED_RUNTIME_TASKS);
				}
				int taskId = Integer.parseInt(request.getParameter(TaskParams.TASK_ID));
				Task runtimeTask = RuntimeTasksUtils.findRuntimeTaskById(runtimeTasks, taskId);
				request.setAttribute(TaskParams.PRINTED_STATUSES, statuses);
				request.setAttribute(UtilParams.IS_ADD_FORM, 0);
				request.setAttribute(TaskParams.PRINTED_EDIT_RUNTIME_TASK, runtimeTask);
				
			}
		} catch (ValidationException e) {
			request.setAttribute(UtilParams.VALIDATION_EXCEPTION, e);
			request.setAttribute(UtilParams.IS_ADD_FORM, request.getParameter(UtilParams.IS_FROM_ADD_FORM));
			request.getSession().setAttribute(ProjectParams.PRINTED_EDIT_PROJECT, e.getEntity());
			return PageConstant.EDIT_PROJECT_PAGE;
		}
		
		return PageConstant.EDIT_RUNTIME_TASK_PAGE;
	}
}
