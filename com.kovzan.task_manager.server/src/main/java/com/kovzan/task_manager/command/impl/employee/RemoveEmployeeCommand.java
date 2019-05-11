package com.kovzan.task_manager.command.impl.employee;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.CommandEnum;
import com.kovzan.task_manager.command.impl.parameters.EmployeeParams;
import com.kovzan.task_manager.command.impl.parameters.UtilParams;
import com.kovzan.task_manager.dao.impl.EmployeeDao;
import com.kovzan.task_manager.dao.impl.TaskDao;
import com.kovzan.task_manager.entity.Employee;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class RemoveEmployeeCommand implements Command {

	private static final String EMPLOYEE_CANNOT_BE_DELETED = "Employee cannot be deleted while is being assigned to task";

	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		
		int employeeId = Integer.parseInt(request.getParameter(EmployeeParams.EMPLOYEE_ID));
		Employee employee = new Employee();
		employee.setId(employeeId);
		if (canEmployeeBeDeleted(employee.getId())) {
			EmployeeDao employeeDao = new EmployeeDao();
			employeeDao.remove(employee);
		} else {
			request.setAttribute(UtilParams.ERROR, EMPLOYEE_CANNOT_BE_DELETED);
		}
		return CommandEnum.PRINT_EMPLOYEES.getCommand().execute(request);
	}

	private static boolean canEmployeeBeDeleted(int id) throws SQLException {
		TaskDao taskDao = new TaskDao();
		return taskDao.findTasksByEmployeeId(id).isEmpty();
	}
}
