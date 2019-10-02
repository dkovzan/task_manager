package com.kovzan.task_manager.command.impl.employee;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.impl.parameters.EmployeeParams;
import com.kovzan.task_manager.dao.DaoException;
import com.kovzan.task_manager.dao.EmployeeDao;
import com.kovzan.task_manager.entity.Employee;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class PrintEmployeesCommand implements Command{

	@Override
	public String execute(HttpServletRequest request) throws DaoException {
		
		EmployeeDao employeeDao = new EmployeeDao();
		List<Employee> employees = employeeDao.findAll();
		request.setAttribute(EmployeeParams.PRINTED_EMPLOYEES, employees);
		return PageConstant.EMPLOYEES_PAGE;
	}
}
