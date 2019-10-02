package com.kovzan.task_manager.dao;

import com.kovzan.task_manager.connection.ConnectionService;
import com.kovzan.task_manager.entity.Entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class DaoBase<T extends Entity> {

	private static final Logger logger = Logger.getLogger(DaoBase.class.getName());
	
	protected abstract String getAddSqlStatement();
	
	protected abstract String getRemoveSqlStatement();
	
	protected abstract String getUpdateSqlStatement();
	
	protected abstract String getFindAllSqlStatement();
	
	protected abstract String getFindByIdSqlStatement();
	
	protected abstract List<T> createEntitiesFromResultSet(ResultSet resultSet) throws DaoException;
	
	protected abstract PreparedStatement fillPreparedStatementForAdd(T element, PreparedStatement statement) throws  DaoException;
	
	protected abstract PreparedStatement fillPreparedStatementForUpdate(T element, PreparedStatement statement) throws  DaoException;

	public int add(T element) throws DaoException {
		try (Connection connection = ConnectionService.getInstance().getConnection()) {
			PreparedStatement statement = connection.prepareStatement(getAddSqlStatement(), Statement.RETURN_GENERATED_KEYS);
			fillPreparedStatementForAdd(element, statement);
			statement.executeUpdate();
			int createdEntityId = getCreatedEntityId(statement);
			logger.info(element.toString() + " is successfully added with id " + createdEntityId);
			return createdEntityId;
		} catch (SQLException e) {
			throw new DaoException("Error while adding entity: ", e);
		}
	}

	public void remove(T element) throws DaoException {
		try (Connection connection = ConnectionService.getInstance().getConnection()) {
			PreparedStatement statement = connection.prepareStatement(getRemoveSqlStatement());
			statement.setInt(1, element.getId());
			statement.execute();
			logger.info(element.toString() + " is successfully deleted");
		} catch (SQLException e) {
			throw new DaoException("Error while removing entity: ", e);
		}
	}

	public void update(T element) throws DaoException {
		try (Connection connection = ConnectionService.getInstance().getConnection()) {
			PreparedStatement statement = connection.prepareStatement(getUpdateSqlStatement());
			fillPreparedStatementForUpdate(element, statement);
			statement.executeUpdate();
			logger.info(element.toString() + " is successfully updated");
		} catch (SQLException e) {
			throw new DaoException("Error while updating entity: ", e);
		}
	}
	public List<T> findAll() throws DaoException {
		List<T> entities = new ArrayList<>();
		try (Connection connection = ConnectionService.getInstance().getConnection()) {
			PreparedStatement statement = connection.prepareStatement(getFindAllSqlStatement());
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				entities = createEntitiesFromResultSet(resultSet);
			}
			logger.info(!entities.isEmpty() ?
					(entities.size() + " entities (" + entities.get(0).getClass().getSimpleName() + ") are successfully found") :
					"Entities are not found");
		} catch (SQLException e) {
			throw new DaoException("Error while getting entities: ", e);
		}
		return entities;
	}

	public T findById(int id) throws DaoException {
		T entity = null;
		try (Connection connection = ConnectionService.getInstance().getConnection()) {
			PreparedStatement statement = connection.prepareStatement(getFindByIdSqlStatement());
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet != null && resultSet.next()) {
				entity = createEntitiesFromResultSet(resultSet).get(0);
				logger.info(entity.toString() + " is successfully found");
			} else {
				logger.info(String.format("Entity was not found by id %s", id));
			}
		} catch (SQLException e) {
			throw new DaoException("Error while getting entity by id: ", e);
		}
		return entity;
	}
	
	private int getCreatedEntityId(PreparedStatement statement) throws DaoException {
		int result = -1;
		try {
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				result = generatedKeys.getInt(1);
			}
		} catch (SQLException e) {
			throw new DaoException("Error while getting generated entity id: ", e);
		}
		return result;
	}

}
