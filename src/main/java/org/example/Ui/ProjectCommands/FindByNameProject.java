package org.example.Ui.ProjectCommands;

import org.example.Exceptions.ProjectNotFound;
import org.example.Exceptions.UsernameAlreadyExistsException;
import org.example.Service.ProjectService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;
import java.util.Scanner;

public class FindByNameProject implements Command {
    private final ProjectService projectService;
    private final Scanner scanner;

    public FindByNameProject(ProjectService projectService, Scanner scanner) {
        this.projectService = projectService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        System.out.println("Searched project name: ");
        String name = scanner.nextLine();
        if(name.isEmpty()){
            System.err.println("Project name is required");
        }

        try {
            Project project = projectService.findProjectByName(name);
            if(project == null) {
                System.out.println("Project not found");
            }
            SessionManager.setCurrentProject(project);
            System.out.println("Project found\n" + project);
        }catch (ProjectNotFound e) {
            throw new ProjectNotFound(e.getMessage());
        }catch (UsernameAlreadyExistsException e){
            throw new IllegalArgumentException("Username already exists");
        }
        return true;
    }
}
