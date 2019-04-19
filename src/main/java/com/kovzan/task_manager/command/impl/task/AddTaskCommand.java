package com.kovzan.task_manager.command.impl.task;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.command.ValidationException;
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

public class AddTaskCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) throws SQLException {

		Task taskFromRequest;
		try {
			taskFromRequest = TaskUtils.buildTask(request);
		} catch (ValidationException e) {
			
			List<Project> projects = ProjectService.findAllProjects();
			List<Employee> employees = EmployeeService.findAllEmployees();
			List<TaskStatus> statuses = Arrays.asList(TaskStatus.values());

			request.setAttribute(ParameterNameConstant.PRINTED_PROJECTS, projects);
			request.setAttribute(ParameterNameConstant.PRINTED_EMPLOYEES, employees);
			request.setAttribute(ParameterNameConstant.PRINTED_STATUSES, statuses);
			
			request.setAttribute(ParameterNameConstant.VALIDATION_EXCEPTION, e);
			request.setAttribute(ParameterNameConstant.IS_ADD_FORM, 1);
			return PageConstant.EDIT_TASK_PAGE;
		}
		TaskService.addTask(taskFromRequest);
		List<Task> tasks = TaskService.findAllTasks();
		request.setAttribute(ParameterNameConstant.PRINTED_TASKS, tasks);
		logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		return PageConstant.TASKS_PAGE;
	}
}
