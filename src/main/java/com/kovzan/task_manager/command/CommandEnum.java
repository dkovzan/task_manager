package com.kovzan.task_manager.command;

import com.kovzan.task_manager.command.impl.Employee.*;
import com.kovzan.task_manager.command.impl.Project.*;

public enum CommandEnum {

    ADD_PROJECT(new AddProjectCommand()),
    UPDATE_PROJECT(new UpdateProjectCommand()),
    REMOVE_PROJECT(new RemoveProjectCommand()),
    PRINT_PROJECTS(new PrintProjectsCommand()),
    PRINT_EDIT_PROJECT(new PrintEditProjectCommand()),

    ADD_EMPLOYEE(new AddEmployeeCommand()),
    UPDATE_EMPLOYEE(new UpdateEmployeeCommand()),
    REMOVE_EMPLOYEE(new RemoveEmployeeCommand()),
    PRINT_EMPLOYEES(new PrintEmployeesCommand()),
    PRINT_EDIT_EMPLOYEE(new PrintEditEmployeeCommand());

    private Command commandType;

    CommandEnum (Command commandType) {
        this.commandType = commandType;
    }

    public Command getCommand() {
        return commandType;
    }
}
