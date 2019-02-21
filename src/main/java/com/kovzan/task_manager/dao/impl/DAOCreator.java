package com.kovzan.task_manager.dao.impl;

import com.kovzan.task_manager.entities.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOCreator {

    public static List<Employee> createEmployees(ResultSet resultSet) throws SQLException{

        ArrayList<Employee> employees = new ArrayList<>();

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String lastName = resultSet.getString(2);
                String firstName = resultSet.getString(3);
                String middleName = resultSet.getString(4);
                String position = resultSet.getString(5);
                Employee employee = new Employee(id, firstName, lastName, middleName, position);
                employees.add(employee);
        }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return employees;
    }
}
