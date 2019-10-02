package com.kovzan.task_manager.command.impl.task;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.kovzan.task_manager.command.ValidationException;
import com.kovzan.task_manager.command.impl.parameters.EmployeeParams;
import com.kovzan.task_manager.command.impl.parameters.ProjectParams;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.dao.DaoException;
import com.kovzan.task_manager.dao.EmployeeDao;
import com.kovzan.task_manager.dao.ProjectDao;
import com.kovzan.task_manager.entity.Employee;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.entity.TaskStatus;

public class TaskUtils {

	private static final String INCORRECT_DATA_MESSAGE = "Incorrect data are entered.";

	public static Task buildTask(HttpServletRequest request) throws ValidationException, DaoException {
		
		Task task = new Task();
		HashMap<String, String> invalidFields = new HashMap<>();
		
		Integer id;
		if (request.getParameter(TaskParams.TASK_ID) != null) {
			id = Integer.parseInt(request.getParameter(TaskParams.TASK_ID));
			task.setId(id);
		}
		
		String name = request.getParameter(TaskParams.TASK_NAME).trim();
		if (TaskValidator.isTaskNameValid(name)) {
			task.setName(name);
		} else {
			invalidFields.put(TaskParams.TASK_NAME, name);
		}
		
		String work = request.getParameter(TaskParams.TASK_WORK).trim();
		if (TaskValidator.isTaskWorkValid(work)) {
			task.setWork(Integer.parseInt(work));
		} else {
			invalidFields.put(TaskParams.TASK_WORK, work);
		}
		
		String beginDate = request.getParameter(TaskParams.TASK_BEGINDATE).trim();
		boolean isBeginDateValid = true;
		if (TaskValidator.isTaskDateValid(beginDate) && TaskValidator.isTaskDateInRange(beginDate)) {
			task.setBeginDate(LocalDate.parse(beginDate));
		} else {
			invalidFields.put(TaskParams.TASK_BEGINDATE, beginDate);
			isBeginDateValid = false;
		}
		
		String endDate = request.getParameter(TaskParams.TASK_ENDDATE).trim();
		boolean isEndDateValid = true;
		if (TaskValidator.isTaskDateValid(endDate) && TaskValidator.isTaskDateInRange(endDate)) {
			task.setEndDate(LocalDate.parse(endDate));
		} else {
			invalidFields.put(TaskParams.TASK_ENDDATE, endDate);
			isEndDateValid = false;
		}
		if (isBeginDateValid && isEndDateValid) {
			if (!TaskValidator.areTaskDatesValid(LocalDate.parse(beginDate), LocalDate.parse(endDate))) {
				task.setBeginDate(null);
				invalidFields.put(TaskParams.TASK_BEGINDATE, beginDate);
				invalidFields.put(TaskParams.TASK_INVALID_DATE_RANGE, beginDate);
			}
		}
		
		Integer projectId = null;
		if (request.getParameter(TaskParams.TASK_PROJECT_ID) != null) {
			projectId = Integer.parseInt(request.getParameter(TaskParams.TASK_PROJECT_ID));
			task.setProjectId(projectId);
		} else {
			invalidFields.put(TaskParams.TASK_PROJECT_ID, String.valueOf(projectId));
		}
		
		Integer employeeId = null;
		if (request.getParameter(TaskParams.TASK_EMPLOYEE_ID) != null) {
			employeeId = Integer.parseInt(request.getParameter(TaskParams.TASK_EMPLOYEE_ID));
			task.setEmployeeId(employeeId);
			task.setEmployeeFullName(getEmployeeFullNameById(employeeId));
		} else {
			invalidFields.put(TaskParams.TASK_EMPLOYEE_ID, String.valueOf(employeeId));
		}
		
		TaskStatus taskStatus = null;
		if (request.getParameter(TaskParams.TASK_STATUS) != null) {
			taskStatus = TaskStatus.valueOf(request.getParameter(TaskParams.TASK_STATUS));
			task.setStatus(taskStatus);
		} else {
			invalidFields.put(TaskParams.TASK_STATUS, String.valueOf(taskStatus));
		}
		
		if(!invalidFields.isEmpty()) {
			throw new ValidationException(INCORRECT_DATA_MESSAGE, task, invalidFields);
		}
		
		return task;
	}
	
	public static void setProjectsEmployeesTaskStatusesAttributes(HttpServletRequest request) throws DaoException {
		setProjectsAttribute(request);
		setEmployeesAttribute(request);
		setTaskStatusesAttribute(request);
	}
	
	public static void setEmployeesAndTaskStatusesAttributes(HttpServletRequest request) throws DaoException {
		setEmployeesAttribute(request);
		setTaskStatusesAttribute(request);
	}
	
	public static void setTaskStatusesAttribute(HttpServletRequest request) {
		request.setAttribute(TaskParams.PRINTED_STATUSES, Arrays.asList(TaskStatus.values()));
	}
	
	private static String getEmployeeFullNameById(int employeeId) throws DaoException {
		EmployeeDao employeeDao = new EmployeeDao();
		Employee employee = employeeDao.findById(employeeId);
		return employee.getFirstName() + " " + employee.getLastName();
	}
	
	private static void setEmployeesAttribute(HttpServletRequest request) throws DaoException {
		EmployeeDao employeeDao = new EmployeeDao();
		request.setAttribute(EmployeeParams.PRINTED_EMPLOYEES, employeeDao.findAll());
	}
	
	private static void setProjectsAttribute(HttpServletRequest request) throws DaoException {
		ProjectDao projectDao = new ProjectDao();
		request.setAttribute(ProjectParams.PRINTED_PROJECTS, projectDao.findAll());
	}

}
