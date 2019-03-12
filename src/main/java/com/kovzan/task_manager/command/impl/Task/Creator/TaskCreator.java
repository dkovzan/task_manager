package com.kovzan.task_manager.command.impl.Task.Creator;

import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.entities.Task;
import com.kovzan.task_manager.logger.LogConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class TaskCreator {

	public static Task createTaskFromRequest(HttpServletRequest request) {

		String taskName = request.getParameter(ParameterNameConstant.TASK_NAME);
		Integer taskEstimation = Integer.parseInt(request.getParameter(ParameterNameConstant.TASK_ESTIMATE));
		String taskCreatedOn = request.getParameter(ParameterNameConstant.TASK_CREATEDON);
		String taskFinishedOn = request.getParameter(ParameterNameConstant.TASK_FINISHEDON);
		Integer taskProjectId = Integer.parseInt(request.getParameter(ParameterNameConstant.TASK_PROJECT_ID));
		Integer taskEmployeeId = Integer.parseInt(request.getParameter(ParameterNameConstant.TASK_EMPLOYEE_ID));
		Integer taskStatusId = Integer.parseInt(request.getParameter(ParameterNameConstant.TASK_STATUS_ID));
		Task task = new Task(taskName, taskEstimation, taskCreatedOn, taskFinishedOn, taskProjectId, taskEmployeeId, taskStatusId);
		logger.log(Level.INFO, LogConstant.OBJECT_CREATED + task.toString());
		return task;
	}

	public static Task createTaskWithIdFromRequest(HttpServletRequest request) {

		Task task = createTaskFromRequest(request);
		try {
			int taskId = Integer.parseInt(request.getParameter(ParameterNameConstant.TASK_ID));
			task.setId(taskId);
		} catch (Exception e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
		}
		logger.log(Level.INFO, LogConstant.OBJECT_CREATED + task.toString());
		return task;
	}
}
