package org.example.Ui.TaskCommands;

import org.example.Service.TaskService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Status;
import org.example.model.Task;

import java.util.Scanner;

import static org.example.Ui.GlobalMethods.GlobalMethods.enterStatus;

public class UpdateTaskCommand implements Command {
    private final TaskService taskService;
    private final Scanner scanner;
    private final SessionManager sessionManager;

    public UpdateTaskCommand(SessionManager sessionManager, Scanner scanner, TaskService taskService) {
        this.scanner = scanner;
        this.taskService = taskService;
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        Task task = sessionManager.getCurrentTask();
        if (task == null) {
            System.err.println("Task not found");
            return true;
        }
        System.out.println("Enter new name: ");
        String name = scanner.nextLine();
        System.out.println("Enter new description: ");
        String description = scanner.nextLine();

        Status newStatus = enterStatus(scanner);
        if (name.isEmpty() || description.isEmpty() || newStatus == null) {
            System.err.println("Name and description are required");
            return true;
        }

        taskService.updateTask(name, description, task.getId());
        System.out.println("Task updated successfully");

        return true;
    }
}
