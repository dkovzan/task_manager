package com.kovzan.task_manager.command;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.logger.LogConstant;

import static com.kovzan.task_manager.logger.Log.logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

@WebServlet("/controller")
public class Controller extends HttpServlet {

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
		String command = request.getParameter(COMMAND);
		Command commandType = CommandEnum.valueOf(command.toUpperCase()).getCommand();
		String page;
		try {
			page = commandType.execute(request);
		} catch (SQLException e) {
			request.setAttribute(UtilParams.ERROR, SQL_ERROR_MESSAGE);
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
			page = PageConstant.ERROR_PAGE;
		}catch (Exception e) {
			request.setAttribute(UtilParams.ERROR, e);
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
			page = PageConstant.ERROR_PAGE;
		}
		request.getRequestDispatcher(page).forward(request,response);
	}
}
