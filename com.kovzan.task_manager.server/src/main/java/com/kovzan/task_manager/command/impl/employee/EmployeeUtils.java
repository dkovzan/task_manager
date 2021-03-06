package com.kovzan.task_manager.command.impl.employee;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

import com.kovzan.task_manager.command.ValidationException;
import com.kovzan.task_manager.command.impl.parameters.EmployeeParams;
import com.kovzan.task_manager.entity.Employee;

public class EmployeeUtils {

	private static final String INCORRECT_DATA_MESSAGE = "Incorrect data are entered.";

	public static Employee buildEmployee(HttpServletRequest request) throws ValidationException {
		
		Employee employee = new Employee();
		HashMap<String, String> invalidFields = new HashMap<>();
		
		Integer id;
		if (request.getParameter(EmployeeParams.EMPLOYEE_ID) != null) {
			id = Integer.parseInt(request.getParameter(EmployeeParams.EMPLOYEE_ID));
			employee.setId(id);
		}
		
		String firstName = request.getParameter(EmployeeParams.EMPLOYEE_FIRSTNAME).trim();
		if (EmployeeValidator.isEmployeeFirstNameValid(firstName)) {
			employee.setFirstName(firstName);
		} else {
			invalidFields.put(EmployeeParams.EMPLOYEE_FIRSTNAME, firstName);
		}
		
		String lastName = request.getParameter(EmployeeParams.EMPLOYEE_LASTNAME).trim();
		if (EmployeeValidator.isEmployeeLastNameValid(lastName)) {
			employee.setLastName(lastName);
		} else {
			invalidFields.put(EmployeeParams.EMPLOYEE_LASTNAME, lastName);
		}
		
		String middleName = request.getParameter(EmployeeParams.EMPLOYEE_MIDDLENAME).trim();
		if (EmployeeValidator.isEmployeeMiddleNameValid(middleName)) {
			employee.setMiddleName(middleName);
		} else {
			invalidFields.put(EmployeeParams.EMPLOYEE_MIDDLENAME, middleName);
		}
		
		String position = request.getParameter(EmployeeParams.EMPLOYEE_POSITION).trim();
		if (EmployeeValidator.isEmployeePositionValid(position)) {
			employee.setPosition(position);
		} else {
			invalidFields.put(EmployeeParams.EMPLOYEE_POSITION, position);
		}
		
		if (!invalidFields.isEmpty()) {
			throw new ValidationException(INCORRECT_DATA_MESSAGE, employee, invalidFields);
		}

		return employee;
	}

}
