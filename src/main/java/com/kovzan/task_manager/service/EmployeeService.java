package com.kovzan.task_manager.service;

import com.kovzan.task_manager.dao.impl.EmployeeDaoImpl;
import com.kovzan.task_manager.entity.Employee;
import com.kovzan.task_manager.dao.DaoException;
import com.kovzan.task_manager.logger.LogConstant;

import java.util.List;

public class EmployeeService {

	public static final EmployeeService instance = new EmployeeService();

	private EmployeeService() {}

	public static EmployeeService getInstance() {
		return instance;
	}

	public static List<Employee> findAllEmployees() throws DaoException {
		return EmployeeDaoImpl.getInstance().findAll();
	}

	public static Employee findEmployeeById(int id) throws DaoException {
		Employee employee = EmployeeDaoImpl.getInstance().findById(id);
		if (employee == null) {
			String paramName = "Id";
			throw new DaoException(String.format(LogConstant.EMPLOYEE_NOT_FOUND, paramName, id));
		}
		return employee;
	}

	public static void addEmployee(Employee employee) throws DaoException {
		EmployeeDaoImpl.getInstance().add(employee);
	}

	public static void updateEmployee(Employee employee) throws DaoException {
		EmployeeDaoImpl.getInstance().update(employee);
	}

	public static void removeEmployee(Employee employee) throws DaoException {
		EmployeeDaoImpl.getInstance().remove(employee);
	}

}
