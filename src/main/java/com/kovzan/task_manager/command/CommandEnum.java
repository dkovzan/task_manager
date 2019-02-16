package com.kovzan.task_manager.command;

import com.kovzan.task_manager.command.impl.Employee.*;

public enum CommandEnum {

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
