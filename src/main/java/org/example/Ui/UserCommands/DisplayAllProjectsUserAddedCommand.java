package org.example.Ui.UserCommands;

import org.example.Exceptions.UserIdNotFound;
import org.example.Service.ProjectUserService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Users;

import java.util.List;

public class DisplayAllProjectsUserAddedCommand implements Command {
    private final ProjectUserService projectUserService;

    public DisplayAllProjectsUserAddedCommand(ProjectUserService projectUserService) {
        this.projectUserService = projectUserService;
    }

    @Override
    public void execute()  {
        Users user = SessionManager.getCurrentUser();
        if (user == null){
            System.err.println("User is required");
            return;
        }
        System.out.println("Displaying all projects in which the user: " + user.getUsername()+" is added.");

        System.out.println("Projects added to: ");
        try {
            List<Integer> project_id = projectUserService.getAllProjectsAssignedToUser(user.getId());
            if(project_id.isEmpty()){
                System.out.println("No projects assigned to user: " + user.getUsername());
            }else {
                for (Integer id : project_id) {
                    System.out.println(id);
                }
            }
        }catch (UserIdNotFound e){
            throw new UserIdNotFound();
        }
    }
}
