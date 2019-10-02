package com.kovzan.task_manager.command.impl.project;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.impl.parameters.ProjectParams;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.dao.DaoException;
import com.kovzan.task_manager.dao.ProjectDao;
import com.kovzan.task_manager.dao.TaskDao;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.entity.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class PrintEditProjectCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) throws DaoException {
		boolean isCleanSessionNeeded = Boolean.parseBoolean(request.getParameter(UtilParams.IS_CLEAN_SESSION_NEEDED));
		if (isCleanSessionNeeded) {
			request.getSession().invalidate();
		}
		setAttributesForCurrentMode(request);
		return PageConstant.EDIT_PROJECT_PAGE;
	}
	
	private void setAttributesForCurrentMode(HttpServletRequest request) throws DaoException {
		Project projectFromRequest = ProjectUtils.createProjectFromRequest(request);
		boolean isAddProjectFrom = getEditModeByProjectId(projectFromRequest.getId());
		if (isAddProjectFrom) {
			setAttributesForAddForm(request, projectFromRequest);
		} else {
			setAttributesForEditForm(request, projectFromRequest);
		}
	}
	
	private boolean getEditModeByProjectId(int projectId) {
		if (projectId == -1) {
			return true;
		} else {
			return false;
		}
	}
	
	private void setAttributesForAddForm(HttpServletRequest request, Project buildedProject) throws DaoException {
		request.setAttribute(ProjectParams.PRINTED_EDIT_PROJECT, buildedProject);
		request.getSession().setAttribute(UtilParams.IS_ADD_FORM, true);
	}
	
	private void setAttributesForEditForm(HttpServletRequest request, Project buildedProject) throws DaoException {
		setTasksAttribute(request, buildedProject.getId());
		setProjectAttribute(request, buildedProject.getId());
		request.getSession().setAttribute(UtilParams.IS_ADD_FORM, false);
	}
	
	private void setTasksAttribute(HttpServletRequest request, int buildedProjectId) throws DaoException {
		if (request.getSession().getAttribute(TaskParams.PRINTED_RUNTIME_TASKS) == null) {
			TaskDao taskDao = new TaskDao();
			List<Task> tasks = taskDao.findTasksByProjectId(buildedProjectId);
			if (tasks != null) {
				request.getSession().setAttribute(TaskParams.PRINTED_RUNTIME_TASKS, tasks);
			}
		}
	}
	
	private void setProjectAttribute(HttpServletRequest request, int buildedProjectId) throws DaoException {
		if (request.getSession().getAttribute(ProjectParams.PRINTED_EDIT_PROJECT) == null) {
			ProjectDao projectDao = new ProjectDao();
			Project projectFromDB = projectDao.findById(buildedProjectId);
			request.getSession().setAttribute(ProjectParams.PRINTED_EDIT_PROJECT, projectFromDB);
		}
	}
}
