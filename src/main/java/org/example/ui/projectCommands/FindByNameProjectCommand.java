package org.example.ui.projectCommands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.service.ProjectService;
import org.example.ui.Command;

import java.util.Scanner;

public class FindByNameProjectCommand implements Command {
    private final ProjectService projectService;
    private final Scanner scanner;
    private final Logger logger = LogManager.getLogger(FindByNameProjectCommand.class);


    public FindByNameProjectCommand(ProjectService projectService, Scanner scanner) {
        this.projectService = projectService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        System.out.println("Searched project name: ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            logger.error("Project name is required");
        }

        Project project = projectService.findProjectByName(name);
        if (project == null) {
            logger.error("Project not found");
        }
        SessionManager.getInstance().setCurrentProject(project);
        System.out.println("Project found\n" + project);
        return true;
    }
}
