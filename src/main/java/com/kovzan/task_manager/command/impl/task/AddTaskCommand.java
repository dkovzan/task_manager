package com.kovzan.task_manager.command.impl.task;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.command.service.EntityCreatorFromRequest;
import com.kovzan.task_manager.entity.Employee;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.entity.TaskStatusesEnum;
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
	public String execute(HttpServletRequest request) {

		try {
			Task taskFromRequest = EntityCreatorFromRequest.createTaskFromRequest(request);
			if(TaskService.getInstance().validateTask(taskFromRequest)) {
				TaskService.addTask(taskFromRequest);
				List<Task> tasks = TaskService.getInstance().findAllTasks();
				request.setAttribute(ParameterNameConstant.PRINTED_TASKS, tasks);
			} else {
				Task taskWithValidFields = TaskService.getInstance().getTaskWithValidFields(taskFromRequest);
				request.setAttribute(ParameterNameConstant.PRINTED_EDIT_TASK, taskWithValidFields);

				List<Project> projects = ProjectService.getInstance().findAllProjects();
				List<Employee> employees = EmployeeService.getInstance().findAllEmployees();
				List<TaskStatusesEnum> statuses = Arrays.asList(TaskStatusesEnum.values());

				request.setAttribute(ParameterNameConstant.PRINTED_PROJECTS, projects);
				request.setAttribute(ParameterNameConstant.PRINTED_EMPLOYEES, employees);
				request.setAttribute(ParameterNameConstant.PRINTED_STATUSES, statuses);

				request.setAttribute(ParameterNameConstant.ERROR, "Incorrect data are entered.");

				request.setAttribute(ParameterNameConstant.IS_ADD_FORM, 1);
				return PageConstant.EDIT_TASK_PAGE;
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
			return PageConstant.ERROR_PAGE;
		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
			return PageConstant.ERROR_PAGE;
		}
		logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		return PageConstant.TASKS_PAGE;
	}
}
