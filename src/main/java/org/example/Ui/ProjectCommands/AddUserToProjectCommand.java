package org.example.Ui.ProjectCommands;


import org.example.Service.ProjectUserService;
import org.example.Service.UserService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.model.Users;

import java.util.Scanner;

public class AddUserToProjectCommand implements Command {
    private final ProjectUserService projectUserService;
    private final UserService userService;
    private final Scanner scanner;
    private final SessionManager sessionManager;

    public AddUserToProjectCommand(SessionManager sessionManager, ProjectUserService projectUserService, UserService userService, Scanner scanner) {
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
            System.err.println("Project not found");
            return true;
        }
        System.out.println("Adding User to Project: " + project.getName());
        System.out.println("Enter username to add user to " + project.getName());
        String username = scanner.nextLine();
        if (username == null) {
            System.err.println("Username is required");
        }

        Users user = userService.findByName(username);
        if (user == null) {
            System.err.println("User " + username + " Not Found");
            return true;
        }
        boolean added = projectUserService.addUserToProject(project.getId(), user.getId());
        if (added) {
            System.out.println("Added User to Project: " + project.getName());
        } else {
            System.out.println("Failed to add User to Project: " + project.getName());
            return true;
        }

        return true;
    }
}
