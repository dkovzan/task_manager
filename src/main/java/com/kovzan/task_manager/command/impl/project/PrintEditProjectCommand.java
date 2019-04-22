package com.kovzan.task_manager.command.impl.project;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.impl.parameters.ProjectParams;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.logger.LogConstant;
import com.kovzan.task_manager.service.ProjectService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class PrintEditProjectCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) throws SQLException {

		int isAddForm = Integer.parseInt(request.getParameter(UtilParams.IS_ADD_FORM));
		if (isAddForm == 1) {
			request.setAttribute(UtilParams.IS_ADD_FORM, 1);
		} else {
			int projectId = Integer.parseInt(request.getParameter(ProjectParams.PROJECT_ID));
			Project project = ProjectService.findProjectById(projectId);
			request.setAttribute(ProjectParams.PRINTED_EDIT_PROJECT, project);
			request.setAttribute(UtilParams.IS_ADD_FORM, 0);
		}
		logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		return PageConstant.EDIT_PROJECT_PAGE;
	}
}
