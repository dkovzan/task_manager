package com.kovzan.task_manager.dao.impl;

import com.kovzan.task_manager.connection.DBConnection;
import com.kovzan.task_manager.dao.DaoBase;
import com.kovzan.task_manager.entity.Employee;
import com.kovzan.task_manager.dao.DaoException;
import com.kovzan.task_manager.logger.LogConstant;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class EmployeeDaoImpl implements DaoBase<Employee> {

	private static final String ADD_EMPLOYEE =
			"INSERT INTO EMPLOYEES (LASTNAME, FIRSTNAME, MIDDLENAME, POSITION) " +
			"VALUES (?,?,?,?)";
	private static final String REMOVE_EMPLOYEE =
			"DELETE FROM EMPLOYEES " +
			"WHERE ID = ?";
	private static final String UPDATE_EMPLOYEE =
			"UPDATE EMPLOYEES " +
			"SET LASTNAME = ?, FIRSTNAME = ?, MIDDLENAME = ?, POSITION = ? " +
			"WHERE ID = ?";
	private static final String SELECT_ALL_EMPLOYEES =
			"SELECT * FROM EMPLOYEES";
	private static final String SELECT_EMPLOYEE_BY_ID =
			"SELECT * FROM EMPLOYEES " +
			"WHERE ID = ?";

	private static EmployeeDaoImpl instance = new EmployeeDaoImpl();

	public static EmployeeDaoImpl getInstance() {
		return instance;
	}

	@Override
	public int add(Employee element) throws DaoException {
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(ADD_EMPLOYEE);
			statement.setString(1, element.getLastName());
			statement.setString(2, element.getFirstName());
			statement.setString(3, element.getMiddleName());
			statement.setString(4, element.getPosition());
			statement.executeUpdate();
			int result;
			ResultSet keys = statement.getGeneratedKeys();
			if(keys.next()) {
				result = keys.getInt(1);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
				return result;
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return -1;
	}

	@Override
	public int update(Employee element) throws DaoException {
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(UPDATE_EMPLOYEE);
			statement.setString(1, element.getLastName());
			statement.setString(2, element.getFirstName());
			statement.setString(3, element.getMiddleName());
			statement.setString(4, element.getPosition());
			statement.setInt(5, element.getId());
			statement.executeUpdate();
			int result;
			ResultSet keys = statement.getGeneratedKeys();
			if(keys.next()) {
				result = keys.getInt(1);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
				return result;
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return -1;
	}

	@Override
	public void remove(Employee element) throws DaoException {
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(REMOVE_EMPLOYEE);
			statement.setInt(1, element.getId());
			statement.execute();
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public List<Employee> findAll() throws DaoException {
		List<Employee> employees = null;
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(SELECT_ALL_EMPLOYEES);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				employees = DaoCreator.createEmployees(resultSet);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			}
			else {
				return employees;
			}
		}
		catch (SQLException e) {
			throw new DaoException(e);
		}
		return employees;
	}

	@Override
	public Employee findById(int employeeId) throws DaoException {
		Employee employee = null;
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID);
			statement.setInt(1, employeeId);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				employee = DaoCreator.createEmployees(resultSet).get(0);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			} else {
				return employee;
			}
		}
		catch (SQLException e) {
			throw new DaoException(e);
		}
		return employee;
	}
}
