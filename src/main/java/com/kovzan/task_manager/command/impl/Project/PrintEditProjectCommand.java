package com.kovzan.task_manager.command.impl.Project;

import com.kovzan.task_manager.command.Command;
import com.kovzan.task_manager.command.PageConstant;
import com.kovzan.task_manager.command.ParameterNameConstant;
import com.kovzan.task_manager.entities.Project;
import com.kovzan.task_manager.service.ProjectService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class PrintEditProjectCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {

        int projectId = Integer.parseInt(request.getParameter(ParameterNameConstant.PROJECT_ID));
        if (projectId == -1) {
            request.setAttribute(ParameterNameConstant.IS_ADD_FORM, 1);
        } else {
            Project project;
            try {
                project = ProjectService.findProjectById(projectId);
                request.setAttribute(ParameterNameConstant.PRINTED_EDIT_PROJECT, project);
                request.setAttribute(ParameterNameConstant.IS_ADD_FORM, 0);
            } catch (SQLException e) {
                System.out.println(e);
                return PageConstant.ERROR_PAGE;
            }
        }
        return PageConstant.EDIT_PROJECT_PAGE;
    }
}
