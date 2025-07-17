package org.example.Ui.ProjectCommands;

import org.example.Service.ProjectUserService;
import org.example.Service.UserService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.model.Users;

import java.util.Scanner;

public class RemovingUserFromProjectCommand implements Command {
    private final ProjectUserService projectUserService;
    private final UserService userService;
    private final Scanner scanner;
    private final SessionManager sessionManager;

    public RemovingUserFromProjectCommand(SessionManager sessionManager, ProjectUserService projectUserService, UserService userService, Scanner scanner) {
        this.projectUserService = projectUserService;
        this.userService = userService;
        this.scanner = scanner;
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        Project project = sessionManager.getCurrentProject();
        if (project == null) {
            System.err.println("Project is required");
            return true;
        }
        System.out.println("Removing User From Project: " + project.getName());
        System.out.println("Enter username to remove");
        String username = scanner.nextLine();
        if (username.isEmpty()) {
            System.err.println("Username is required");
        }
        Users user = userService.findByName(username);
        if (user == null) {
            System.err.println("Username " + username + " Not Found");
            return true;
        }
        boolean remove = projectUserService.removeUserFromProject(project.getId(), user.getId());
        if (remove) {
            System.out.println("Removed " + username + " from Project: " + project.getName());
        }


        return true;
    }
}
