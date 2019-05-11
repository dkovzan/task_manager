package com.kovzan.task_manager.command.impl.project;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.impl.parameters.ProjectParams;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.command.impl.service.CommandService;
import com.kovzan.task_manager.dao.impl.ProjectDao;
import com.kovzan.task_manager.dao.impl.TaskDao;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.entity.Task;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class PrintEditProjectCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) throws SQLException {

		boolean isCleanSessionNeeded = Boolean.parseBoolean(request.getParameter(UtilParams.IS_CLEAN_SESSION_NEEDED));
		cleanSession(isCleanSessionNeeded, request);
		Project projectFromRequest = ProjectUtils.createProjectFromRequest(request);
		int currentMode = getEditMode(projectFromRequest.getId());
		setAttributesForCurrentMode(request, currentMode, projectFromRequest);
		return PageConstant.EDIT_PROJECT_PAGE;
	}
	
	private void cleanSession(boolean isCleanSessionNeeded, HttpServletRequest request) {
		if (isCleanSessionNeeded) {
			CommandService.cleanSession(request);
		}
	}
	
	private int getEditMode(int projectId) {
		if (projectId == -1) {
			return 1;
		} else {
			return 0;
		}
	}
	
	private void setAttributesForCurrentMode(HttpServletRequest request, int mode, Project project) throws SQLException {
		if (mode == 1) {
			request.setAttribute(ProjectParams.PRINTED_EDIT_PROJECT, project);
			request.getSession().setAttribute(UtilParams.IS_ADD_FORM, 1);
		} else {
			
			if (request.getSession().getAttribute(TaskParams.PRINTED_RUNTIME_TASKS) == null) {
				TaskDao taskDao = new TaskDao();
				List<Task> tasks = taskDao.findTasksByProjectId(project.getId());
				if (tasks != null) {
					request.getSession().setAttribute(TaskParams.PRINTED_RUNTIME_TASKS, tasks);
				}
			}
			if (request.getSession().getAttribute(ProjectParams.PRINTED_EDIT_PROJECT) == null) {
				ProjectDao projectDao = new ProjectDao();
				Project projectFromDB = projectDao.findById(project.getId());
				request.getSession().setAttribute(ProjectParams.PRINTED_EDIT_PROJECT, projectFromDB);
			}
			request.getSession().setAttribute(UtilParams.IS_ADD_FORM, 0);
		}
	}
}
