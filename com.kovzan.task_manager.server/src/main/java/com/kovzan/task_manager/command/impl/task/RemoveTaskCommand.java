package com.kovzan.task_manager.command.impl.task;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.CommandEnum;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.dao.impl.TaskDao;
import com.kovzan.task_manager.entity.Task;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class RemoveTaskCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		
		TaskDao taskDao = new TaskDao();
		Integer taskId = Integer.parseInt(request.getParameter(TaskParams.TASK_ID));
		Task task = new Task();
		task.setId(taskId);
		taskDao.remove(task);
		return CommandEnum.PRINT_TASKS.getCommand().execute(request);
	}
}
