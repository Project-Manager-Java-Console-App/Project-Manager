package org.example.ui.taskCommands;


import org.example.model.SessionManager;
import org.example.model.Task;
import org.example.service.TaskService;
import org.example.ui.Command;

import java.util.Scanner;

public class FindByIdTaskCommand implements Command {
    private final TaskService taskService;
    private final Scanner scanner;


    public FindByIdTaskCommand(TaskService taskService, Scanner scanner) {
        this.taskService = taskService;
        this.scanner = scanner;

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
        SessionManager.getInstance().setCurrentTask(task);
        System.out.println("Task found");
        System.out.println(task);
        return true;
    }
}
