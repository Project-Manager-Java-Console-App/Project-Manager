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

public class RemovingUserFromProjectCommand implements Command {
    private final ProjectUserService projectUserService;
    private final UserService userService;
    private final Scanner scanner;
    private final Logger logger = LogManager.getLogger(RemovingUserFromProjectCommand.class);

    public RemovingUserFromProjectCommand(ProjectUserService projectUserService, UserService userService, Scanner scanner) {
        this.projectUserService = projectUserService;
        this.userService = userService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        Project project = SessionManager.getInstance().getCurrentProject();
        if (project == null) {
            logger.error("Project is required");
            return true;
        }
        System.out.println("Removing User From Project: " + project.getName());
        System.out.println("Enter username to remove");
        String username = scanner.nextLine();
        if (username.isEmpty()) {
            logger.error("Username is required");
        }
        Users user = userService.findByName(username);
        if (user == null) {
            logger.error("Username {}", username + " Not Found");
            return true;
        }
        boolean remove = projectUserService.removeUserFromProject(project.getId(), user.getId());
        if (remove) {
            System.out.println("Removed " + username + " from Project: " + project.getName());
        }
        return true;
    }
}
