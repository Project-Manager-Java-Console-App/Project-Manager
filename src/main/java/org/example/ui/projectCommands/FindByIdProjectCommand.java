package org.example.ui.projectCommands;

import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.service.ProjectService;
import org.example.ui.Command;

import java.util.Scanner;

public class FindByIdProjectCommand implements Command {
    private final ProjectService projectService;
    private final Scanner scanner;


    public FindByIdProjectCommand(ProjectService projectService, Scanner scanner) {
        this.projectService = projectService;
        this.scanner = scanner;

    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        System.out.println("Finding Project by ID");
        System.out.println("Enter Project ID");
        int id = scanner.nextInt();
        if (id == 0) {
            System.err.println("Project ID is required");
        }

        Project project = projectService.findById(id);
        if (project == null) {
            System.err.println("Project not found");
            return true;
        }
        SessionManager.getInstance().setCurrentProject(project);
        System.out.println("Project found");
        System.out.println(project);
        return true;
    }
}
