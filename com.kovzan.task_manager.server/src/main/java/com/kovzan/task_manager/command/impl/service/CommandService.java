package com.kovzan.task_manager.command.impl.service;

import com.kovzan.task_manager.command.impl.parameters.UtilParams;

import javax.servlet.http.HttpServletRequest;

public class CommandService {
	
	public static void cleanSession(HttpServletRequest request) {
		request.getSession().invalidate();
	}
	
	public static void setEditProjectModeByProjectId(HttpServletRequest request, int projectId) {
		if (projectId < 0) {
			request.setAttribute(UtilParams.IS_ADD_FORM, 0);
		} else {
			request.setAttribute(UtilParams.IS_ADD_FORM, 1);
		}
	}
}
