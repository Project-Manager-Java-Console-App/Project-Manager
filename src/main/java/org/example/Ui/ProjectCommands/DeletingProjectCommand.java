package org.example.Ui.ProjectCommands;

import org.example.Service.ProjectService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;

import java.util.Scanner;

public class DeletingProjectCommand implements Command {
    private final ProjectService projectService;
    private final Scanner scanner;
    private final SessionManager sessionManager;


    public DeletingProjectCommand(SessionManager sessionManager, ProjectService projectService, Scanner scanner) {
        this.projectService = projectService;
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
        System.out.println("Are you sure you want to delete the project " + project.getName() + "(Y/N)");
        String answer = scanner.nextLine();

        if (answer.equals("Y")) {
            boolean deleted = projectService.deleteProject(project);
            if (!deleted) {
                System.out.println("Failed to delete " + project.getName());
            }
            System.out.println("Project deleted successfully");
            sessionManager.setCurrentProject(null);
        } else if (answer.equals("N")) {
            System.out.println("Project " + project.getName() + "is not deleted");
        } else {
            System.err.println("Invalid answer entered");
        }
        return true;
    }
}
