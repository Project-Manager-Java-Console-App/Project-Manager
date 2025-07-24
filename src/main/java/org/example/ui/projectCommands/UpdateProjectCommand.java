package org.example.ui.projectCommands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.service.ProjectService;
import org.example.ui.Command;

import java.util.Scanner;

public class UpdateProjectCommand implements Command {
    private final ProjectService projectService;
    private final Scanner scanner;
    private final Logger logger = LogManager.getLogger(UpdateProjectCommand.class);

    public UpdateProjectCommand(ProjectService projectService, Scanner scanner) {
        this.projectService = projectService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        System.out.println("Enter new project name: ");
        String name = scanner.nextLine();
        System.out.println("Enter new project description: ");
        String description = scanner.nextLine();
        if (name.isEmpty() || description.isEmpty()) {
            logger.error("Project name or description is required");
        }

        Project project = SessionManager.getInstance().getCurrentProject();
        if (project == null) {
            logger.error("No project found");
            return true;
        }
        Project projectUpdated = projectService.updateProject(name, description, project.getId());
        SessionManager.getInstance().setCurrentProject(projectUpdated);
        System.out.println("Project updated\n" + project);
        return true;
    }
}
