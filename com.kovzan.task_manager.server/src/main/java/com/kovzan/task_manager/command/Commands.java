package com.kovzan.task_manager.command;

import com.kovzan.task_manager.command.impl.employee.*;
import com.kovzan.task_manager.command.impl.project.*;
import com.kovzan.task_manager.command.impl.task.*;
import com.kovzan.task_manager.command.impl.task.runtime.AddRuntimeTask;
import com.kovzan.task_manager.command.impl.task.runtime.PrintEditRuntimeTask;
import com.kovzan.task_manager.command.impl.task.runtime.RemoveRuntimeTask;
import com.kovzan.task_manager.command.impl.task.runtime.UpdateRuntimeTask;

public enum Commands {

	ADD_PROJECT(new AddProjectCommand()),
	UPDATE_PROJECT(new UpdateProjectCommand()),
	REMOVE_PROJECT(new RemoveProjectCommand()),
	PRINT_PROJECTS(new PrintProjectsCommand()),
	PRINT_EDIT_PROJECT(new PrintEditProjectCommand()),

	ADD_TASK(new AddTaskCommand()),
	UPDATE_TASK(new UpdateTaskCommand()),
	REMOVE_TASK(new RemoveTaskCommand()),
	PRINT_TASKS(new PrintTasksCommand()),
	PRINT_EDIT_TASK(new PrintEditTaskCommand()),
	
	PRINT_EDIT_RUNTIME_TASK(new PrintEditRuntimeTask()),
	ADD_RUNTIME_TASK(new AddRuntimeTask()),
	UPDATE_RUNTIME_TASK(new UpdateRuntimeTask()),
	REMOVE_RUNTIME_TASK(new RemoveRuntimeTask()),

	ADD_EMPLOYEE(new AddEmployeeCommand()),
	UPDATE_EMPLOYEE(new UpdateEmployeeCommand()),
	REMOVE_EMPLOYEE(new RemoveEmployeeCommand()),
	PRINT_EMPLOYEES(new PrintEmployeesCommand()),
	PRINT_EDIT_EMPLOYEE(new PrintEditEmployeeCommand());

	private Command commandType;

	Commands(Command commandType) {
		this.commandType = commandType;
	}

	public Command getCommand() {
		return commandType;
	}
	
	public static boolean contains(String givenCommand) {
		
		for (Commands c : Commands.values()) {
			if (c.name().equals(givenCommand)) {
				return true;
			}
		}
		return false;
	}
}
