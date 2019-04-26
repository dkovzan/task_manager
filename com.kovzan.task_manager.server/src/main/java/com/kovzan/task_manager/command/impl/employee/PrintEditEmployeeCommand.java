package com.kovzan.task_manager.command.impl.employee;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.impl.parameters.EmployeeParams;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.entity.Employee;
import com.kovzan.task_manager.logger.LogConstant;
import com.kovzan.task_manager.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class PrintEditEmployeeCommand implements Command {

	@Override
	public String execute(HttpServletRequest request) throws SQLException {

		int isAddForm = Integer.parseInt(request.getParameter(UtilParams.IS_ADD_FORM));
		if (isAddForm == 1) {
			request.setAttribute(UtilParams.IS_ADD_FORM, 1);
		} else {
			int employeeId = Integer.parseInt(request.getParameter(EmployeeParams.EMPLOYEE_ID));
			Employee employee = EmployeeService.findEmployeeById(employeeId);
			request.setAttribute(EmployeeParams.PRINTED_EDIT_EMPLOYEE, employee);
			request.setAttribute(UtilParams.IS_ADD_FORM, 0);
		}
		logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		return PageConstant.EDIT_EMPLOYEE_PAGE;
	}
}
