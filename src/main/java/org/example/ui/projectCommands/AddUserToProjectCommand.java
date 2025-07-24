package org.example.ui.projectCommands;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.model.Users;
import org.example.service.ProjectUserService;
import org.example.service.UserService;
import org.example.ui.Command;

import java.util.Scanner;

public class AddUserToProjectCommand implements Command {
    private final ProjectUserService projectUserService;
    private final UserService userService;
    private final Scanner scanner;
    private final Logger logger = LogManager.getLogger(AddUserToProjectCommand.class);


    public AddUserToProjectCommand(ProjectUserService projectUserService, UserService userService, Scanner scanner) {
        this.projectUserService = projectUserService;
        this.userService = userService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        Project project = SessionManager.getInstance().getCurrentProject();
        if (project == null) {
            logger.error("Project not found");
            return true;
        }
        System.out.println("Adding User to Project: " + project.getName());
        System.out.println("Enter username to add user to " + project.getName());
        String username = scanner.nextLine();
        if (username == null) {
            logger.error("Username is required");
        }

        Users user = userService.findByName(username);
        if (user == null) {
            logger.error("User {}", username + " Not Found");
            return true;
        }
        boolean added = projectUserService.addUserToProject(project.getId(), user.getId());
        if (added) {
            System.out.println("Added User to Project: " + project.getName());
        } else {
            logger.error("Failed to add User to Project: {}", project.getName());
            return true;
        }

        return true;
    }
}
