package com.kovzan.task_manager.command.impl.employee;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.CommandEnum;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ValidationException;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.dao.impl.EmployeeDao;
import com.kovzan.task_manager.entity.Employee;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class UpdateEmployeeCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		
		Employee employee;
		try {
			employee = EmployeeUtils.buildEmployee(request);
		} catch (ValidationException e) {
			request.setAttribute(UtilParams.VALIDATION_EXCEPTION, e);
			request.setAttribute(UtilParams.IS_ADD_FORM, 0);
			return PageConstant.EDIT_EMPLOYEE_PAGE;
		}
		EmployeeDao employeeDao = new EmployeeDao();
		employeeDao.update(employee);
		return CommandEnum.PRINT_EMPLOYEES.getCommand().execute(request);
	}
}
