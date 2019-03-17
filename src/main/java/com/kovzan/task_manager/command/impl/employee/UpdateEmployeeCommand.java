package com.kovzan.task_manager.command.impl.employee;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.command.service.EntityCreatorFromRequest;
import com.kovzan.task_manager.entities.Employee;
import com.kovzan.task_manager.logger.LogConstant;
import com.kovzan.task_manager.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class UpdateEmployeeCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) {
		Employee employee;
		try {
			employee = EntityCreatorFromRequest.createEmployeeFromRequest(request);
			EmployeeService.updateEmployee(employee);
			List<Employee> employees = EmployeeService.getInstance().findAllEmployees();
			request.setAttribute(ParameterNameConstant.PRINTED_EMPLOYEES, employees);
		} catch (Exception e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
			return PageConstant.ERROR_PAGE;
		}
		logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		return PageConstant.EMPLOYEES_PAGE;
	}
}