package com.kovzan.task_manager.dao.impl;

import com.kovzan.task_manager.connection.DBConnection;
import com.kovzan.task_manager.dao.DaoBase;
import com.kovzan.task_manager.entity.Employee;
import com.kovzan.task_manager.logger.LogConstant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class EmployeeDao implements DaoBase<Employee> {
	
	private static final String addEmployee =
			"INSERT INTO employee (lastname, firstname, middlename, position) " +
			"VALUES (?,?,?,?)";
	private static final String removeEmployee =
			"DELETE FROM employee " +
			"WHERE id = ?";
	private static final String updateEmployee =
			"UPDATE employee " +
			"SET lastname = ?, firstname = ?, middlename = ?, position = ? " +
			"WHERE id = ?";
	private static final String selectAllEmployees =
			"SELECT id, lastname, firstname, middlename, position FROM employee ORDER BY id";
	private static final String selectEmployeeById =
			"SELECT id, lastname, firstname, middlename, position FROM employee " +
			"WHERE id = ?";

	private static EmployeeDao instance = new EmployeeDao();

	public static EmployeeDao getInstance() {
		return instance;
	}

	@Override
	public int add(Employee element) throws SQLException {
		int result = -1;
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(addEmployee, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, element.getLastName());
			statement.setString(2, element.getFirstName());
			statement.setString(3, element.getMiddleName());
			statement.setString(4, element.getPosition());
			statement.executeUpdate();
			ResultSet keys = statement.getGeneratedKeys();
			if(keys.next()) {
				result = keys.getInt(1);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			}
		}
		return result;
	}

	@Override
	public void update(Employee element) throws SQLException {
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(updateEmployee);
			statement.setString(1, element.getLastName());
			statement.setString(2, element.getFirstName());
			statement.setString(3, element.getMiddleName());
			statement.setString(4, element.getPosition());
			statement.setInt(5, element.getId());
			statement.executeUpdate();
			logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			}
		}

	@Override
	public void remove(Employee element) throws SQLException {
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(removeEmployee);
			statement.setInt(1, element.getId());
			statement.execute();
		}
	}

	@Override
	public List<Employee> findAll() throws SQLException {
		List<Employee> employees = new ArrayList<>();
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(selectAllEmployees);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				employees = DaoCreator.createEmployees(resultSet);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			}
		}
		return employees;
	}

	@Override
	public Employee findById(int employeeId) throws SQLException {
		Employee employee = null;
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(selectEmployeeById);
			statement.setInt(1, employeeId);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				employee = DaoCreator.createEmployees(resultSet).get(0);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			}
		}
		return employee;
	}
}
