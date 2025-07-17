package org.example.Ui.TaskCommands;

import org.example.Service.TaskService;
import org.example.Ui.Command;
import org.example.model.*;

import java.time.LocalDate;
import java.util.Scanner;

public class CreateTaskInProjectCommand implements Command {
    private final TaskService taskService;
    private final Scanner scanner;
    private final SessionManager sessionManager;

    public CreateTaskInProjectCommand(SessionManager sessionManager, TaskService taskService, Scanner scanner) {
        this.taskService = taskService;
        this.scanner = scanner;
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        Project project = sessionManager.getCurrentProject();
        if (project == null) {
            System.err.println("Project not found");
            return true;
        }
        Users user = sessionManager.getCurrentUser();
        if (user == null) {
            System.err.println("User is required");
            return true;
        }
        System.out.println("Creating task " + project.getName() + " in " + user.getName());
        System.out.println("Please enter name: ");
        String name = scanner.nextLine();
        System.out.println("Please enter description: ");
        String description = scanner.nextLine();
        if (name.isEmpty() || description.isEmpty()) {
            System.err.println("Name and description are required");
            return true;
        }

        Task task = taskService.createTask(name, project.getId(), description, Status.IN_PROGRESS, LocalDate.now(), user.getId());
        if (task == null) {
            System.err.println("Failed to create task " + project.getName() + " in " + user.getName());
            return true;
        }
        sessionManager.setCurrentTask(task);
        return true;
    }
}
