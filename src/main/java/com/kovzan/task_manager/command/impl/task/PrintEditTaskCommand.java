package com.kovzan.task_manager.command.impl.task;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.impl.parameters.EmployeeParams;
import com.kovzan.task_manager.command.impl.parameters.ProjectParams;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.entity.Employee;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.entity.TaskStatus;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.logger.LogConstant;
import com.kovzan.task_manager.service.EmployeeService;
import com.kovzan.task_manager.service.ProjectService;
import com.kovzan.task_manager.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class PrintEditTaskCommand implements Command {
	
	private static final String TASK_CANNOT_BE_ADDED = "Task cannot be added when projects or employees list is empty.";

	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		
		if (!canTaskBeCreated()) {
			List<Task> tasks = TaskService.findAllTasks();
			request.setAttribute(UtilParams.ERROR, TASK_CANNOT_BE_ADDED);
			request.setAttribute(TaskParams.PRINTED_TASKS, tasks);
			return PageConstant.TASKS_PAGE;
		} else {
			int isAddForm = Integer.parseInt(request.getParameter(UtilParams.IS_ADD_FORM));
			if (isAddForm == 1) {
				request.setAttribute(UtilParams.IS_ADD_FORM, 1);
				List<Project> projects = ProjectService.findAllProjects();
				List<Employee> employees = EmployeeService.findAllEmployees();
				List<TaskStatus> statuses = Arrays.asList(TaskStatus.values());
				
				request.setAttribute(ProjectParams.PRINTED_PROJECTS, projects);
				request.setAttribute(EmployeeParams.PRINTED_EMPLOYEES, employees);
				request.setAttribute(TaskParams.PRINTED_STATUSES, statuses);
			} else {
				Task task;
				int taskId = Integer.parseInt(request.getParameter(TaskParams.TASK_ID));
				task = TaskService.findTaskById(taskId);
				request.setAttribute(TaskParams.PRINTED_EDIT_TASK, task);
				request.setAttribute(UtilParams.IS_ADD_FORM, 0);
				
				List<Project> projects = ProjectService.findAllProjects();
				List<Employee> employees = EmployeeService.findAllEmployees();
				List<TaskStatus> statuses = Arrays.asList(TaskStatus.values());
				
				request.setAttribute(ProjectParams.PRINTED_PROJECTS, projects);
				request.setAttribute(EmployeeParams.PRINTED_EMPLOYEES, employees);
				request.setAttribute(TaskParams.PRINTED_STATUSES, statuses);
			}
			logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			return PageConstant.EDIT_TASK_PAGE;
		}
	}
	
	private static boolean canTaskBeCreated() throws SQLException {
		if (ProjectService.findAllProjects() == null || EmployeeService.findAllEmployees() == null) {
			return false;
		} else {
			return !ProjectService.findAllProjects().isEmpty() && !EmployeeService.findAllEmployees().isEmpty();
		}
	}
}
