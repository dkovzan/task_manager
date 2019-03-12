package com.kovzan.task_manager.logger;

public class LogConstant {

	public final static String INDENT = "  ";
	public final static String OBJECT_CREATED = INDENT + "Created object: ";
	public final static String SUCCESSFUL_EXECUTE = INDENT + "execute result: SUCCESSFULL";
	public final static String EXCEPTION = INDENT + "method throws exception: ";
	public final static String DRIVER_INSTALLED_SUCCESSFULLY = INDENT + "driver installed successfully";
	public final static String DRIVER_INSTALLATION_FAILED = INDENT + "driver installation failed";

	private LogConstant() {}
}
