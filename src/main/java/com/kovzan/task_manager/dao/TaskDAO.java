package com.kovzan.task_manager.dao;

import com.kovzan.task_manager.entities.Task;
import com.kovzan.task_manager.exception.DAOException;

import java.util.List;

public interface TaskDAO extends DAOBase<Task> {

    Task findTaskById(int id) throws DAOException;
    List<Task> findAllTasksWithRefs() throws DAOException;
}
