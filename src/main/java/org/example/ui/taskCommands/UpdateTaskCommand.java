package org.example.ui.taskCommands;

import org.example.model.SessionManager;
import org.example.model.Status;
import org.example.model.Task;
import org.example.service.TaskService;
import org.example.ui.Command;

import java.util.Scanner;

import static org.example.ui.globalMethods.GlobalMethods.enterStatus;

public class UpdateTaskCommand implements Command {
    private final TaskService taskService;
    private final Scanner scanner;


    public UpdateTaskCommand(Scanner scanner, TaskService taskService) {
        this.scanner = scanner;
        this.taskService = taskService;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        Task task = SessionManager.getInstance().getCurrentTask();
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
