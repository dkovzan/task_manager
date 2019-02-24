package com.kovzan.task_manager.dao;

import com.kovzan.task_manager.entities.Task;
import com.kovzan.task_manager.exception.DAOException;

public interface TaskDAO extends DAOBase<Task> {

    Task findTaskById(int id) throws DAOException;
}
