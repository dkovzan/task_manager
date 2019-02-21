package com.kovzan.task_manager.command.impl.Employee;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.entities.Employee;
import com.kovzan.task_manager.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class PrintEditEmployeeCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute(ParameterNameConstant.PRINTED_EDIT_EMPLOYEE, null);
        int employeeId = Integer.parseInt(request.getParameter(ParameterNameConstant.EMPLOYEE_ID));
        if (employeeId == -1) {
            request.setAttribute(ParameterNameConstant.IS_ADD_FORM, 1);
        } else {
            Employee employee;
            try {
                employee = EmployeeService.findEmployeeById(employeeId);
            } catch (SQLException e) {
                System.out.println(e);
                return PageConstant.ERROR_PAGE;
            }
            request.setAttribute(ParameterNameConstant.PRINTED_EDIT_EMPLOYEE, employee);
            request.setAttribute(ParameterNameConstant.IS_ADD_FORM, 0);
        }
        return PageConstant.EDIT_EMPLOYEE_PAGE;
    }
}
