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

@WebServlet(urlPatterns = "/add_employee")
public class AddServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/add_employee.jsp");
        requestDispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String middleName = req.getParameter("middleName");
        String position = req.getParameter("position");

        Employee employee = new Employee(firstName, lastName, middleName, position);

        try {
            EmployeeDAOImpl.getInstance().add(employee);
        } catch (DAOException e) {
            System.out.println(e.getCause()+ " " + e.getMessage());
        }
        req.setAttribute("employeeName", firstName);
        doGet(req,resp);
    }
}
