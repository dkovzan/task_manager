package com.kovzan.task_manager.command.impl.Task;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.entities.Task;
import com.kovzan.task_manager.exception.DAOException;
import com.kovzan.task_manager.logger.LogConstant;
import com.kovzan.task_manager.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class PrintTasksCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) {
		try {
			List<Task> tasks = TaskService.getInstance().findAllTasks();
			request.setAttribute(ParameterNameConstant.PRINTED_TASKS, tasks);
		} catch (DAOException e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
			return PageConstant.ERROR_PAGE;
		}
		logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		return PageConstant.TASKS_PAGE;
	}
}
