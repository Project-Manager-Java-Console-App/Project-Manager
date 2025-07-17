package org.example.Ui.UserCommands;

import org.example.Service.ProjectUserService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.model.Users;

import java.util.List;

public class DisplayAllProjectsCreatedByUserCommand implements Command {
    private final ProjectUserService projectUserService;
    private final SessionManager sessionManager;

    public DisplayAllProjectsCreatedByUserCommand(SessionManager sessionManager, ProjectUserService projectUserService) {
        this.projectUserService = projectUserService;
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean execute() {
        Users users = sessionManager.getCurrentUser();
        if (users == null) {
            System.err.println("Users is null");
            return true;
        }
        System.out.println("Displaying all projects created by user: " + users);

        try {
            List<Project> project_ids = projectUserService.getAllProjectsCreatedByUser(users.getId());
            if (project_ids.isEmpty()) {
                System.out.println("No projects assigned to user: " + users);
            } else {
                System.out.println("Projects ids: ");
                for (Project project : project_ids) {
                    System.out.println(project);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to display all projects created by user: " + e.getMessage());
        }
        return true;
    }
}
