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
    public boolean execute() {
        scanner.nextLine();
        System.out.print("Enter project name: ");
        String projectName = scanner.nextLine();
        System.out.print("Enter project description: ");
        String projectDescription = scanner.nextLine();
        if(projectName.isEmpty() || projectDescription.isEmpty()){
            System.err.println("Project name or description is empty");
        }

        try{
            int userId = SessionManager.getCurrentUser().getId();
            Project project = projectService.createProject(projectName, projectDescription, userId);
            SessionManager.setCurrentProject(project);
            System.out.println("Project created");
        }catch (Exception e){
           throw new RuntimeException(e.getMessage());
        }
        return true;
    }
}
