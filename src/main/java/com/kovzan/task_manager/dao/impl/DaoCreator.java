package com.kovzan.task_manager.dao.impl;

import com.kovzan.task_manager.dao.DaoException;
import com.kovzan.task_manager.entity.Employee;
import com.kovzan.task_manager.entity.Project;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.service.StatusService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static com.kovzan.task_manager.logger.Log.logger;

public class DaoCreator {

	public static List<Project> createProjects(ResultSet resultSet) throws DaoException {

		ArrayList<Project> projects = new ArrayList<>();
		try {
			do {
				Integer id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String shortName = resultSet.getString(3);
				String description = resultSet.getString(4);
				Project project = new Project(id, name, shortName, description);
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
				Integer id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				LocalDate createdOn = LocalDate.parse(resultSet.getString(3));
				Integer estimate = resultSet.getInt(4);
				Integer projectId = resultSet.getInt(5);
				Integer taskStatusId = resultSet.getInt(6);
				LocalDate finishedOn = LocalDate.parse(resultSet.getString(7));
				Integer employeeId = resultSet.getInt(8);
				Task task = new Task(id, name, estimate, createdOn, finishedOn, projectId, employeeId, taskStatusId);
				tasks.add(task);}
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
				Integer id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				LocalDate createdOn = LocalDate.parse(resultSet.getString(3));
				Integer estimate = resultSet.getInt(4);
				String projectShortName = resultSet.getString(5);
				Integer statusId = resultSet.getInt(6);
				String statusName = StatusService.getInstance().findAllStatuses().get(statusId).getName();
				LocalDate finishedOn = LocalDate.parse(resultSet.getString(7));
				String employeeFullName = resultSet.getString(8);
				Task task = new Task(id, name, estimate, createdOn, finishedOn, projectShortName, employeeFullName, statusName);
				tasks.add(task);
			}
			while (resultSet.next());
		}
		catch (SQLException e) {
			throw new DaoException(e);
		}
		return tasks;
	}

	public static List<Employee> createEmployees(ResultSet resultSet) throws DaoException {

		ArrayList<Employee> employees = new ArrayList<>();
		try {
			do {
				Integer id = resultSet.getInt(1);
				String lastName = resultSet.getString(2);
				String firstName = resultSet.getString(3);
				String middleName = resultSet.getString(4);
				String position = resultSet.getString(5);
				Employee employee = new Employee(id, firstName, lastName, middleName, position);
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
