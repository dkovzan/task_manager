package com.kovzan.task_manager.command.impl.project;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.impl.parameters.ProjectParams;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.command.impl.service.CommandService;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.logger.LogConstant;
import com.kovzan.task_manager.service.ProjectService;
import com.kovzan.task_manager.service.TaskService;

import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class PrintEditProjectCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) throws SQLException {

		int isAddForm = Integer.parseInt(request.getParameter(UtilParams.IS_ADD_FORM));
		boolean isCleanSessionNeeded = Boolean.parseBoolean(request.getParameter(UtilParams.IS_CLEAN_SESSION_NEEDED));
		if (isAddForm == 1) {
			cleanSession(isCleanSessionNeeded, request);
			request.setAttribute(UtilParams.IS_ADD_FORM, 1);
		} else {
			cleanSession(isCleanSessionNeeded, request);
			int projectId = Integer.parseInt(request.getParameter(ProjectParams.PROJECT_ID));
			if (request.getSession().getAttribute(TaskParams.PRINTED_RUNTIME_TASKS) == null) {
				List<Task> tasks = TaskService.findTasksByProjectId(projectId);
				if (tasks != null) {
					request.setAttribute(TaskParams.PRINTED_RUNTIME_TASKS, tasks);
				}
			}
			if (request.getSession().getAttribute(ProjectParams.PRINTED_EDIT_PROJECT) == null) {
				Project project = ProjectService.findProjectById(projectId);
				request.setAttribute(ProjectParams.PRINTED_EDIT_PROJECT, project);
			}
			request.setAttribute(UtilParams.IS_ADD_FORM, 0);
		}
		logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		return PageConstant.EDIT_PROJECT_PAGE;
	}
	
	private void cleanSession(boolean isCleanSessionNeeded, HttpServletRequest request) {
		if (isCleanSessionNeeded) {
			CommandService.cleanSession(request);
		}
	}
}
