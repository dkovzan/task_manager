package com.kovzan.task_manager.dao;

import java.sql.SQLException;

public class DaoException extends SQLException{

	public DaoException() {
		super();
	}
	public DaoException(String message) {
		super(message);
	}
	public DaoException(Throwable cause) {
		super(cause);
	}
	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}
}
