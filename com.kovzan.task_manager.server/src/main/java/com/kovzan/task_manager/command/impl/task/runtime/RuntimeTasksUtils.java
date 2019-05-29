package com.kovzan.task_manager.command.impl.task.runtime;

import com.kovzan.task_manager.command.impl.parameters.EmployeeParams;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.command.impl.task.TaskUtils;
import com.kovzan.task_manager.dao.impl.EmployeeDao;
import com.kovzan.task_manager.entity.Task;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RuntimeTasksUtils {
	
	public static Task findRuntimeTaskById(List<Task> runtimeTasks, int id) {
		Task runtimeTask = null;
		for (Task task : runtimeTasks) {
			if (task.getId().equals(id)) {
				runtimeTask = task;
				break;
			}
		}
		return runtimeTask;
	}
	
	public static List<Task> removeRuntimeTask(List<Task> runtimeTasks, int runtimeTaskId) {
		for (Task runtimeTask : runtimeTasks) {
			if (runtimeTask.getId().equals(runtimeTaskId)) {
				runtimeTasks.remove(runtimeTask);
				break;
			}
		}
		return runtimeTasks;
	}
	
	public static List<Task> addRuntimeTask(List<Task> runtimeTasks, Task taskFromRequest) {
		runtimeTasks.add(taskFromRequest);
		return runtimeTasks;
	}
	
	public static List<Task> updateRuntimeTask(List<Task> runtimeTasks, Task taskFromRequest) {
		for (int i = 0; i < runtimeTasks.size(); i++) {
			if (runtimeTasks.get(i).getId().equals(taskFromRequest.getId())) {
				runtimeTasks.remove(i);
				runtimeTasks.add(i, taskFromRequest);
				break;
			}
		}
		return runtimeTasks;
	}
	
	public static List<Task> getRuntimeTasks(HttpServletRequest request) {
		List<Task> runtimeTasks = new ArrayList<>();
		if (request.getSession().getAttribute(TaskParams.PRINTED_RUNTIME_TASKS) != null) {
			runtimeTasks = (List<Task>) request.getSession().getAttribute(TaskParams.PRINTED_RUNTIME_TASKS);
		}
		return runtimeTasks;
	}
	
	public static void setEmployeesTaskStatusesAttributes(HttpServletRequest request) throws SQLException {
		EmployeeDao employeeDao = new EmployeeDao();
		request.setAttribute(EmployeeParams.PRINTED_EMPLOYEES, employeeDao.findAll());
		TaskUtils.setTasksStatusesAttribute(request);
	}
}
