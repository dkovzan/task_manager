package com.kovzan.task_manager.dao;

import com.kovzan.task_manager.entity.Entity;

import java.sql.SQLException;
import java.util.List;

public interface DaoBase<T extends Entity> {
	int add(T element) throws SQLException;
	void remove(T element) throws SQLException;
	int update(T element) throws SQLException;
	List<T> findAll() throws SQLException;
	T findById(int id) throws SQLException;

}
