package com.kovzan.task_manager.command.impl.project;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.impl.parameters.ProjectParams;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
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
		if (isCleanSessionNeeded) {
			request.getSession().invalidate();
		}
		setAttributesForCurrentMode(request);
		return PageConstant.EDIT_PROJECT_PAGE;
	}
	
	private void setAttributesForCurrentMode(HttpServletRequest request) throws SQLException {
		Project projectFromRequest = getProjectFromUrlOrFromSession(request);
		boolean isAddProjectFrom = getEditModeByProjectId(projectFromRequest.getId());
		if (isAddProjectFrom) {
			setAttributesForAddForm(request, projectFromRequest);
		} else {
			setAttributesForEditForm(request, projectFromRequest);
		}
	}

	private Project getProjectFromUrlOrFromSession(HttpServletRequest request) {
		if (request.getSession().getAttribute(ProjectParams.PRINTED_EDIT_PROJECT) != null) {
			return (Project) request.getSession().getAttribute(ProjectParams.PRINTED_EDIT_PROJECT);
		} else {
			return ProjectUtils.createProjectFromRequest(request);
		}
	}
	
	private boolean getEditModeByProjectId(int projectId) {
		if (projectId == -1) {
			return true;
		} else {
			return false;
		}
	}
	
	private void setAttributesForAddForm(HttpServletRequest request, Project buildedProject) throws SQLException {
		request.setAttribute(ProjectParams.PRINTED_EDIT_PROJECT, buildedProject);
		request.getSession().setAttribute(UtilParams.IS_ADD_FORM, true);
	}
	
	private void setAttributesForEditForm(HttpServletRequest request, Project buildedProject) throws SQLException {
		setTasksAttribute(request, buildedProject.getId());
		setProjectAttribute(request, buildedProject.getId());
		request.getSession().setAttribute(UtilParams.IS_ADD_FORM, false);
	}
	
	private void setTasksAttribute(HttpServletRequest request, int buildedProjectId) throws SQLException {
		if (request.getSession().getAttribute(TaskParams.PRINTED_RUNTIME_TASKS) == null) {
			TaskDao taskDao = new TaskDao();
			List<Task> tasks = taskDao.findTasksByProjectId(buildedProjectId);
			if (tasks != null) {
				request.getSession().setAttribute(TaskParams.PRINTED_RUNTIME_TASKS, tasks);
			}
		}
	}
	
	private void setProjectAttribute(HttpServletRequest request, int buildedProjectId) throws SQLException {
		if (request.getSession().getAttribute(ProjectParams.PRINTED_EDIT_PROJECT) == null) {
			ProjectDao projectDao = new ProjectDao();
			Project projectFromDB = projectDao.findById(buildedProjectId);
			request.getSession().setAttribute(ProjectParams.PRINTED_EDIT_PROJECT, projectFromDB);
		}
	}
}
