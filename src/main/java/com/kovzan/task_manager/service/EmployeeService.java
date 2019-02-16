package com.kovzan.task_manager.service;

import com.kovzan.task_manager.dao.impl.EmployeeDAOImpl;
import com.kovzan.task_manager.entities.Employee;

import java.sql.SQLException;
import java.util.List;

public class EmployeeService {

    public static List<Employee> findAllEmployees() throws SQLException {
        List<Employee> employees;
        try {
            employees = EmployeeDAOImpl.getInstance().findAll();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return employees;
    }

    public static Employee findEmployeeById(int id) throws SQLException {
        Employee employee;
        try {
            employee = EmployeeDAOImpl.getInstance().findEmployeeById(id);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return employee;
    }

    public static void addEmployee(Employee employee) throws SQLException {
        try {
            EmployeeDAOImpl.getInstance().add(employee);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public static void updateEmployee(Employee employee) throws SQLException {
        try{
            EmployeeDAOImpl.getInstance().update(employee);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public static void removeEmployee(Employee employee) throws SQLException {
        try {
            EmployeeDAOImpl.getInstance().remove(employee);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

}
