package org.example.Ui.ProjectCommands;

import org.example.Exceptions.ProjectNotFound;
import org.example.Exceptions.UsernameAlreadyExistsException;
import org.example.Service.ProjectService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;

import java.util.Scanner;

public class FindByIdProjectCommand implements Command {
    private final ProjectService projectService;
    private final Scanner scanner;

    public FindByIdProjectCommand(ProjectService projectService, Scanner scanner) {
        this.projectService = projectService;
        this.scanner = scanner;
    }

    @Override
    public void execute()  {
        scanner.nextLine();
        System.out.println("Finding Project by ID");
        System.out.println("Enter Project ID");
        int id = scanner.nextInt();
        if (id ==0){
            System.err.println("Project ID is required");
            return;
        }

        try{
            Project project = projectService.findById(id);
            if (project == null) {
                throw new ProjectNotFound("Project not found");
            }
            SessionManager.setCurrentProject(project);
            System.out.println("Project found");
            System.out.println(project);
        }catch (Exception e) {
            throw new RuntimeException("Failed to find project");
        }
    }
}
