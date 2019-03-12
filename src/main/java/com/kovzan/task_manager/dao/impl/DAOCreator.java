package com.kovzan.task_manager.dao.impl;

import com.kovzan.task_manager.entities.Employee;
import com.kovzan.task_manager.entities.Project;
import com.kovzan.task_manager.entities.Status;
import com.kovzan.task_manager.entities.Task;
import com.kovzan.task_manager.logger.LogConstant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class DAOCreator {

	public static List<Project> createProjects(ResultSet resultSet) throws SQLException {

		ArrayList<Project> projects = new ArrayList<>();
		try {
			while(resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String shortName = resultSet.getString(3);
				String description = resultSet.getString(4);
				Project project = new Project(id, name, shortName, description);
				projects.add(project);
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
		}
		return projects;
	}

	public static List<Task> createTasks(ResultSet resultSet) throws SQLException {

		ArrayList<Task> tasks = new ArrayList<>();
		try {
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String createdOn = resultSet.getString(3);
				int estimate = resultSet.getInt(4);
				int projectId = resultSet.getInt(5);
				int taskStatusId = resultSet.getInt(6);
				String finishedOn = resultSet.getString(7);
				int employeeId = resultSet.getInt(8);
				Task task = new Task(id, name, estimate, createdOn, finishedOn, projectId, employeeId, taskStatusId);
				tasks.add(task);
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
		}
		return tasks;
	}

	public static List<Task> createTasksWithRefs(ResultSet resultSet) throws SQLException {

		ArrayList<Task> tasks = new ArrayList<>();
		try {
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String createdOn = resultSet.getString(3);
				int estimate = resultSet.getInt(4);
				String projectShortName = resultSet.getString(5);
				String statusName = resultSet.getString(6);
				String finishedOn = resultSet.getString(7);
				String employeeFullName = resultSet.getString(8);
				Task task = new Task(id, name, estimate, createdOn, finishedOn, projectShortName, employeeFullName, statusName);
				tasks.add(task);
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
		}
		return tasks;
	}

	public static List<Employee> createEmployees(ResultSet resultSet) throws SQLException {

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
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
		}
		return employees;
	}

	public static List<Status> createStatuses(ResultSet resultSet) throws SQLException {

		ArrayList<Status> statuses = new ArrayList<>();
		try {
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				Status status = new Status(id, name);
				statuses.add(status);
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, LogConstant.EXCEPTION, e);
		}
		return statuses;
	}
}
