package com.kovzan.task_manager.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Task extends Entity implements Serializable {

	private String name;
	private Integer estimate;
	private LocalDate createdOn;
	private LocalDate finishedOn;
	private Integer projectId;
	private String projectShortName;
	private Integer employeeId;
	private String employeeFullName;
	private TaskStatus status;

	@Override
	public String toString() {
		return "Task{" +
				"name='" + name + '\'' +
				", estimate=" + estimate +
				", createdOn=" + createdOn +
				", finishedOn=" + finishedOn +
				", projectId=" + projectId +
				", projectShortName='" + projectShortName + '\'' +
				", employeeId=" + employeeId +
				", employeeFullName='" + employeeFullName + '\'' +
				", status=" + status +
				", id=" + id +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Task task = (Task) o;

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
		return status == task.status;
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + estimate.hashCode();
		result = 31 * result + createdOn.hashCode();
		result = 31 * result + finishedOn.hashCode();
		result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
		result = 31 * result + (projectShortName != null ? projectShortName.hashCode() : 0);
		result = 31 * result + (employeeId != null ? employeeId.hashCode() : 0);
		result = 31 * result + (employeeFullName != null ? employeeFullName.hashCode() : 0);
		result = 31 * result + status.hashCode();
		return result;
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

	public LocalDate getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDate createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDate getFinishedOn() {
		return finishedOn;
	}

	public void setFinishedOn(LocalDate finishedOn) {
		this.finishedOn = finishedOn;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectShortName() {
		return projectShortName;
	}

	public void setProjectShortName(String projectShortName) {
		this.projectShortName = projectShortName;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeFullName() {
		return employeeFullName;
	}

	public void setEmployeeFullName(String employeeFullName) {
		this.employeeFullName = employeeFullName;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}
}
