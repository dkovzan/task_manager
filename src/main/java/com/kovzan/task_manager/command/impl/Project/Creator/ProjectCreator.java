package com.kovzan.task_manager.command.impl.Project.Creator;

import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.entities.Project;

import javax.servlet.http.HttpServletRequest;

public class ProjectCreator {

    public static Project createProjectFromRequest(HttpServletRequest request) {

        String projectName = request.getParameter(ParameterNameConstant.PROJECT_NAME);
        String projectShortName = request.getParameter(ParameterNameConstant.PROJECT_SHORTNAME);
        String projectDescription = request.getParameter(ParameterNameConstant.PROJECT_DESCRIPTION);
        return new Project(projectName, projectShortName, projectDescription);
    }

    public static Project createProjectWithIdFromRequest(HttpServletRequest request) {

        Project project = createProjectFromRequest(request);
        try {
            int projectId = Integer.parseInt(request.getParameter(ParameterNameConstant.PROJECT_ID));
            project.setId(projectId);
        } catch (Exception e) {
            System.out.println(e);
        }
        return project;
    }
}
