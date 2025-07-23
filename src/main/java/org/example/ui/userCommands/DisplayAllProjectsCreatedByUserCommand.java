package org.example.ui.userCommands;

import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.model.Users;
import org.example.service.ProjectUserService;
import org.example.ui.Command;

import java.util.List;

public class DisplayAllProjectsCreatedByUserCommand implements Command {
    private final ProjectUserService projectUserService;


    public DisplayAllProjectsCreatedByUserCommand(ProjectUserService projectUserService) {
        this.projectUserService = projectUserService;
    }

    @Override
    public boolean execute() {
        Users users = SessionManager.getInstance().getCurrentUser();
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
