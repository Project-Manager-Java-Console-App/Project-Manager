package org.example.Ui.ProjectCommands;

import org.example.Exceptions.ProjectIdNotFound;
import org.example.Exceptions.ProjectNotFound;
import org.example.Service.ProjectService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;

import java.sql.SQLException;
import java.util.Scanner;

public class FindByIdProjectCommand implements Command {
    private final ProjectService projectService;
    private final Scanner scanner;

    public FindByIdProjectCommand(ProjectService projectService, Scanner scanner) {
        this.projectService = projectService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute()  {
        scanner.nextLine();
        System.out.println("Finding Project by ID");
        System.out.println("Enter Project ID");
        int id = scanner.nextInt();
        if (id ==0){
            System.err.println("Project ID is required");
        }

        try{
            Project project = projectService.findById(id);
            if (project == null) {
                throw new ProjectNotFound("Project not found");
            }
            SessionManager.setCurrentProject(project);
            System.out.println("Project found");
            System.out.println(project);
        }catch (ProjectIdNotFound e) {
            throw new ProjectIdNotFound();
        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
        return true;
    }
}
