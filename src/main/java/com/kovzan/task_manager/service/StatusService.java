package com.kovzan.task_manager.service;

import com.kovzan.task_manager.dao.impl.StatusDAOImpl;
import com.kovzan.task_manager.entities.Status;

import java.sql.SQLException;
import java.util.List;

public class StatusService {

    public static List<Status> findAllStatuses() throws SQLException {
        List<Status> statuses = null;
        try {
            statuses = StatusDAOImpl.getInstance().findAll();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return statuses;
    }

    public static Status findStatusById(int id) throws SQLException {
        Status status = null;
        try {
            status = StatusDAOImpl.getInstance().findStatusById(id);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return status;
    }
}
