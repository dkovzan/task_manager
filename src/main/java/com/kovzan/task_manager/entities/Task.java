package com.kovzan.task_manager.entities;

import java.util.Objects;

public class Task implements Entity {

    private Integer id;
    private String name;
    private Integer estimate;
    private String createdOn;
    private String finishedOn;
    private Integer projectId;
    private Integer employeeId;
    private Integer statusId;

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

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", estimate=" + estimate +
                ", createdOn='" + createdOn + '\'' +
                ", finishedOn='" + finishedOn + '\'' +
                ", projectId=" + projectId +
                ", employeeId=" + employeeId +
                ", statusId=" + statusId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
                name.equals(task.name) &&
                estimate.equals(task.estimate) &&
                createdOn.equals(task.createdOn) &&
                finishedOn.equals(task.finishedOn) &&
                projectId.equals(task.projectId) &&
                employeeId.equals(task.employeeId) &&
                statusId.equals(task.statusId);
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

}
