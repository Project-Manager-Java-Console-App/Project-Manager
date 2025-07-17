package org.example.Ui.ProjectCommands;

import org.example.Service.ProjectUserService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;

import java.util.List;

public class DisplayAllUsersByProjectCommand implements Command {
    private final ProjectUserService projectUserService;
    private final SessionManager sessionManager;

    public DisplayAllUsersByProjectCommand(SessionManager sessionManager, ProjectUserService projectUserService) {
        this.projectUserService = projectUserService;
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean execute() {
        Project project = sessionManager.getCurrentProject();
        if (project == null) {
            System.err.println("Project is required");
            return true;
        }
        System.out.println("Displaying all users assigned to project: " + project.getName());

        List<Integer> users = projectUserService.getUsersAssignedToProject(project.getId());
        System.out.println("Users ids: ");
        for (Integer user_id : users) {
            System.out.println(user_id);
        }
        return true;
    }
}
