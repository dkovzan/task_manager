package com.kovzan.task_manager.dao;

import com.kovzan.task_manager.connection.DBConnection;
import com.kovzan.task_manager.entity.Entity;
import com.kovzan.task_manager.logger.LogConstant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DaoBase<T extends Entity> {

	private static final Logger logger = Logger.getLogger(DaoBase.class.getName());

	public int add(T element, String addQuery) throws SQLException {
		int result = -1;
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(addQuery, Statement.RETURN_GENERATED_KEYS);
			fillPreparedStatementForAdd(element, statement);
			statement.executeUpdate();
			ResultSet keys = statement.getGeneratedKeys();
			if (keys.next()) {
				result = keys.getInt(1);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			}
		}
		return result;
	}

	public void remove(T element, String removeQuery) throws SQLException {
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(removeQuery);
			statement.setInt(1, element.getId());
			statement.execute();
			logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		}
	}

	public void update(T element, String updateQuery) throws SQLException {
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(updateQuery);
			fillPreparedStatementForUpdate(element, statement);
			statement.executeUpdate();
			logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		}
	}
	public List<T> findAll(String findAllQuery) throws SQLException {
		List<T> entities = new ArrayList<>();
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(findAllQuery);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				entities = createEntitiesFromResultSet(resultSet);
			}
			logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
		}
		return entities;
	}

	public T findById(int id, String findByIdQuery) throws SQLException {
		T entity = null;
		try (Connection connection = DBConnection.getDBConnection()) {
			PreparedStatement statement = connection.prepareStatement(findByIdQuery);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet != null && resultSet.next()) {
				entity = createEntitiesFromResultSet(resultSet).get(0);
				logger.log(Level.INFO, LogConstant.SUCCESSFUL_EXECUTE);
			} else {
				logger.log(Level.INFO, LogConstant.ENTITY_NOT_FOUND, id);
			}
		}
		return entity;
	}

	abstract protected List<T> createEntitiesFromResultSet(ResultSet resultSet) throws SQLException;

	abstract protected PreparedStatement fillPreparedStatementForAdd(T element, PreparedStatement statement) throws  SQLException;

	abstract protected PreparedStatement fillPreparedStatementForUpdate(T element, PreparedStatement statement) throws  SQLException;

}
