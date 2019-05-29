package com.kovzan.task_manager.command.impl.employee;

public class EmployeeValidator {
	
	private static final String CHECK_EMPLOYEE_FIRSTNAME_REGEX = "[\\D\\d]{1,255}";
	private static final String CHECK_EMPLOYEE_LASTNAME_REGEX = "[\\D\\d]{1,255}";
	private static final String CHECK_EMPLOYEE_MIDDLENAME_REGEX = "[\\D\\d]{0,255}";
	private static final String CHECK_EMPLOYEE_POSITION_REGEX = "[\\D\\d]{1,255}";
	
	public static boolean isEmployeeFirstNameValid(String firstName) {
		return firstName.matches(CHECK_EMPLOYEE_FIRSTNAME_REGEX);
	}
	
	public static boolean isEmployeeLastNameValid(String lastName) {
		return lastName.matches(CHECK_EMPLOYEE_LASTNAME_REGEX);
	}
	
	public static boolean isEmployeeMiddleNameValid(String middleName) {
		return middleName.matches(CHECK_EMPLOYEE_MIDDLENAME_REGEX);
	}
	
	public static boolean isEmployeePositionValid(String position) {
		return position.matches(CHECK_EMPLOYEE_POSITION_REGEX);
	}

}
