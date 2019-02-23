package com.kovzan.task_manager.command.impl.Project;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.entities.Project;
import com.kovzan.task_manager.service.ProjectService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class PrintProjectsCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {

        List<Project> projects;
        try {
            projects = ProjectService.findAllProjects();
            request.setAttribute(ParameterNameConstant.PRINTED_PROJECTS, projects);
        } catch (SQLException e) {
            System.out.println(e);
            return PageConstant.ERROR_PAGE;
        }
        return PageConstant.PROJECTS_PAGE;
    }
}
