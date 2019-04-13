package com.kovzan.task_manager.dao.impl;

import com.kovzan.task_manager.dao.DaoException;
import com.kovzan.task_manager.entity.Employee;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.entity.TaskStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DaoCreator {

	public static List<Project> createProjects(ResultSet resultSet) throws DaoException {

		ArrayList<Project> projects = new ArrayList<>();
		try {
			do {
				Project project = new Project();
				project.setId(resultSet.getInt(1));
				project.setName(resultSet.getString(2));
				project.setShortName(resultSet.getString(3));
				project.setDescription(resultSet.getString(4));
				projects.add(project);
			}
			while(resultSet.next());
		}
		catch (SQLException e) {
			throw new DaoException(e);
		}
		return projects;
	}

	public static List<Task> createTasks(ResultSet resultSet) throws DaoException {

		ArrayList<Task> tasks = new ArrayList<>();
		try {
			do {
				Task task = new Task();
				task.setId(resultSet.getInt(1));
				task.setName(resultSet.getString(2));
				task.setBeginDate(resultSet.getDate(3).toLocalDate());
				task.setEndDate(resultSet.getDate(7).toLocalDate());
				task.setWork(resultSet.getInt(4));
				task.setProjectId(resultSet.getInt(5));
				task.setStatus(TaskStatus.valueOf(resultSet.getString(6)));
				task.setEmployeeId(resultSet.getInt(8));
				tasks.add(task);
			}
			while (resultSet.next());
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return tasks;
	}

	public static List<Task> createTasksWithRefs(ResultSet resultSet) throws DaoException {

		ArrayList<Task> tasks = new ArrayList<>();
		try {
			do {
				Task task = new Task();
				task.setId(resultSet.getInt(1));
				task.setName(resultSet.getString(2));
				task.setBeginDate(resultSet.getDate(3).toLocalDate());
				task.setEndDate(resultSet.getDate(7).toLocalDate());
				task.setWork(resultSet.getInt(4));
				task.setProjectShortName(resultSet.getString(5));
				task.setStatus(TaskStatus.valueOf(resultSet.getString(6)));
				task.setEmployeeFullName(resultSet.getString(8));
				tasks.add(task);
			}
			while (resultSet.next());
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return tasks;
	}

	public static List<Employee> createEmployees(ResultSet resultSet) throws DaoException {

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
		}
		catch (SQLException e) {
			throw new DaoException(e);
		}
		return employees;
	}
}
