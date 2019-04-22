package com.kovzan.task_manager.command;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
	String execute(HttpServletRequest request) throws SQLException;
}
