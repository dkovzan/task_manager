package com.kovzan.task_manager.servlets;

import com.kovzan.task_manager.dao.impl.EmployeeDAOImpl;
import com.kovzan.task_manager.entities.Employee;
import com.kovzan.task_manager.exception.DAOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/employees")
public class ListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        try {
            List<Employee> employees = EmployeeDAOImpl.getInstance().findAll();
            req.setAttribute("employees", employees);
        } catch (DAOException e) {
            System.out.println(e.getCause() + " " + e.getMessage());
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/employees.jsp");
        requestDispatcher.forward(req,resp);
    }
}
