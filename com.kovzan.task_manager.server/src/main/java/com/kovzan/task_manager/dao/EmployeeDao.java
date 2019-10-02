package com.kovzan.task_manager.dao;

import com.kovzan.task_manager.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao extends DaoBase<Employee> {
	
	private final String addEmployee =
			"INSERT INTO employee (lastname, firstname, middlename, position) " +
			"VALUES (?,?,?,?)";
	private final String removeEmployee =
			"UPDATE employee SET isdeleted = 1 " +
			"WHERE id = ?";
	private final String updateEmployee =
			"UPDATE employee " +
			"SET lastname = ?, firstname = ?, middlename = ?, position = ? " +
			"WHERE id = ?";
	private final String selectAllEmployees =
			"SELECT id, lastname, firstname, middlename, position FROM employee " +
			"WHERE isdeleted = 0 " +
			"ORDER BY id";
	private final String selectEmployeeById =
			"SELECT id, lastname, firstname, middlename, position FROM employee " +
			"WHERE id = ?";
	
	@Override
	protected List<Employee> createEntitiesFromResultSet(ResultSet resultSet) throws DaoException {
		ArrayList<Employee> employees = new ArrayList<>();
		try {
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
		} catch (SQLException e) {
			throw new DaoException("Error while creating employee from result set: ", e);
		}
		return employees;
	}
	
	@Override
	protected PreparedStatement fillPreparedStatementForAdd(Employee element, PreparedStatement statement) throws DaoException {
		try {
			statement.setString(1, element.getLastName());
			statement.setString(2, element.getFirstName());
			statement.setString(3, element.getMiddleName());
			statement.setString(4, element.getPosition());
		} catch (SQLException e) {
			throw new DaoException("Error while filling prepared statement for adding employee: ", e);
		}
		return statement;
	}
	
	@Override
	protected PreparedStatement fillPreparedStatementForUpdate(Employee element, PreparedStatement statement) throws DaoException {
		try {
			statement.setString(1, element.getLastName());
			statement.setString(2, element.getFirstName());
			statement.setString(3, element.getMiddleName());
			statement.setString(4, element.getPosition());
			statement.setInt(5, element.getId());
		} catch (SQLException e) {
			throw new DaoException("Error while filling prepared statement for updating employee: ", e);
		}
		return statement;
	}
	
	@Override
	protected String getAddSqlStatement() {
		return addEmployee;
	}
	
	@Override
	protected String getRemoveSqlStatement() {
		return removeEmployee;
	}
	
	@Override
	protected String getUpdateSqlStatement() {
		return updateEmployee;
	}
	
	@Override
	protected String getFindAllSqlStatement() {
		return selectAllEmployees;
	}
	
	@Override
	protected String getFindByIdSqlStatement() {
		return selectEmployeeById;
	}

}
