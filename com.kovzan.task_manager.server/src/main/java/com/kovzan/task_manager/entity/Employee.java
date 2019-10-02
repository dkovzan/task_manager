package com.kovzan.task_manager.entity;

import java.io.Serializable;

public class Employee extends Entity implements Serializable {

	private String firstName;
	private String lastName;
	private String middleName;
	private String position;

	@Override
	public String toString() {
		return "Employee{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", middleName='" + middleName + '\'' +
				", position='" + position + '\'' +
				", id=" + getId() +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Employee employee = (Employee) o;

		if (!firstName.equals(employee.firstName)) return false;
		if (!lastName.equals(employee.lastName)) return false;
		if (middleName != null ? !middleName.equals(employee.middleName) : employee.middleName != null) return false;
		return position.equals(employee.position);
	}

	@Override
	public int hashCode() {
		int result = firstName.hashCode();
		result = 31 * result + lastName.hashCode();
		result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
		result = 31 * result + position.hashCode();
		return result;
	}

	public Integer getId() {
		return super.getId();
	}
	
	public void setId(Integer id) {
		super.setId(id);
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}
