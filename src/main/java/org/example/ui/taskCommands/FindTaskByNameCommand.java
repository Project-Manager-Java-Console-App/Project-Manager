package org.example.ui.taskCommands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.SessionManager;
import org.example.model.Task;
import org.example.service.TaskService;
import org.example.ui.Command;

import java.util.Scanner;

public class FindTaskByNameCommand implements Command {
    private final TaskService taskService;
    private final Scanner scanner;
    private final Logger logger = LogManager.getLogger(FindTaskByNameCommand.class);

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
            logger.error("Task name is empty");
            return true;
        }
        try {
            Task task = taskService.findByName(name);
            if (task == null) {
                logger.error("Task {}", name + " not found");
            }
            SessionManager.getInstance().setCurrentTask(task);
            System.out.println("Task found");
            System.out.println(task);
        } catch (Exception e) {
            logger.error("Task not found");
        }
        return true;
    }
}
