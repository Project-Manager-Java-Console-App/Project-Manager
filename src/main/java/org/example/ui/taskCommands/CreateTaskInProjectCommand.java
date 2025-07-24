package org.example.ui.taskCommands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.*;
import org.example.service.TaskService;
import org.example.ui.Command;

import java.time.LocalDate;
import java.util.Scanner;

public class CreateTaskInProjectCommand implements Command {
    private final TaskService taskService;
    private final Scanner scanner;
    private final Logger logger = LogManager.getLogger(CreateTaskInProjectCommand.class);

    public CreateTaskInProjectCommand(TaskService taskService, Scanner scanner) {
        this.taskService = taskService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        Project project = SessionManager.getInstance().getCurrentProject();
        if (project == null) {
            logger.error("Project not found");
            return true;
        }
        Users user = SessionManager.getInstance().getCurrentUser();
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
            logger.error("Name and description are required");
            return true;
        }

        Task task = taskService.createTask(name, project.getId(), description, Status.IN_PROGRESS, LocalDate.now(), user.getId());
        if (task == null) {
            logger.error("Failed to create task " + project.getName() + " in " + user.getName());
            return true;
        }
        SessionManager.getInstance().setCurrentTask(task);
        return true;
    }
}
