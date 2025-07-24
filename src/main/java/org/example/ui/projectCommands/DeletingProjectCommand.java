package org.example.ui.projectCommands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.service.ProjectService;
import org.example.ui.Command;

import java.util.Scanner;

public class DeletingProjectCommand implements Command {
    private final ProjectService projectService;
    private final Scanner scanner;
    private final Logger logger = LogManager.getLogger(DeletingProjectCommand.class);

    public DeletingProjectCommand(ProjectService projectService, Scanner scanner) {
        this.projectService = projectService;
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
        System.out.println("Are you sure you want to delete the project " + project.getName() + "(Y/N)");
        String answer = scanner.nextLine();

        if (answer.equals("Y")) {
            boolean deleted = projectService.deleteProject(project);
            if (!deleted) {
                logger.error("Failed to delete {}", project.getName());
            }
            System.out.println("Project deleted successfully");
            SessionManager.getInstance().setCurrentProject(null);
        } else if (answer.equals("N")) {
            System.out.println("Project " + project.getName() + "is not deleted");
        } else {
            logger.error("Invalid answer entered");
        }
        return true;
    }
}
