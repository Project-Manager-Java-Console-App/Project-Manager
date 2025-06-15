package org.example.Ui.ProjectCommands;

import org.example.Service.ProjectService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;

import java.util.Scanner;

public class CreateProjectCommand implements Command {
    private final ProjectService projectService;
    private final Scanner scanner;

    public CreateProjectCommand(ProjectService projectService, Scanner scanner) {
        this.projectService = projectService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        scanner.nextLine();
        System.out.print("Enter project name: ");
        String projectName = scanner.nextLine();
        System.out.print("Enter project description: ");
        String projectDescription = scanner.nextLine();
        if(projectName.isEmpty() || projectDescription.isEmpty()){
            System.err.println("Project name or description is empty");
            return;
        }

        try{
            int userId = SessionManager.getCurrentUser().getId();
            Project project = projectService.createProject(projectName, projectDescription, userId);
            SessionManager.setCurrentProject(project);
            System.out.println("Project created");
        }catch (Exception e){
            System.err.println("Failed to create project");
        }

    }
}
