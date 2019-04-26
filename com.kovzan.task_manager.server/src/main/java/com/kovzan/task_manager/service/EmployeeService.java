package com.kovzan.task_manager.service;

import com.kovzan.task_manager.dao.impl.EmployeeDao;
import com.kovzan.task_manager.entity.Employee;
import com.kovzan.task_manager.logger.LogConstant;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class EmployeeService {

	private EmployeeService() {}

	public static List<Employee> findAllEmployees() throws SQLException {
		return EmployeeDao.getInstance().findAll();
	}

	public static Employee findEmployeeById(int id) throws SQLException {
		Employee employee = EmployeeDao.getInstance().findById(id);
		if (employee == null) {
			String paramName = "Id";
			//throw new Exception(String.format(LogConstant.EMPLOYEE_NOT_FOUND, paramName, id));
			logger.log(Level.WARNING, String.format(LogConstant.EMPLOYEE_NOT_FOUND, paramName, id));
		}
		return employee;
	}

	public static void addEmployee(Employee employee) throws SQLException {
		EmployeeDao.getInstance().add(employee);
	}

	public static void updateEmployee(Employee employee) throws SQLException {
		EmployeeDao.getInstance().update(employee);
	}

	public static void removeEmployee(Employee employee) throws SQLException {
		EmployeeDao.getInstance().remove(employee);
	}

}
