package org.example.Ui.ProjectCommands;

import org.example.Service.ProjectService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;

import java.sql.SQLException;
import java.util.Scanner;

public class UpdateProjectCommand implements Command {
    private final ProjectService projectService;
    private final Scanner scanner;

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
        if (name.isEmpty() || description.isEmpty()){
            System.err.println("Project name or description is required");
        }

        try {
            Project project= SessionManager.getCurrentProject();
            if(project == null){
                System.err.println("No project found");
                return true;
            }
            Project projectUpdated = projectService.updateProject(name,description,project.getId());
            SessionManager.setCurrentProject(projectUpdated);
            System.out.println("Project updated\n" + project);
        }catch (SQLException e){
            System.err.println("Failed to update project");
        }
        return true;
    }
}
