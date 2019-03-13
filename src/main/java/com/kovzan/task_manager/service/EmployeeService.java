package com.kovzan.task_manager.service;

import com.kovzan.task_manager.dao.impl.EmployeeDAOImpl;
import com.kovzan.task_manager.entities.Employee;
import com.kovzan.task_manager.exception.DAOException;
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

	public static List<Employee> findAllEmployees() throws DAOException {
		return EmployeeDAOImpl.getInstance().findAll();
	}

	public static Employee findEmployeeById(int id) throws DAOException {
		Employee employee = EmployeeDAOImpl.getInstance().findById(id);
		if (employee == null) {
			String paramName = "Id";
			throw new DAOException(String.format(LogConstant.EMPLOYEE_NOT_FOUND, paramName, id));
		}
		return employee;
	}

	public static void addEmployee(Employee employee) throws DAOException {
		EmployeeDAOImpl.getInstance().add(employee);
	}

	public static void updateEmployee(Employee employee) throws DAOException {
		EmployeeDAOImpl.getInstance().update(employee);
	}

	public static void removeEmployee(Employee employee) throws DAOException {
		EmployeeDAOImpl.getInstance().remove(employee);
	}

}
