package org.example.Ui.ProjectCommands;

import org.example.Service.ProjectService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;

import java.util.Scanner;

public class FindByNameProjectCommand implements Command {
    private final ProjectService projectService;
    private final Scanner scanner;
    private final SessionManager sessionManager;

    public FindByNameProjectCommand(SessionManager sessionManager, ProjectService projectService, Scanner scanner) {
        this.projectService = projectService;
        this.scanner = scanner;
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        System.out.println("Searched project name: ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            System.err.println("Project name is required");
        }

        Project project = projectService.findProjectByName(name);
        if (project == null) {
            System.out.println("Project not found");
        }
        sessionManager.setCurrentProject(project);
        System.out.println("Project found\n" + project);
        return true;
    }
}
