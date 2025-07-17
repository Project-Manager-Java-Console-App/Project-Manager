package org.example.Ui.UserCommands;

import org.example.Service.ProjectUserService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Users;

import java.util.List;

public class DisplayAllProjectsUserAddedCommand implements Command {
    private final ProjectUserService projectUserService;
    private final SessionManager sessionManager;

    public DisplayAllProjectsUserAddedCommand(SessionManager sessionManager, ProjectUserService projectUserService) {
        this.projectUserService = projectUserService;
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean execute() {
        Users user = sessionManager.getCurrentUser();
        if (user == null) {
            System.err.println("User is empty");
            return true;
        }
        System.out.println("Displaying all projects in which the user: " + user.getName() + " is added.");

        System.out.println("Projects added to: ");
        List<Integer> project_id = projectUserService.getAllProjectsWhereUserIsAdded(user.getId());
        if (project_id.isEmpty()) {
            System.out.println("No projects assigns user: " + user.getName());
        } else {
            for (Integer id : project_id) {
                System.out.println(id);
            }
        }
        return true;
    }
}
