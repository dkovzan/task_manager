package com.kovzan.task_manager.command.impl.Employee.Creator;

import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.entities.Employee;

import javax.servlet.http.HttpServletRequest;

public class EmployeeCreator {

    public static Employee createEmployeeFromRequest(HttpServletRequest request) {
        String employeeFirstName = request.getParameter(ParameterNameConstant.EMPLOYEE_FIRSTNAME);
        String employeeLastName = request.getParameter(ParameterNameConstant.EMPLOYEE_LASTNAME);
        String employeeMiddleName = request.getParameter(ParameterNameConstant.EMPLOYEE_MIDDLENAME);
        String employeePosition = request.getParameter(ParameterNameConstant.EMPLOYEE_POSITION);
        return new Employee(employeeFirstName, employeeLastName, employeeMiddleName, employeePosition);
    }

    public static Employee createEmployeeWithIdFromRequest(HttpServletRequest request) {
        Employee employee = createEmployeeFromRequest(request);
        try {
            int employeeId = Integer.parseInt(request.getParameter(ParameterNameConstant.EMPLOYEE_ID));
            employee.setId(employeeId);
        } catch(Exception e) {
            System.out.println(e);
        }
        return employee;
    }
}
