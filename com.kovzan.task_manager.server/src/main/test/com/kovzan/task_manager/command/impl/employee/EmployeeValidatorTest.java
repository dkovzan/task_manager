package com.kovzan.task_manager.command.impl.employee;

import org.junit.Assert;
import org.junit.Test;

public class EmployeeValidatorTest {
	
	@Test
	public void whenEmployeeHasFirstNameWithSpaceThenValueIsValid() {
		Assert.assertTrue(EmployeeValidator.isEmployeeFirstNameValid("T t"));
	}
	
	@Test
	public void whenEmployeeHasLastNameWithSpaceThenValueIsValid() {
		Assert.assertTrue(EmployeeValidator.isEmployeeLastNameValid("T t"));
	}
	
	@Test
	public void whenEmployeeHasPositionWithSpaceThenValueIsValid() {
		Assert.assertTrue(EmployeeValidator.isEmployeePositionValid("T t"));
	}
	
	@Test
	public void whenEmployeeHasEmptyNameThenValueIsInvalid() {
		Assert.assertFalse(EmployeeValidator.isEmployeeFirstNameValid(""));
	}
	
	@Test
	public void whenEmployeeHasLongerFirstNameThenValueIsInvalid() {
		String name = String.format("%0" + 256 + "d", 0).replace('0', 'a');
		Assert.assertFalse(EmployeeValidator.isEmployeeFirstNameValid(name));
	}
	
	@Test
	public void whenEmployeeHasEmptyLastNameThenValueIsInvalid() {
		Assert.assertFalse(EmployeeValidator.isEmployeeLastNameValid(""));
	}
	
	@Test
	public void whenEmployeeHasEmptyMiddleNameThenValueIsValid() {
		Assert.assertTrue(EmployeeValidator.isEmployeeMiddleNameValid(""));
	}
	
	@Test
	public void whenEmployeeHasEmptyPositionThenValueIsInvalid() {
		Assert.assertFalse(EmployeeValidator.isEmployeePositionValid(""));
	}
	
}