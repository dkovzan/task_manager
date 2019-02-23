package com.kovzan.task_manager.command.impl.Project;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.entities.Project;
import com.kovzan.task_manager.service.ProjectService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class RemoveProjectCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {

        try {
            int projectId = Integer.parseInt(request.getParameter(ParameterNameConstant.PROJECT_ID));
            Project project = new Project(projectId);
            ProjectService.removeProject(project);
            List<Project> projects = ProjectService.findAllProjects();
            request.setAttribute(ParameterNameConstant.PRINTED_PROJECTS, projects);
        } catch (SQLException e) {
            System.out.println(e);
            return PageConstant.ERROR_PAGE;
        }
        return PageConstant.PROJECTS_PAGE;
    }
}
