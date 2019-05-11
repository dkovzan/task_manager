package com.kovzan.task_manager.command.impl.task;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.dao.impl.TaskDao;
import com.kovzan.task_manager.entity.Task;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.List;

public class PrintTasksCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		
		TaskDao taskDao = new TaskDao();
		List<Task> tasks = taskDao.findAll();
		request.setAttribute(TaskParams.PRINTED_TASKS, tasks);
		return PageConstant.TASKS_PAGE;
	}
}
