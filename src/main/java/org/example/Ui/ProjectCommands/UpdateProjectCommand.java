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
    public void execute() {
        scanner.nextLine();
        System.out.println("Enter new project name: ");
        String name = scanner.nextLine();
        System.out.println("Enter new project description: ");
        String description = scanner.nextLine();

        try {
            Project projectId = SessionManager.getCurrentProject();
            if(projectId == null){
                System.err.println("No project found");
                return;
            }
            Project project = projectService.updateProject(name,description,projectId.getId());
            System.out.println("Project updated\n" + project);
        }catch (SQLException e){
            System.err.println("Failed to update project");
        }
    }
}
