package com.kovzan.task_manager.dao.impl;

import com.kovzan.task_manager.connection.DBConnection;
import com.kovzan.task_manager.dao.StatusDAO;
import com.kovzan.task_manager.entities.Status;
import com.kovzan.task_manager.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StatusDAOImpl implements StatusDAO {

//    private static final String ADD_STATUS = "INSERT INTO TASKSTATUSES(VALUE) VALUES(?)";
//    private static final String UPDATE_STATUS = "UPDATE TASKSTATUSES SET VALUE = ? WHERE ID = ?";
    private static final String SELECT_ALL_STATUSES = "SELECT * FROM TASKSTATUSES";
    private static final String SELECT_STATUS_BY_ID = "SELECT * FROM TASKSTATUSES WHERE ID = ?";

    private static StatusDAOImpl instance = new StatusDAOImpl();

    public static StatusDAOImpl getInstance() {
        return instance;
    }

    public List<Status> findAll() throws DAOException {
        List<Status> statuses = null;
        try {
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_STATUSES);
            ResultSet resultSet = statement.executeQuery();
            statuses = DAOCreator.createStatuses(resultSet);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return statuses;
    }

    @Override
    public Status findStatusById(int statusId) throws DAOException {
        Status status = null;
        try {
            Connection connection = DBConnection.getDBConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_STATUS_BY_ID);
            statement.setInt(1, statusId);
            ResultSet resultSet = statement.executeQuery();
            status = DAOCreator.createStatuses(resultSet).get(0);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return status;
    }

//    @Override
//    public int add(Status status) throws DAOException {
//        try {
//            Connection connection = DBConnection.getDBConnection();
//            PreparedStatement statement = connection.prepareStatement(ADD_STATUS);
//            statement.setString(1,status.getName());
//            statement.executeUpdate();
//            ResultSet keys = statement.getGeneratedKeys();
//            int result;
//            if (keys.next()) {
//                result = keys.getInt(1);
//                return result;
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//        return -1;
//    }

//    @Override
//    public int update(Status status) throws DAOException {
//        try {
//            Connection connection = DBConnection.getDBConnection();
//            PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS);
//            statement.setString(1, status.getName());
//            statement.setInt(2, status.getId());
//            statement.executeUpdate();
//            ResultSet keys = statement.getGeneratedKeys();
//            int result;
//            if (keys.next()) {
//                result = keys.getInt(1);
//                return result;
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//        return -1;
//    }
}
