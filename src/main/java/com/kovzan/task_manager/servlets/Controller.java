package com.kovzan.task_manager.servlets;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.CommandEnum;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        String page = commandType.execute(req);
        req.getRequestDispatcher(page).forward(req,resp);
    }
}
