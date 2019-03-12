package com.kovzan.task_manager.entities;

import java.util.Objects;

public class Task implements Entity {

	private Integer id;
	private String name;
	private Integer estimate;
	private String createdOn;
	private String finishedOn;
	private Integer projectId;
	private String projectShortName;
	private Integer employeeId;
	private String employeeFullName;
	private Integer statusId;
	private String statusName;

	public Task(Integer id) {
		this.id = id;
	}

	public Task(String name, Integer estimate, String createdOn, String finishedOn, Integer projectId, Integer employeeId, Integer statusId) {
		this.name = name;
		this.estimate = estimate;
		this.createdOn = createdOn;
		this.finishedOn = finishedOn;
		this.projectId = projectId;
		this.employeeId = employeeId;
		this.statusId = statusId;
	}

	public Task(Integer id, String name, Integer estimate, String createdOn, String finishedOn, Integer projectId, Integer employeeId, Integer statusId) {
		this.id = id;
		this.name = name;
		this.estimate = estimate;
		this.createdOn = createdOn;
		this.finishedOn = finishedOn;
		this.projectId = projectId;
		this.employeeId = employeeId;
		this.statusId = statusId;
	}

	public Task(Integer id, String name, Integer estimate, String createdOn, String finishedOn, String projectShortName, String employeeFullName, String statusName) {
		this.id = id;
		this.name = name;
		this.estimate = estimate;
		this.createdOn = createdOn;
		this.finishedOn = finishedOn;
		this.projectShortName = projectShortName;
		this.employeeFullName = employeeFullName;
		this.statusName = statusName;
	}

	@Override
	public String toString() {
		return "Task{" +
				"id=" + id +
				", name='" + name + '\'' +
				", estimate=" + estimate +
				", createdOn='" + createdOn + '\'' +
				", finishedOn='" + finishedOn + '\'' +
				", projectId=" + projectId +
				", projectShortName='" + projectShortName + '\'' +
				", employeeId=" + employeeId +
				", employeeFullName='" + employeeFullName + '\'' +
				", statusId=" + statusId +
				", statusName='" + statusName + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Task task = (Task) o;

		if (!id.equals(task.id)) return false;
		if (!name.equals(task.name)) return false;
		if (!estimate.equals(task.estimate)) return false;
		if (!createdOn.equals(task.createdOn)) return false;
		if (!finishedOn.equals(task.finishedOn)) return false;
		if (projectId != null ? !projectId.equals(task.projectId) : task.projectId != null) return false;
		if (projectShortName != null ? !projectShortName.equals(task.projectShortName) : task.projectShortName != null)
			return false;
		if (employeeId != null ? !employeeId.equals(task.employeeId) : task.employeeId != null) return false;
		if (employeeFullName != null ? !employeeFullName.equals(task.employeeFullName) : task.employeeFullName != null)
			return false;
		if (statusId != null ? !statusId.equals(task.statusId) : task.statusId != null) return false;
		return statusName != null ? statusName.equals(task.statusName) : task.statusName == null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getName(), getEstimate(), getCreatedOn(), getFinishedOn(), getProjectId(), getEmployeeId(), getStatusId());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getEstimate() {
		return estimate;
	}

	public void setEstimate(Integer estimate) {
		this.estimate = estimate;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getFinishedOn() {
		return finishedOn;
	}

	public void setFinishedOn(String finishedOn) {
		this.finishedOn = finishedOn;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getProjectShortName() {
		return projectShortName;
	}

	public void setProjectShortName(String projectShortName) {
		this.projectShortName = projectShortName;
	}

	public String getEmployeeFullName() {
		return employeeFullName;
	}

	public void setEmployeeFullName(String employeeFullName) {
		this.employeeFullName = employeeFullName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}


}
