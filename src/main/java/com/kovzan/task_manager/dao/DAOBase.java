package com.kovzan.task_manager.dao;

import com.kovzan.task_manager.entities.Entity;
import com.kovzan.task_manager.exception.DAOException;

import java.util.List;

public interface DAOBase <T extends Entity> {
	int add(T element) throws DAOException;
	void remove(T element) throws DAOException;
	int update(T element) throws DAOException;
	List<T> findAll() throws DAOException;
	T findById(int id) throws DAOException;

}
