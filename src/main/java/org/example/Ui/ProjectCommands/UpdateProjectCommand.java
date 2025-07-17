package org.example.Ui.ProjectCommands;

import org.example.Service.ProjectService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;

import java.util.Scanner;

public class UpdateProjectCommand implements Command {
    private final ProjectService projectService;
    private final Scanner scanner;
    private final SessionManager sessionManager;

    public UpdateProjectCommand(SessionManager sessionManager, ProjectService projectService, Scanner scanner) {
        this.projectService = projectService;
        this.scanner = scanner;
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        System.out.println("Enter new project name: ");
        String name = scanner.nextLine();
        System.out.println("Enter new project description: ");
        String description = scanner.nextLine();
        if (name.isEmpty() || description.isEmpty()) {
            System.err.println("Project name or description is required");
        }

        Project project = sessionManager.getCurrentProject();
        if (project == null) {
            System.err.println("No project found");
            return true;
        }
        Project projectUpdated = projectService.updateProject(name, description, project.getId());
        sessionManager.setCurrentProject(projectUpdated);
        System.out.println("Project updated\n" + project);
        return true;
    }
}
