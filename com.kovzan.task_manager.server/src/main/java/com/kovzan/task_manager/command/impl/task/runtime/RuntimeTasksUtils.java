package com.kovzan.task_manager.command.impl.task.runtime;

import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.entity.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class RuntimeTasksUtils {

	public static List<Task> getRuntimeTasks(HttpServletRequest request) {
		List<Task> runtimeTasks = new ArrayList<>();
		if (request.getSession().getAttribute(TaskParams.PRINTED_RUNTIME_TASKS) != null)
			runtimeTasks = (List<Task>) request.getSession().getAttribute(TaskParams.PRINTED_RUNTIME_TASKS);
		return runtimeTasks;
	}
	
	static Task findRuntimeTaskById(List<Task> runtimeTasks, int id) {
		Task runtimeTask = null;
		for (Task task : runtimeTasks) {
			if (task.getId().equals(id)) {
				runtimeTask = task;
				break;
			}
		}
		return runtimeTask;
	}
	
}
