package com.kovzan.task_manager.command.impl.task;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.dao.DaoException;
import com.kovzan.task_manager.logger.LogConstant;
import com.kovzan.task_manager.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class PrintTasksCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) {

		try {
			List<Task> tasks = TaskService.getInstance().findAllTasks();
			request.setAttribute(ParameterNameConstant.PRINTED_TASKS, tasks);
		} catch (DaoException e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
			return PageConstant.ERROR_PAGE;
		}
		logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		return PageConstant.TASKS_PAGE;
	}
}
