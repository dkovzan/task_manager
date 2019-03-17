package com.kovzan.task_manager.service;

import com.kovzan.task_manager.entity.Status;

import java.util.ArrayList;
import java.util.List;

public class StatusService {

	public static final StatusService instance = new StatusService();

	private StatusService() {}

	public static StatusService getInstance() {
		return instance;
	}

	public static List<Status> findAllStatuses() {
		ArrayList<Status> statusesList = new ArrayList<>();
		statusesList.add(new Status(0, "New"));
		statusesList.add(new Status(1,"In progress"));
		statusesList.add(new Status(2, "Resolved"));
		statusesList.add(new Status (3, "In testing"));
		statusesList.add(new Status(4, "Closed"));
		statusesList.add(new Status(5, "Reopened"));
		return statusesList;
	}
}
