package com.kovzan.task_manager.service;

import com.kovzan.task_manager.dao.impl.EmployeeDaoImpl;
import com.kovzan.task_manager.entity.Employee;
import com.kovzan.task_manager.logger.LogConstant;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class EmployeeService {

	public static final EmployeeService instance = new EmployeeService();

	private EmployeeService() {}

	public static EmployeeService getInstance() {
		return instance;
	}

	public static List<Employee> findAllEmployees() throws SQLException {
		return EmployeeDaoImpl.getInstance().findAll();
	}

	public static Employee findEmployeeById(int id) throws SQLException {
		Employee employee = EmployeeDaoImpl.getInstance().findById(id);
		if (employee == null) {
			String paramName = "Id";
			//throw new Exception(String.format(LogConstant.EMPLOYEE_NOT_FOUND, paramName, id));
			logger.log(Level.WARNING, String.format(LogConstant.EMPLOYEE_NOT_FOUND, paramName, id));
		}
		return employee;
	}

	public static void addEmployee(Employee employee) throws SQLException {
		EmployeeDaoImpl.getInstance().add(employee);
	}

	public static void updateEmployee(Employee employee) throws SQLException {
		EmployeeDaoImpl.getInstance().update(employee);
	}

	public static void removeEmployee(Employee employee) throws SQLException {
		EmployeeDaoImpl.getInstance().remove(employee);
	}

}
