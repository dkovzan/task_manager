package com.kovzan.task_manager.command.impl.Employee;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.command.impl.Employee.Creator.EmployeeCreator;
import com.kovzan.task_manager.entities.Employee;
import com.kovzan.task_manager.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class UpdateEmployeeCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        Employee employee;
        try {
            employee = EmployeeCreator.createEmployeeWithIdFromRequest(request);
            EmployeeService.updateEmployee(employee);
            List<Employee> employees = EmployeeService.findAllEmployees();
            request.setAttribute(ParameterNameConstant.PRINTED_EMPLOYEES, employees);
        } catch (Exception e) {
            System.out.println(e);
            return PageConstant.ERROR_PAGE;
        }
        return PageConstant.EMPLOYEES_PAGE;
    }
}
