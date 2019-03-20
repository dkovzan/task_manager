package com.kovzan.task_manager.entity;

public enum TaskStatusesEnum {

	NEW,
	IN_PROGRESS,
	RESOLVED,
	IN_TESTING,
	REOPENED,
	CLOSED;

	private String statusName;

	static {
		NEW.setStatusName("New");
		IN_PROGRESS.setStatusName("In progress");
		RESOLVED.setStatusName("Resolved");
		IN_TESTING.setStatusName("In testing");
		REOPENED.setStatusName("Reopened");
		CLOSED.setStatusName("Closed");
	}

	public String getStatusName() {
		return statusName;
	}

	void setStatusName(String statusName) {
		this.statusName = statusName;
	}

}
