package com.kovzan.task_manager.dao.impl;

import com.kovzan.task_manager.connection.DBConnection;
import com.kovzan.task_manager.dao.EmployeeDAO;
import com.kovzan.task_manager.entities.Employee;
import com.kovzan.task_manager.exception.DAOException;

import java.sql.*;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    private static final String ADD_EMPLOYEE = "INSERT INTO EMPLOYEES (LASTNAME, FIRSTNAME, MIDDLENAME, POSITION) values (?,?,?,?)";
    private static final String REMOVE_EMPLOYEE = "DELETE FROM EMPLOYEES WHERE ID = ?";
    private static final String UPDATE_EMPLOYEE = "UPDATE EMPLOYEES SET LASTNAME = ?, FIRSTNAME = ?, MIDDLENAME = ?, POSITION = ? WHERE ID = ?";
    private static final String SELECT_ALL_EMPLOYEES = "SELECT * FROM EMPLOYEES";
    private static final String SELECT_EMPLOYEE_BY_ID = "SELECT * FROM EMPLOYEES WHERE ID = ?";
    private static EmployeeDAOImpl instance = new EmployeeDAOImpl();

    public static EmployeeDAOImpl getInstance() {
        return instance;
    }

    @Override
    public int add(Employee element) throws DAOException {
        try {
            Connection connection = DBConnection.getDBConnection();
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
                return result;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    @Override
    public int update(Employee element) throws DAOException {
        try {
            Connection connection = DBConnection.getDBConnection();
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
                return result;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    @Override
    public void remove(Employee element) throws DAOException {
        try {
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(REMOVE_EMPLOYEE);
            statement.setInt(1, element.getId());
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Employee> findAll() throws DAOException {
        List<Employee> employees = null;
        try {
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_EMPLOYEES);
            ResultSet resultSet = statement.executeQuery();
            employees = DAOCreator.createEmployees(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employees;
    }

    @Override
    public Employee findEmployeeById(int employeeId) throws DAOException {
        Employee employee = null;
        try {
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID);
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();
            employee = DAOCreator.createEmployees(resultSet).get(0);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employee;
    }
}
