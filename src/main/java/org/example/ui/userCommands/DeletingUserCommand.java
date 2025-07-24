package org.example.ui.userCommands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.model.Users;
import org.example.service.ProjectService;
import org.example.service.ProjectUserService;
import org.example.service.UserService;
import org.example.ui.Command;

import java.util.List;
import java.util.Scanner;

public class DeletingUserCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;
    private final ProjectService projectService;
    private final ProjectUserService projectUserService;
    private final Logger logger = LogManager.getLogger(DeletingUserCommand.class);


    public DeletingUserCommand(UserService userService, ProjectService projectService, ProjectUserService projectUserService, Scanner scanner) {
        this.userService = userService;
        this.projectService = projectService;
        this.projectUserService = projectUserService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        Users users = SessionManager.getInstance().getCurrentUser();
        if (users == null) {
            logger.error("User not found");
            return true;
        }
        System.out.println("Deleting user");
        System.out.println("Are you sure you want to delete this user?(Y/N)");
        String answer = scanner.nextLine();
        switch (answer) {
            case "" -> {
                logger.error("Answer is empty");
                return true;
            }
            case "Y" -> {
                System.out.println("Deleting projects created by " + users.getName());
                List<Project> createdBy = projectUserService.getAllProjectsCreatedByUser(users.getId());
                for (Project project : createdBy) {
                    System.out.print("Deleting project " + project.getName() + ": ");
                    boolean delete = projectService.deleteProject(project);
                    System.out.println(delete);
                }

                boolean deleted = userService.deleteUser(users.getId());
                if (!deleted) {
                    logger.error("Failed to delete user");
                    return true;
                } else {
                    SessionManager.getInstance().logout();
                }
            }
            case "N" -> System.out.println("Deleting process is rejected");
        }
        return true;
    }
}
