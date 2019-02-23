package com.kovzan.task_manager.dao;

import com.kovzan.task_manager.entities.Project;
import com.kovzan.task_manager.exception.DAOException;

public interface ProjectDAO extends DAOBase<Project>{

    Project findProjectById(int id) throws DAOException;
}
