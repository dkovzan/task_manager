package com.kovzan.task_manager.command;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kovzan.task_manager.logger.LogConstant;

import static com.kovzan.task_manager.logger.Log.logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

@WebServlet("/controller")
public class Controller extends HttpServlet {

	public static final String COMMAND = "command";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		String command = req.getParameter(COMMAND);
		Command commandType = CommandEnum.valueOf(command.toUpperCase()).getCommand();
		String page;
		try {
			page = commandType.execute(req);
		} catch (SQLException e) {
			req.setAttribute(ParameterNameConstant.ERROR, e);
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
			page = PageConstant.ERROR_PAGE;
		} catch (NumberFormatException e) {
			req.setAttribute(ParameterNameConstant.ERROR, e);
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
			page = PageConstant.ERROR_PAGE;
		}
		req.getRequestDispatcher(page).forward(req,resp);
	}
}
