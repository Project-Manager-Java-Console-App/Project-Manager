package org.example.Ui.ProjectCommands;

import org.example.Service.ProjectService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;

import java.util.Scanner;

public class CreateProjectCommand implements Command {
    private final ProjectService projectService;
    private final Scanner scanner;
    private final SessionManager sessionManager;

    public CreateProjectCommand(SessionManager sessionManager, ProjectService projectService, Scanner scanner) {
        this.projectService = projectService;
        this.scanner = scanner;
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        System.out.print("Enter project name: ");
        String projectName = scanner.nextLine();
        System.out.print("Enter project description: ");
        String projectDescription = scanner.nextLine();
        if (projectName.isEmpty() || projectDescription.isEmpty()) {
            System.err.println("Project name or description is empty");
            return true;
        }

        int userId = sessionManager.getCurrentUser().getId();
        Project project = projectService.createProject(projectName, projectDescription, userId);
        sessionManager.setCurrentProject(project);
        System.out.println("Project created");
        return true;
    }
}
