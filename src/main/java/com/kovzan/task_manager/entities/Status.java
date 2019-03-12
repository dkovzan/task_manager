package com.kovzan.task_manager.entities;

import java.util.Objects;

public class Status implements Entity{

	private Integer id;
	private String name;

	public Status(Integer id) {
		this.id = id;
	}

	public Status(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Status{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Status status = (Status) o;
		return Objects.equals(getId(), status.getId()) &&
				Objects.equals(getName(), status.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getName());
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
}
