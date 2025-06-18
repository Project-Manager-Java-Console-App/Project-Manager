package org.example.Ui.ProjectCommands;

import org.example.Service.ProjectUserService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;

import java.util.List;

public class DisplayAllUsersByProject implements Command {
    private final ProjectUserService projectUserService;

    public DisplayAllUsersByProject(ProjectUserService projectUserService) {
        this.projectUserService = projectUserService;
    }

    @Override
    public boolean execute() {
        Project project = SessionManager.getCurrentProject();
        if (project==null){
            System.err.println("Project is required");
            return true;
        }
        System.out.println("Displaying all users assigned to project: "+project.getName());

        try{
            List<Integer> users = projectUserService.getUsersAssignedToProject(project.getId());
            System.out.println("Users ids: ");
            for (Integer user_id : users) {
                System.out.println(user_id);
            }
        }catch (Exception e){
            System.err.println("Failed to display all users assigned to project: "+project.getName());
        }
        return true;
    }
}
