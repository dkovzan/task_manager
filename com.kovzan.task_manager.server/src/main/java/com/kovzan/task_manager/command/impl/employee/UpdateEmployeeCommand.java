package com.kovzan.task_manager.command.impl.employee;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.Commands;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ValidationException;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.dao.DaoException;
import com.kovzan.task_manager.dao.EmployeeDao;
import com.kovzan.task_manager.entity.Employee;

import javax.servlet.http.HttpServletRequest;

public class UpdateEmployeeCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) throws DaoException {
		
		Employee employee;
		try {
			employee = EmployeeUtils.buildEmployee(request);
		} catch (ValidationException e) {
			request.setAttribute(UtilParams.VALIDATION_EXCEPTION, e);
			request.setAttribute(UtilParams.IS_ADD_FORM, false);
			return PageConstant.EDIT_EMPLOYEE_PAGE;
		}
		EmployeeDao employeeDao = new EmployeeDao();
		employeeDao.update(employee);
		return Commands.PRINT_EMPLOYEES.getCommand().execute(request);
	}
}
