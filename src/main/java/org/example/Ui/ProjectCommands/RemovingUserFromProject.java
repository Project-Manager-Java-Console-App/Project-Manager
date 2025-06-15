package org.example.Ui.ProjectCommands;

import org.example.Exceptions.UserNotFound;
import org.example.Service.ProjectUserService;
import org.example.Service.UserService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.model.Users;

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
    public void execute() {
        scanner.nextLine();
        Project project = SessionManager.getCurrentProject();
        if (project == null){
            System.err.println("Project is required");
            return;
        }
        System.out.println("Removing User From Project: "+project.getName());
        System.out.println("Enter username to remove");
        String username = scanner.nextLine();
        if (username.isEmpty()){
            System.err.println("Username is required");
            return;
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

        }catch (Exception e){
            System.err.println("Failed to remove user from project: "+project.getName());
        }
    }
}
