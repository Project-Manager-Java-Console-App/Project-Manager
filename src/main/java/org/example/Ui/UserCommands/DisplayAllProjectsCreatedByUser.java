package org.example.Ui.UserCommands;

import org.example.Exceptions.UserIdNotFound;
import org.example.Service.ProjectUserService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Users;
import java.util.List;

public class DisplayAllProjectsCreatedByUser implements Command {
    private final ProjectUserService projectUserService;

    public DisplayAllProjectsCreatedByUser(ProjectUserService projectUserService) {
        this.projectUserService = projectUserService;
    }

    @Override
    public void execute()  {
        Users users = SessionManager.getCurrentUser();
        if (users == null){
            System.err.println("User is required");
            return;
        }
        System.out.println("Displaying all projects created by user: " + users);

        try{
            List<Integer> project_ids = projectUserService.getAllProjectsCreatedByUser(users.getId());
            if (project_ids.isEmpty()) {
                System.out.println("No projects assigned to user: " + users);
            }else {
                System.out.println("Projects ids: ");
                for (Integer project_id : project_ids) {
                    System.out.println(project_id);
                }
            }
        }catch (Exception e){
            throw new UserIdNotFound();
        }

    }
}
