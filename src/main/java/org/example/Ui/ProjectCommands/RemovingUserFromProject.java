package org.example.Ui.ProjectCommands;

import org.example.Exceptions.UserIdNotFound;
import org.example.Exceptions.UserNotFound;
import org.example.Service.ProjectUserService;
import org.example.Service.UserService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.model.Users;

import java.sql.SQLException;
import java.util.Scanner;

public class RemovingUserFromProject implements Command {
    private final ProjectUserService projectUserService;
    private final UserService userService;
    private final Scanner scanner;

    public RemovingUserFromProject(ProjectUserService projectUserService,UserService userService, Scanner scanner) {
        this.projectUserService = projectUserService;
        this.userService = userService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        Project project = SessionManager.getCurrentProject();
        if (project == null){
            System.err.println("Project is required");
            return true;
        }
        System.out.println("Removing User From Project: "+project.getName());
        System.out.println("Enter username to remove");
        String username = scanner.nextLine();
        if (username.isEmpty()){
            System.err.println("Username is required");
        }
        try{
            Users user = userService.findByName(username);
            if(user == null){
                throw new UserNotFound("Username "+username+" Not Found");
            }
            boolean remove = projectUserService.removeUserFromProject(project.getId(),user.getId());
            if(remove){
                System.out.println("Removed "+username+" from Project: "+project.getName());
            }

        }catch (UserIdNotFound e){
            throw new UserNotFound("Username "+username+" Not Found");
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return true;
    }
}
