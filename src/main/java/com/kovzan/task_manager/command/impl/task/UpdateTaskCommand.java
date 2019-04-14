package com.kovzan.task_manager.command.impl.task;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.command.service.EntityCreatorFromRequest;
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

public class UpdateTaskCommand implements Command {
	
	private static final String INCORRECT_DATA = "Incorrect data is entered.";

	@Override
	public String execute(HttpServletRequest request) throws SQLException {

		Task taskWithIdFromRequest = EntityCreatorFromRequest.createTaskFromRequest(request);
		if (TaskService.validateTask(taskWithIdFromRequest)) {
			TaskService.updateTask(taskWithIdFromRequest);
			List<Task> tasks = TaskService.findAllTasks();
			request.setAttribute(ParameterNameConstant.PRINTED_TASKS, tasks);
		} else {
			Task taskWithValidFields = TaskService.getTaskWithValidFields(taskWithIdFromRequest);
			request.setAttribute(ParameterNameConstant.PRINTED_EDIT_TASK, taskWithValidFields);

			List<Project> projects = ProjectService.findAllProjects();
			List<Employee> employees = EmployeeService.findAllEmployees();
			List<TaskStatus> statuses = Arrays.asList(TaskStatus.values());

			request.setAttribute(ParameterNameConstant.PRINTED_PROJECTS, projects);
			request.setAttribute(ParameterNameConstant.PRINTED_EMPLOYEES, employees);
			request.setAttribute(ParameterNameConstant.PRINTED_STATUSES, statuses);

			request.setAttribute(ParameterNameConstant.ERROR, INCORRECT_DATA);
			request.setAttribute(ParameterNameConstant.IS_ADD_FORM, 0);
			return PageConstant.EDIT_TASK_PAGE;
		}
		logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		return PageConstant.TASKS_PAGE;
	}
}
