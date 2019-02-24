package com.kovzan.task_manager.service;

import com.kovzan.task_manager.dao.impl.TaskDAOImpl;
import com.kovzan.task_manager.entities.Task;

import java.sql.SQLException;
import java.util.List;

public class TaskService {

    public static List<Task> findAllTasks() throws SQLException {
        List<Task> tasks = null;
        try {
            tasks = TaskDAOImpl.getInstance().findAll();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return tasks;
    }

    public static Task findTaskById(int id) throws SQLException {
        Task project = null;
        try {
            project = TaskDAOImpl.getInstance().findTaskById(id);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return project;
    }

    public static void addTask(Task task) throws SQLException {
        try {
            TaskDAOImpl.getInstance().add(task);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void updateTask(Task task) throws SQLException {
        try {
            TaskDAOImpl.getInstance().update(task);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void removeTask(Task task) throws SQLException {
        try {
            TaskDAOImpl.getInstance().remove(task);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
