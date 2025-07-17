package org.example.Ui.ProjectCommands;

import org.example.Service.ProjectService;
import org.example.Service.TaskService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.model.Task;

import java.util.Scanner;

public class AddTaskToProjectCommand implements Command {
    private final ProjectService projectService;
    private final TaskService taskService;
    private final Scanner scanner;
    private final SessionManager sessionManager;

    public AddTaskToProjectCommand(SessionManager sessionManager, ProjectService projectService, TaskService taskService, Scanner scanner) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.scanner = scanner;
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        Project project = sessionManager.getCurrentProject();
        if (project == null) {
            System.err.println("No current project selected.");
            return true;
        }
        System.out.println("Adding task to project: " + project.getName());
        System.out.println("Please enter the name of the task you would like to add: ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            System.err.println("Please enter the name of the task you would like to add: ");

        }
        Task task = taskService.findByName(name);

        if (task == null) {
            System.err.println("Task " + name + "Not Found");
            return true;
        }
        projectService.addTaskToProject(project.getId(), task.getId());

        return true;
    }
}
