package org.example.ui.projectCommands;

import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.service.ProjectUserService;
import org.example.ui.Command;

import java.util.List;

public class DisplayAllUsersByProjectCommand implements Command {
    private final ProjectUserService projectUserService;


    public DisplayAllUsersByProjectCommand(ProjectUserService projectUserService) {
        this.projectUserService = projectUserService;

    }

    @Override
    public boolean execute() {
        Project project = SessionManager.getInstance().getCurrentProject();
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
