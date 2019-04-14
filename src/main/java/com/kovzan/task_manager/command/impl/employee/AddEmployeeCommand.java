package com.kovzan.task_manager.command.impl.employee;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.command.service.EntityCreatorFromRequest;
import com.kovzan.task_manager.entity.Employee;
import com.kovzan.task_manager.logger.LogConstant;
import com.kovzan.task_manager.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class AddEmployeeCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) throws SQLException {

		Employee employee = EntityCreatorFromRequest.createEmployeeFromRequest(request);
		EmployeeService.addEmployee(employee);
		List<Employee> employees = EmployeeService.findAllEmployees();
		request.setAttribute(ParameterNameConstant.PRINTED_EMPLOYEES, employees);
		logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		return PageConstant.EMPLOYEES_PAGE;
	}
}
