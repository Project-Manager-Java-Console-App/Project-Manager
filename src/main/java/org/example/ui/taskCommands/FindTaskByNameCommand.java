package org.example.ui.taskCommands;

import org.example.model.SessionManager;
import org.example.model.Task;
import org.example.service.TaskService;
import org.example.ui.Command;

import java.util.Scanner;

public class FindTaskByNameCommand implements Command {
    private final TaskService taskService;
    private final Scanner scanner;

    public FindTaskByNameCommand(TaskService taskService, Scanner scanner) {
        this.taskService = taskService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        System.out.println("Searching for the task");
        System.out.println("Enter the name of the task: ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Task name is empty");
        }
        try {
            Task task = taskService.findByName(name);
            if (task == null) {
                System.err.println("Task " + name + " not found");
            }
            SessionManager.getInstance().setCurrentTask(task);
            System.out.println("Task found");
            System.out.println(task);
        } catch (Exception e) {
            System.err.println("Task " + name + " not found");
        }
        return true;
    }
}
