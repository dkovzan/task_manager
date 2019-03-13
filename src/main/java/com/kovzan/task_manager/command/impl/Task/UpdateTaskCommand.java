package com.kovzan.task_manager.command.impl.Task;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.command.impl.Task.Creator.TaskCreator;
import com.kovzan.task_manager.entities.Employee;
import com.kovzan.task_manager.entities.Project;
import com.kovzan.task_manager.entities.Status;
import com.kovzan.task_manager.entities.Task;
import com.kovzan.task_manager.logger.LogConstant;
import com.kovzan.task_manager.service.EmployeeService;
import com.kovzan.task_manager.service.ProjectService;
import com.kovzan.task_manager.service.StatusService;
import com.kovzan.task_manager.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class UpdateTaskCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) {
		try {
			Task taskWithIdFromRequest = TaskCreator.createTaskWithIdFromRequest(request);
			if(TaskService.getInstance().validateTask(taskWithIdFromRequest)) {
				TaskService.updateTask(taskWithIdFromRequest);
				List<Task> tasks = TaskService.getInstance().findAllTasks();
				request.setAttribute(ParameterNameConstant.PRINTED_TASKS, tasks);
			} else {
				Task taskWithValidFields = TaskService.getInstance().getTaskWithValidFields(taskWithIdFromRequest);
				request.setAttribute(ParameterNameConstant.PRINTED_EDIT_TASK, taskWithValidFields);

				List<Project> projects = ProjectService.getInstance().findAllProjects();
				List<Employee> employees = EmployeeService.getInstance().findAllEmployees();
				List<Status> statuses = StatusService.getInstance().findAllStatuses();

				request.setAttribute(ParameterNameConstant.PRINTED_PROJECTS, projects);
				request.setAttribute(ParameterNameConstant.PRINTED_EMPLOYEES, employees);
				request.setAttribute(ParameterNameConstant.PRINTED_STATUSES, statuses);

				request.setAttribute(ParameterNameConstant.ERROR, "Incorrect dates are set.");
				request.setAttribute(ParameterNameConstant.IS_ADD_FORM, 0);
				return PageConstant.EDIT_TASK_PAGE;
			}

		} catch (SQLException e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
			return PageConstant.ERROR_PAGE;
		}
		logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		return PageConstant.TASKS_PAGE;
	}
}
