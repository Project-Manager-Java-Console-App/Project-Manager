package org.example.ui.projectCommands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.service.ProjectService;
import org.example.ui.Command;

import java.util.Scanner;

public class CreateProjectCommand implements Command {
    private final ProjectService projectService;
    private final Scanner scanner;
    private final Logger logger = LogManager.getLogger(CreateProjectCommand.class);

    public CreateProjectCommand(ProjectService projectService, Scanner scanner) {
        this.projectService = projectService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        System.out.print("Enter project name: ");
        String projectName = scanner.nextLine();
        System.out.print("Enter project description: ");
        String projectDescription = scanner.nextLine();
        if (projectName.isEmpty() || projectDescription.isEmpty()) {
            logger.error("Project name or description is empty");
            return true;
        }

        int userId = SessionManager.getInstance().getCurrentUser().getId();
        Project project = projectService.createProject(projectName, projectDescription, userId);
        SessionManager.getInstance().setCurrentProject(project);
        System.out.println("Project created");
        return true;
    }
}
