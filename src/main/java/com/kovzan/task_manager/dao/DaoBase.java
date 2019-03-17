package com.kovzan.task_manager.dao;

import com.kovzan.task_manager.entity.Entity;

import java.util.List;

public interface DaoBase<T extends Entity> {
	int add(T element) throws DaoException;
	void remove(T element) throws DaoException;
	int update(T element) throws DaoException;
	List<T> findAll() throws DaoException;
	T findById(int id) throws DaoException;

}
