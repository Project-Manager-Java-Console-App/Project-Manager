package org.example.Ui.UserCommands;

import org.example.Exceptions.UserIdNotFound;
import org.example.Exceptions.UserNotFound;
import org.example.Service.ProjectUserService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.model.Users;
import java.util.List;

public class DisplayAllProjectsCreatedByUser implements Command {
    private final ProjectUserService projectUserService;

    public DisplayAllProjectsCreatedByUser(ProjectUserService projectUserService) {
        this.projectUserService = projectUserService;
    }

    @Override
    public boolean execute()  {
        Users users = SessionManager.getCurrentUser();
        if (users == null){
            throw new UserNotFound("User is empty");
        }
        System.out.println("Displaying all projects created by user: " + users);

        try{
            List<Project> project_ids = projectUserService.getAllProjectsCreatedByUser(users.getId());
            if (project_ids.isEmpty()) {
                System.out.println("No projects assigned to user: " + users);
            }else {
                System.out.println("Projects ids: ");
                for (Project project : project_ids) {
                    System.out.println(project);
                }
            }
        }catch (Exception e){
            throw new UserIdNotFound();
        }
        return true;
    }
}
