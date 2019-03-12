package com.kovzan.task_manager.dao;

import com.kovzan.task_manager.entities.Status;
import com.kovzan.task_manager.exception.DAOException;

public interface StatusDAO {

	Status findStatusById(int id) throws DAOException;
}
