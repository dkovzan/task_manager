package com.kovzan.task_manager.command.impl.Employee;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.entities.Employee;
import com.kovzan.task_manager.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class PrintEmployeesCommand implements Command{

    @Override
    public String execute(HttpServletRequest request) {
        List<Employee> employees;
        try {
            employees = EmployeeService.findAllEmployees();
        } catch (SQLException e) {
            System.out.println(e);
            return PageConstant.ERROR_PAGE;
        }
        request.setAttribute(ParameterNameConstant.PRINTED_EMPLOYEES, employees);
        return PageConstant.EMPLOYEES_PAGE;
    }
}
