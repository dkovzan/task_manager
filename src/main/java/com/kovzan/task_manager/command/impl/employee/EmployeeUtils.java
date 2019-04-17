package com.kovzan.task_manager.command.impl.employee;

import static com.kovzan.task_manager.logger.Log.logger;

import java.util.HashMap;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;

import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.command.ValidationException;
import com.kovzan.task_manager.entity.Employee;
import com.kovzan.task_manager.logger.LogConstant;

public class EmployeeUtils {
	
	private static final String INCORRECT_DATA_MESSAGE = "Incorrect data are entered.";
	
	public static Employee buildEmployee(HttpServletRequest request) throws ValidationException {
		
		Employee employee = new Employee();
		HashMap<String, String> invalidFields = new HashMap<String, String>();
		Integer id = null;
		if (request.getParameter(ParameterNameConstant.EMPLOYEE_ID) != null) {
			id = Integer.parseInt(request.getParameter(ParameterNameConstant.EMPLOYEE_ID));
			employee.setId(id);
		}
		String firstName = request.getParameter(ParameterNameConstant.EMPLOYEE_FIRSTNAME);
		if (EmployeeValidator.isEmployeeFirstNameValid(firstName)) {
			employee.setFirstName(firstName);
		} else {
			invalidFields.put(ParameterNameConstant.EMPLOYEE_FIRSTNAME, firstName);
		}
		String lastName = request.getParameter(ParameterNameConstant.EMPLOYEE_LASTNAME);
		if (EmployeeValidator.isEmployeeLastNameValid(lastName)) {
			employee.setLastName(lastName);
		} else {
			invalidFields.put(ParameterNameConstant.EMPLOYEE_LASTNAME, lastName);
		}
		String middleName = request.getParameter(ParameterNameConstant.EMPLOYEE_MIDDLENAME);
		if (EmployeeValidator.isEmployeeMiddleNameValid(middleName)) {
			employee.setMiddleName(middleName);
		} else {
			invalidFields.put(ParameterNameConstant.EMPLOYEE_MIDDLENAME, middleName);
		}
		String position = request.getParameter(ParameterNameConstant.EMPLOYEE_POSITION);
		if (EmployeeValidator.isEmployeePositionValid(position)) {
			employee.setPosition(position);
		} else {
			invalidFields.put(ParameterNameConstant.EMPLOYEE_POSITION, position);
		}
		if (!invalidFields.isEmpty()) {
			throw new ValidationException(INCORRECT_DATA_MESSAGE, employee, invalidFields);
		}
		logger.log(Level.INFO, LogConstant.OBJECT_CREATED + employee.toString());
		return employee;
	}

}
