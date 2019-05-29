package com.kovzan.task_manager.dao.impl;

import com.kovzan.task_manager.dao.DaoBase;
import com.kovzan.task_manager.entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao extends DaoBase<Employee> {
	
	private static final String addEmployee =
			"INSERT INTO employee (lastname, firstname, middlename, position) " +
			"VALUES (?,?,?,?)";
	private static final String removeEmployee =
			"UPDATE employee SET isdeleted = 1 " +
			"WHERE id = ?";
	private static final String updateEmployee =
			"UPDATE employee " +
			"SET lastname = ?, firstname = ?, middlename = ?, position = ? " +
			"WHERE id = ?";
	private static final String selectAllEmployees =
			"SELECT id, lastname, firstname, middlename, position FROM employee WHERE isdeleted = 0 ORDER BY id";
	private static final String selectEmployeeById =
			"SELECT id, lastname, firstname, middlename, position FROM employee " +
			"WHERE id = ?";

	public void add(Employee employee) throws SQLException {
		add(employee, addEmployee);
	}

	public void update(Employee employee) throws SQLException {
		update(employee, updateEmployee);
	}

	public void remove(Employee employee) throws SQLException {
		remove(employee, removeEmployee);
	}

	public List<Employee> findAll() throws SQLException {
		return findAll(selectAllEmployees);
	}

	public Employee findById(int id) throws SQLException {
		return findById(id, selectEmployeeById);
	}

	@Override
	protected List<Employee> createEntitiesFromResultSet(ResultSet resultSet) throws SQLException {
		ArrayList<Employee> employees = new ArrayList<>();
		do {
			Employee employee = new Employee();
			employee.setId(resultSet.getInt(1));
			employee.setLastName(resultSet.getString(2));
			employee.setFirstName(resultSet.getString(3));
			employee.setMiddleName(resultSet.getString(4));
			employee.setPosition(resultSet.getString(5));
			employees.add(employee);
		}
		while (resultSet.next());
		return employees;
	}

	@Override
	protected PreparedStatement fillPreparedStatementForAdd(Employee element, PreparedStatement statement) throws SQLException {
		statement.setString(1, element.getLastName());
		statement.setString(2, element.getFirstName());
		statement.setString(3, element.getMiddleName());
		statement.setString(4, element.getPosition());
		return statement;
	}

	@Override
	protected PreparedStatement fillPreparedStatementForUpdate(Employee element, PreparedStatement statement) throws SQLException {
		statement.setString(1, element.getLastName());
		statement.setString(2, element.getFirstName());
		statement.setString(3, element.getMiddleName());
		statement.setString(4, element.getPosition());
		statement.setInt(5, element.getId());
		return statement;
	}

}
