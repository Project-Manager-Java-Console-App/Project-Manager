package org.example.Ui.TaskCommands;

import org.example.Service.TaskService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Task;

import java.util.Scanner;

public class FindTaskByNameCommand implements Command {
    private final TaskService taskService;
    private final Scanner scanner;
    private final SessionManager sessionManager;

    public FindTaskByNameCommand(SessionManager sessionManager, TaskService taskService, Scanner scanner) {
        this.taskService = taskService;
        this.scanner = scanner;
        this.sessionManager = sessionManager;
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
            sessionManager.setCurrentTask(task);
            System.out.println("Task found");
            System.out.println(task);
        } catch (Exception e) {
            System.err.println("Task " + name + " not found");
        }
        return true;
    }
}
