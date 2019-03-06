package com.kovzan.task_manager.command.impl.Employee.Creator;

import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.entities.Employee;
import com.kovzan.task_manager.logger.LogConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class EmployeeCreator {

    public static Employee createEmployeeFromRequest(HttpServletRequest request) {
        String employeeFirstName = request.getParameter(ParameterNameConstant.EMPLOYEE_FIRSTNAME);
        String employeeLastName = request.getParameter(ParameterNameConstant.EMPLOYEE_LASTNAME);
        String employeeMiddleName = request.getParameter(ParameterNameConstant.EMPLOYEE_MIDDLENAME);
        String employeePosition = request.getParameter(ParameterNameConstant.EMPLOYEE_POSITION);
        Employee employee = new Employee(employeeFirstName, employeeLastName, employeeMiddleName, employeePosition);
        logger.log(Level.INFO, LogConstant.OBJECT_CREATED + employee.toString());
        return employee;
    }

    public static Employee createEmployeeWithIdFromRequest(HttpServletRequest request) {
        Employee employee = createEmployeeFromRequest(request);
        try {
            int employeeId = Integer.parseInt(request.getParameter(ParameterNameConstant.EMPLOYEE_ID));
            employee.setId(employeeId);
        } catch(Exception e) {
            logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
        }
        logger.log(Level.INFO, LogConstant.OBJECT_CREATED + employee.toString());
        return employee;
    }
}
