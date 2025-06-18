package org.example.Ui.UserCommands;

import org.example.Exceptions.UserIdNotFound;
import org.example.Exceptions.UserNotFound;
import org.example.Service.ProjectService;
import org.example.Service.ProjectUserService;
import org.example.Service.UserService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.model.Users;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class DeletingUserCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;
    private final ProjectService projectService;
    private final ProjectUserService projectUserService;

    public DeletingUserCommand(UserService userService, ProjectService projectService,ProjectUserService projectUserService, Scanner scanner) {
        this.userService = userService;
        this.projectService = projectService;
        this.projectUserService = projectUserService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute()  {
        scanner.nextLine();
        Users users = SessionManager.getCurrentUser();
        if (users == null){
            throw new UserNotFound("User not found");
        }
        System.out.println("Deleting user");
        System.out.println("Are you sure you want to delete this user?(Y/N)");
        String answer = scanner.nextLine();
        if (answer.isEmpty()){
            throw new IllegalArgumentException("Answer is empty");
        }
        try {
            if (answer.equals("Y")) {
                System.out.println("Deleting projects created by "+users.getUsername());
                List<Project> createdBy = projectUserService.getAllProjectsCreatedByUser(users.getId());
                for (Project project : createdBy) {
                    System.out.print("Deleting project "+project.getName()+": ");
                    boolean delete =  projectService.deleteProject(project);
                    System.out.println(delete);
                }

                boolean deleted = userService.deleteUser(users.getId());
                if (!deleted) {
                    throw new SQLException("Failed to delete user");
                }else {
                    SessionManager.logout();
                }

            }else if (answer.equals("N")) {
                System.out.println("Deleting process is rejected");
            }
        }catch (SQLException e) {
            throw new UserIdNotFound();
        }
        return true;
    }
}
