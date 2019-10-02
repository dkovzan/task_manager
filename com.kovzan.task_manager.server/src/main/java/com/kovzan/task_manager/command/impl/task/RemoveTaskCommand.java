package com.kovzan.task_manager.command.impl.task;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.Commands;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.dao.DaoException;
import com.kovzan.task_manager.dao.TaskDao;
import com.kovzan.task_manager.entity.Task;

import javax.servlet.http.HttpServletRequest;

public class RemoveTaskCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) throws DaoException {
		
		TaskDao taskDao = new TaskDao();
		Integer taskId = Integer.parseInt(request.getParameter(TaskParams.TASK_ID));
		Task task = new Task();
		task.setId(taskId);
		taskDao.remove(task);
		return Commands.PRINT_TASKS.getCommand().execute(request);
	}
}
