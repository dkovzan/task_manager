package com.kovzan.task_manager.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Task extends Entity implements Serializable {

	private String name;
	private Integer work;
	private LocalDate beginDate;
	private LocalDate endDate;
	private Integer projectId;
	private String projectShortName;
	private Integer employeeId;
	private String employeeFullName;
	private TaskStatus status;

	@Override
	public String toString() {
		return "Task{" +
				"name='" + name + '\'' +
				", estimate=" + work +
				", createdOn=" + beginDate +
				", finishedOn=" + endDate +
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
		if (!work.equals(task.work)) return false;
		if (!beginDate.equals(task.beginDate)) return false;
		if (!endDate.equals(task.endDate)) return false;
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
		result = 31 * result + work.hashCode();
		result = 31 * result + beginDate.hashCode();
		result = 31 * result + endDate.hashCode();
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

	public Integer getWork() {
		return work;
	}

	public void setWork(Integer work) {
		this.work = work;
	}

	public LocalDate getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(LocalDate beginDate) {
		this.beginDate = beginDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
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
