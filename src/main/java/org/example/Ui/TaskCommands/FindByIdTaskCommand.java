package org.example.Ui.TaskCommands;


import org.example.Service.TaskService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Task;

import java.util.Scanner;

public class FindByIdTaskCommand implements Command {
    private final TaskService taskService;
    private final Scanner scanner;
    private final SessionManager sessionManager;

    public FindByIdTaskCommand(SessionManager sessionManager, TaskService taskService, Scanner scanner) {
        this.taskService = taskService;
        this.scanner = scanner;
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        System.out.println("Searching for Task by id");
        System.out.println("Enter task id: ");
        int id = scanner.nextInt();
        if (id == 0) {
            throw new IllegalArgumentException("Task id is null");
        }

        Task task = taskService.findById(id);
        if (task == null) {
            System.err.println("Task " + id + " not found");
            return true;
        }
        sessionManager.setCurrentTask(task);
        System.out.println("Task found");
        System.out.println(task);
        return true;
    }
}
