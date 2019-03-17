package com.kovzan.task_manager.logger;

public class LogConstant {

	public final static String INDENT = "  ";
	public final static String OBJECT_CREATED = INDENT + "Created object: ";
	public final static String TASK_NOT_FOUND = INDENT + "task with parameter %s: %s is not found";
	public final static String EMPLOYEE_NOT_FOUND = INDENT + "employee with parameter %s: %s is not found";
	public final static String SUCCESSFUL_EXECUTE = INDENT + "Execute result: SUCCESSFULL";
	public final static String EXCEPTION = INDENT + "Method throws exception: ";
	public final static String DRIVER_INSTALLED_SUCCESSFULLY = INDENT + "Driver installed successfully";
	public final static String DRIVER_INSTALLATION_FAILED = INDENT + "Driver installation failed";

	private LogConstant() {}
}
