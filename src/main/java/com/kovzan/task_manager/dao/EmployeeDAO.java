package com.kovzan.task_manager.dao;

import com.kovzan.task_manager.entities.Employee;
import com.kovzan.task_manager.exception.DAOException;

import java.util.List;

public interface EmployeeDAO extends DAOBase<Employee> {

	Employee findEmployeeById(int id) throws DAOException;
}
