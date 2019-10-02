package com.kovzan.task_manager.command;

import com.kovzan.task_manager.dao.DaoException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
	String execute(HttpServletRequest request) throws DaoException;
}
