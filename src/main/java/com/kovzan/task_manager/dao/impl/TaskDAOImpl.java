package com.kovzan.task_manager.dao.impl;

import com.kovzan.task_manager.connection.DBConnection;
import com.kovzan.task_manager.dao.TaskDAO;
import com.kovzan.task_manager.entities.Task;
import com.kovzan.task_manager.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TaskDAOImpl implements TaskDAO {

    private static final String ADD_TASK = "INSERT INTO TASKS(NAME, ESTIMATE, CREATEDON, FINISHEDON, PROJECTID, EMPLOYEEID, STATUSID) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_TASK = "UPDATE TASKS SET NAME = ?, ESTIMATE = ?, CREATEDON = ?, FINISHEDON = ?, PROJECTID = ?, EMPLOYEEID = ?, STATUSID = ? WHERE ID = ?";
    private static final String REMOVE_TASK = "DELETE FROM TASKS WHERE ID = ?";
    private static final String SELECT_ALL_TASKS = "SELECT * FROM TASKS";
    private static final String SELECT_TASK_BY_ID = "SELECT * FROM TASKS WHERE ID = ?";

    private static TaskDAOImpl instance = new TaskDAOImpl();

    public static TaskDAOImpl getInstance() {
        return instance;
    }

    @Override
    public int add(Task task) throws DAOException {
        try {
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_TASK);
            statement.setString(1, task.getName());
            statement.setInt(2, task.getEstimate());
            statement.setString(3, task.getCreatedOn());
            statement.setString(4, task.getFinishedOn());
            statement.setInt(5, task.getProjectId());
            statement.setInt(6, task.getEmployeeId());
            statement.setInt(7, task.getStatusId());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            int result;
            if (keys.next()) {
                result = keys.getInt(1);
                return result;
            }
            } catch (SQLException e) {
                System.out.println(e);
        }
        return -1;
    }

    @Override
    public int update(Task task) throws DAOException {
        try {
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_TASK);
            statement.setString(1, task.getName());
            statement.setInt(2, task.getEstimate());
            statement.setString(3, task.getCreatedOn());
            statement.setString(4, task.getFinishedOn());
            statement.setInt(5, task.getProjectId());
            statement.setInt(6, task.getEmployeeId());
            statement.setInt(7, task.getStatusId());
            statement.setInt(8, task.getId());
            statement.executeUpdate();
            int result;
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                result = keys.getInt(1);
                return result;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    @Override
    public void remove(Task task) throws DAOException {
        try {
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(REMOVE_TASK);
            statement.setInt(1, task.getId());
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public List<Task> findAll() throws DAOException {
        List<Task> tasks = null;
        try {
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_TASKS);
            ResultSet resultSet = statement.executeQuery();
            tasks = DAOCreator.createTasks(resultSet);
        } catch (SQLException e) {
            System.out.print(e);
        }
        return tasks;
    }

    @Override
    public Task findTaskById(int taskId) throws DAOException {
        Task task = null;
        try {
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_TASK_BY_ID);
            statement.setInt(1, taskId);
            ResultSet resultSet = statement.executeQuery();
            task = DAOCreator.createTasks(resultSet).get(0);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return task;
    }
}
