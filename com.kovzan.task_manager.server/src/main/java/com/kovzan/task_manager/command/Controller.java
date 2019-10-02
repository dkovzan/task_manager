package com.kovzan.task_manager.command;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.dao.DaoException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/controller")
public class Controller extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(Controller.class.getName());
	
	private static final String COMMAND = "command";
	
	private static final String SQL_ERROR_MESSAGE = "Oops... Database error has occured.";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		logger.info(getFullPath(request));
		String command = request.getParameter(COMMAND);
		if (command != null) {
			String page;
			if (Commands.contains(command)) {
				try {
					Command commandType = Commands.valueOf(command.toUpperCase()).getCommand();
					page = commandType.execute(request);
				} catch (DaoException e) {
					request.setAttribute(UtilParams.ERROR, SQL_ERROR_MESSAGE);
					logger.log(Level.SEVERE, "Database error has occured: ", e);
					page = PageConstant.ERROR_PAGE;
				} catch (Exception e) {
					request.setAttribute(UtilParams.ERROR, e);
					logger.log(Level.SEVERE, "Unexpected error has occured: ", e);
					page = PageConstant.ERROR_PAGE;
				}
				request.getRequestDispatcher(page).forward(request, response);
			} else {
				response.sendError(404, "Command cannot be processed");
			}
		} else {
			response.sendError(404, "Command should be provided");
		}
	}
	
	private String getFullPath(HttpServletRequest request) {
		return request.getRequestURI() + (request.getQueryString() != null ? ("?" + request.getQueryString()) : "");
	}
	
}
