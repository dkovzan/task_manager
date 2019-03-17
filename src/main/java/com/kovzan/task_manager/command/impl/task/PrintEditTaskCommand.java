package com.kovzan.task_manager.command.impl.task;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.entity.Employee;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.entity.Status;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.exception.DAOException;
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

public class PrintEditTaskCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) {

		int isAddForm = Integer.parseInt(request.getParameter(ParameterNameConstant.IS_ADD_FORM));
		if (isAddForm == 1) {
			request.setAttribute(ParameterNameConstant.IS_ADD_FORM, 1);
			try {
				List<Project> projects = ProjectService.getInstance().findAllProjects();
				List<Employee> employees = EmployeeService.getInstance().findAllEmployees();
				List<Status> statuses = StatusService.getInstance().findAllStatuses();

				request.setAttribute(ParameterNameConstant.PRINTED_PROJECTS, projects);
				request.setAttribute(ParameterNameConstant.PRINTED_EMPLOYEES, employees);
				request.setAttribute(ParameterNameConstant.PRINTED_STATUSES, statuses);
			} catch (SQLException e) {
				logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
				return PageConstant.ERROR_PAGE;
			}
		} else {
			Task task;
			try {
				int taskId = Integer.parseInt(request.getParameter(ParameterNameConstant.TASK_ID));
				task = TaskService.findTaskById(taskId);
				request.setAttribute(ParameterNameConstant.PRINTED_EDIT_TASK, task);
				request.setAttribute(ParameterNameConstant.IS_ADD_FORM, 0);

				List<Project> projects = ProjectService.getInstance().findAllProjects();
				List<Employee> employees = EmployeeService.findAllEmployees();
				List<Status> statuses = StatusService.findAllStatuses();

				request.setAttribute(ParameterNameConstant.PRINTED_PROJECTS, projects);
				request.setAttribute(ParameterNameConstant.PRINTED_EMPLOYEES, employees);
				request.setAttribute(ParameterNameConstant.PRINTED_STATUSES, statuses);
			} catch (DAOException e) {
				logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
				return PageConstant.ERROR_PAGE;
			}
		}
		logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		return PageConstant.EDIT_TASK_PAGE;
	}
}
