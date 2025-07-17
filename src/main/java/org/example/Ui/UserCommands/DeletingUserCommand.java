package org.example.Ui.UserCommands;

import org.example.Service.ProjectService;
import org.example.Service.ProjectUserService;
import org.example.Service.UserService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.model.Users;

import java.util.List;
import java.util.Scanner;

public class DeletingUserCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;
    private final ProjectService projectService;
    private final ProjectUserService projectUserService;
    private final SessionManager sessionManager;

    public DeletingUserCommand(SessionManager sessionManager, UserService userService, ProjectService projectService, ProjectUserService projectUserService, Scanner scanner) {
        this.userService = userService;
        this.projectService = projectService;
        this.projectUserService = projectUserService;
        this.scanner = scanner;
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        Users users = sessionManager.getCurrentUser();
        if (users == null) {
            System.err.println("User not found");
            return true;
        }
        System.out.println("Deleting user");
        System.out.println("Are you sure you want to delete this user?(Y/N)");
        String answer = scanner.nextLine();
        switch (answer) {
            case "" -> {
                System.err.println("Answer is empty");
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
                    System.err.println("Failed to delete user");
                    return true;
                } else {
                    sessionManager.logout();
                }
            }
            case "N" -> System.out.println("Deleting process is rejected");
        }
        return true;
    }
}
