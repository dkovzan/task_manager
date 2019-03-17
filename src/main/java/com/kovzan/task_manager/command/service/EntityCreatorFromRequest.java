package com.kovzan.task_manager.command.service;

import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.entities.Employee;
import com.kovzan.task_manager.entities.Project;
import com.kovzan.task_manager.entities.Task;
import com.kovzan.task_manager.logger.LogConstant;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class EntityCreatorFromRequest {

	public static Task createTaskFromRequest(HttpServletRequest request) {
		Integer taskId = null;
		if (request.getParameter(ParameterNameConstant.TASK_ID) != null) {
			taskId = Integer.parseInt(request.getParameter(ParameterNameConstant.TASK_ID));
		}
		String taskName = request.getParameter(ParameterNameConstant.TASK_NAME);
		Integer taskEstimation = Integer.parseInt(request.getParameter(ParameterNameConstant.TASK_ESTIMATE));
		LocalDate taskCreatedOn = LocalDate.parse(request.getParameter(ParameterNameConstant.TASK_CREATEDON));
		LocalDate taskFinishedOn = LocalDate.parse(request.getParameter(ParameterNameConstant.TASK_FINISHEDON));
		Integer taskProjectId = Integer.parseInt(request.getParameter(ParameterNameConstant.TASK_PROJECT_ID));
		Integer taskEmployeeId = Integer.parseInt(request.getParameter(ParameterNameConstant.TASK_EMPLOYEE_ID));
		Integer taskStatusId = Integer.parseInt(request.getParameter(ParameterNameConstant.TASK_STATUS_ID));
		Task task = new Task(taskId, taskName, taskEstimation, taskCreatedOn, taskFinishedOn, taskProjectId, taskEmployeeId, taskStatusId);
		logger.log(Level.INFO, LogConstant.OBJECT_CREATED + task.toString());
		return task;
	}

	public static Project createProjectFromRequest(HttpServletRequest request) {
		Integer projectId = null;
		if (request.getParameter(ParameterNameConstant.PROJECT_ID) != null) {
			projectId = Integer.parseInt(request.getParameter(ParameterNameConstant.PROJECT_ID));
		}
		String projectName = request.getParameter(ParameterNameConstant.PROJECT_NAME);
		String projectShortName = request.getParameter(ParameterNameConstant.PROJECT_SHORTNAME);
		String projectDescription = request.getParameter(ParameterNameConstant.PROJECT_DESCRIPTION);
		Project project = new Project(projectId, projectName, projectShortName, projectDescription);
		logger.log(Level.INFO, LogConstant.OBJECT_CREATED + project.toString());
		return project;
	}

	public static Employee createEmployeeFromRequest(HttpServletRequest request) {
		Integer employeeId = null;
		if (request.getParameter(ParameterNameConstant.EMPLOYEE_ID) != null) {
			employeeId = Integer.parseInt(request.getParameter(ParameterNameConstant.EMPLOYEE_ID));
		}
		String employeeFirstName = request.getParameter(ParameterNameConstant.EMPLOYEE_FIRSTNAME);
		String employeeLastName = request.getParameter(ParameterNameConstant.EMPLOYEE_LASTNAME);
		String employeeMiddleName = request.getParameter(ParameterNameConstant.EMPLOYEE_MIDDLENAME);
		String employeePosition = request.getParameter(ParameterNameConstant.EMPLOYEE_POSITION);
		Employee employee = new Employee(employeeId, employeeFirstName, employeeLastName, employeeMiddleName, employeePosition);
		logger.log(Level.INFO, LogConstant.OBJECT_CREATED + employee.toString());
		return employee;
	}
}
