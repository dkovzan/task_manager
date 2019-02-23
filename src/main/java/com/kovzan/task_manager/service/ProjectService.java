package com.kovzan.task_manager.service;

import com.kovzan.task_manager.dao.impl.ProjectDAOImpl;
import com.kovzan.task_manager.entities.Project;

import java.sql.SQLException;
import java.util.List;

public class ProjectService {

    public static List<Project> findAllProjects() throws SQLException {
        List<Project> projects = null;
        try {
            projects = ProjectDAOImpl.getInstance().findAll();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return projects;
    }

    public static Project findProjectById(int id) throws SQLException {
        Project project = null;
        try {
            project = ProjectDAOImpl.getInstance().findProjectById(id);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return project;
    }

    public static void addProject(Project project) throws SQLException {
        try {
            ProjectDAOImpl.getInstance().add(project);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void updateProject(Project project) throws SQLException {
        try {
            ProjectDAOImpl.getInstance().update(project);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void removeProject(Project project) throws SQLException {
        try {
            ProjectDAOImpl.getInstance().remove(project);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
