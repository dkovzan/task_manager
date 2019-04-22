package com.kovzan.task_manager.command.impl.task;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.logger.LogConstant;
import com.kovzan.task_manager.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class RemoveTaskCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) throws SQLException {

		Integer taskId = Integer.parseInt(request.getParameter(TaskParams.TASK_ID));
		Task task = new Task();
		task.setId(taskId);
		TaskService.removeTask(task);
		List<Task> tasks = TaskService.findAllTasks();
		request.setAttribute(TaskParams.PRINTED_TASKS, tasks);
		logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		return PageConstant.TASKS_PAGE;
	}
}
