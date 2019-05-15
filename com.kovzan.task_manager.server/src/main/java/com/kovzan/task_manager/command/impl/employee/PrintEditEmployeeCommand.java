package com.kovzan.task_manager.command.impl.employee;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.impl.parameters.EmployeeParams;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.dao.impl.EmployeeDao;
import com.kovzan.task_manager.entity.Employee;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class PrintEditEmployeeCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		
		boolean isAddForm = Boolean.parseBoolean(request.getParameter(UtilParams.IS_ADD_FORM));
		if (isAddForm) {
			request.setAttribute(UtilParams.IS_ADD_FORM, true);
		} else {
			EmployeeDao employeeDao = new EmployeeDao();
			int employeeId = Integer.parseInt(request.getParameter(EmployeeParams.EMPLOYEE_ID));
			Employee employee = employeeDao.findById(employeeId);
			request.setAttribute(EmployeeParams.PRINTED_EDIT_EMPLOYEE, employee);
			request.setAttribute(UtilParams.IS_ADD_FORM, false);
		}
		return PageConstant.EDIT_EMPLOYEE_PAGE;
	}
}
