package org.example.Ui.ProjectCommands;

import org.example.Repository.ProjectUserRepository;
import org.example.Service.ProjectUserService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.model.Users;

import java.util.List;

public class DisplayAllUsersByProject implements Command {
    private final ProjectUserService projectUserService;

    public DisplayAllUsersByProject(ProjectUserService projectUserService) {
        this.projectUserService = projectUserService;
    }

    @Override
    public void execute() {
        Project project = SessionManager.getCurrentProject();
        if (project==null){
            System.err.println("Project is required");
            return;
        }
        System.out.println("Displaying all users assigned to project: "+project.getName());

        try{
            List<Users> users = projectUserService.getUsersAssignedToProject(project.getId());
            for (Users user : users) {
                System.out.println(user);
            }
        }catch (Exception e){
            System.err.println("Failed to display all users assigned to project: "+project.getName());
        }
    }
}
