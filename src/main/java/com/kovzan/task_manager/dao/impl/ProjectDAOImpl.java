package com.kovzan.task_manager.dao.impl;

import com.kovzan.task_manager.connection.DBConnection;
import com.kovzan.task_manager.dao.ProjectDAO;
import com.kovzan.task_manager.entities.Project;
import com.kovzan.task_manager.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProjectDAOImpl implements ProjectDAO {

    private static final String ADD_PROJECT = "INSERT INTO PROJECTS (NAME, SHORTNAME, DESCRIPTION) VALUES (?, ?, ?)";
    private static final String UPDATE_PROJECT = "UPDATE PROJECTS SET NAME = ?, SHORTNAME = ?, DESCRIPTION = ? WHERE ID = ?";
    private static final String REMOVE_PROJECT = "DELETE FROM PROJECTS WHERE ID = ?";
    private static final String SELECT_ALL_PROJECTS = "SELECT * FROM PROJECTS";
    private static final String SELECT_PROJECT_BY_ID = "SELECT * FROM PROJECTS WHERE ID = ?";

    @Override
    public int add(Project element) throws DAOException {
        try {
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_PROJECT);
            statement.setString(1, element.getName());
            statement.setString(2, element.getShortName());
            statement.setString(3, element.getDescription());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            int result;
            if(keys.next()) {
                result = keys.getInt(1);
                return result;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    @Override
    public int update(Project element) throws DAOException {
        try {
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_PROJECT);
            statement.setString(1, element.getName());
            statement.setString(2, element.getShortName());
            statement.setString(3, element.getDescription()));
            statement.setInt(4, element.getId());
            statement.executeUpdate();
            int result;
            ResultSet keys = statement.getGeneratedKeys();
            if(keys.next()) {
                result = keys.getInt(1);
                return result;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    @Override
    public void remove(Project element) throws DAOException {
        try {
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(REMOVE_PROJECT);
            statement.setInt(1, element.getId());
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public List<Project> findAll() throws DAOException {
        List<Project> projects = null;
        try {
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PROJECTS);
            ResultSet resultSet = statement.executeQuery();
            projects = DAOCreator.createProjects(resultSet);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return projects;
    }

    @Override
    public Project findProjectById(int projectId) throws DAOException {
        Project project = null;
        try {
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_PROJECT_BY_ID);
            statement.setInt(1, projectId);
            ResultSet resultSet = statement.executeQuery();
            project = DAOCreator.createProjects(resultSet).get(0);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return project;
    }
}
