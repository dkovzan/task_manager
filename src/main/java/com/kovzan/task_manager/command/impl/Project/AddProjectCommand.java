package com.kovzan.task_manager.command.impl.Project;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.impl.Project.Creator.ProjectCreator;
import com.kovzan.task_manager.entities.Project;

import javax.servlet.http.HttpServletRequest;

public class AddProjectCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {

        Project project = ProjectCreator.createProjectFromRequest(request);

    }
}
