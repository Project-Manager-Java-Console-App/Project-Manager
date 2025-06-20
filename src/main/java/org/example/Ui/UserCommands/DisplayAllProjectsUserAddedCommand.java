package org.example.Ui.UserCommands;

import org.example.Exceptions.UserIdNotFound;
import org.example.Exceptions.UserNotFound;
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
    public boolean execute()  {
        Users user = SessionManager.getCurrentUser();
        if (user == null){
            throw new UserNotFound("User is empty");
        }
        System.out.println("Displaying all projects in which the user: " + user.getUsername()+" is added.");

        System.out.println("Projects added to: ");
        try {
            List<Integer> project_id = projectUserService.getAllProjectsWhereUserIsAdded(user.getId());
            if(project_id.isEmpty()){
                System.out.println("No projects assigns user: " + user.getUsername());
            }else {
                for (Integer id : project_id) {
                    System.out.println(id);
                }
            }
        }catch (UserIdNotFound e){
            throw new UserIdNotFound();
        }
        return true;
    }
}
