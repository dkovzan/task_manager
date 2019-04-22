package com.kovzan.task_manager.command;

import java.util.HashMap;

import com.kovzan.task_manager.entity.Entity;

public class ValidationException extends Exception {
	
	private Entity entity;
	private String message;
	private HashMap<String, String> invalidFields;
	
	public ValidationException(String message, Entity entity, HashMap<String, String> invalidFields) {
		super();
		this.message = message;
		this.entity = entity;
		this.invalidFields = invalidFields;
	}
	
	public HashMap<String, String> getInvalidFields() {
		return this.invalidFields;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public Entity getEntity() {
		return this.entity;
	}
	
	public void setInvalidFields(HashMap<String, String> invalidFields) {
		this.invalidFields = invalidFields;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

}
