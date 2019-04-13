package com.kovzan.task_manager.command.impl.employee;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.dao.impl.DaoCreator;
import com.kovzan.task_manager.dao.impl.TaskDaoImpl;
import com.kovzan.task_manager.entity.Employee;
import com.kovzan.task_manager.logger.LogConstant;
import com.kovzan.task_manager.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class RemoveEmployeeCommand implements Command {

	private static final String EMPLOYEE_CANNOT_BE_DELETED = "Employee cannot be deleted while is being assigned to task";

	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		int employeeId = Integer.parseInt(request.getParameter(ParameterNameConstant.EMPLOYEE_ID));
		Employee employee = new Employee();
		employee.setId(employeeId);
		if (canEmployeeBeDeleted(employee.getId())) {
			EmployeeService.removeEmployee(employee);
		} else {
			request.setAttribute(ParameterNameConstant.ERROR, EMPLOYEE_CANNOT_BE_DELETED);
		}
		List<Employee> employees = EmployeeService.findAllEmployees();
		request.setAttribute(ParameterNameConstant.PRINTED_EMPLOYEES, employees);
		logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		return PageConstant.EMPLOYEES_PAGE;
	}

	public static boolean canEmployeeBeDeleted(int id) throws SQLException {
		TaskDaoImpl taskDao = new TaskDaoImpl();
		return taskDao.findTasksByEmployeeId(id).isEmpty();
	}
}
