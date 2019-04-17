package com.kovzan.task_manager.entity;

import java.io.Serializable;

public class Project extends Entity implements Serializable {

	private String name;
	private String shortName;
	private String description;

	@Override
	public String toString() {
		return "Project{" +
				"name='" + name + '\'' +
				", shortName='" + shortName + '\'' +
				", description='" + description + '\'' +
				", id=" + id +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Project project = (Project) o;

		if (!name.equals(project.name)) return false;
		if (!shortName.equals(project.shortName)) return false;
		return description != null ? description.equals(project.description) : project.description == null;
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + shortName.hashCode();
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
