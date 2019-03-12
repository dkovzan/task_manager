package com.kovzan.task_manager.exception;

import java.sql.SQLException;

public class DAOException extends SQLException{

	public DAOException() {
		super();
	}
	public DAOException(String message) {
		super(message);
	}
	public DAOException(Throwable cause) {
		super(cause);
	}
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}
}
