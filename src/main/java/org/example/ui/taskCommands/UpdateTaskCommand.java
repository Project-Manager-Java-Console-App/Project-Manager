package org.example.ui.taskCommands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final Logger logger = LogManager.getLogger(UpdateTaskCommand.class);


    public UpdateTaskCommand(Scanner scanner, TaskService taskService) {
        this.scanner = scanner;
        this.taskService = taskService;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        Task task = SessionManager.getInstance().getCurrentTask();
        if (task == null) {
            logger.error("Task not found");
            return true;
        }
        System.out.println("Enter new name: ");
        String name = scanner.nextLine();
        System.out.println("Enter new description: ");
        String description = scanner.nextLine();

        Status newStatus = enterStatus(scanner);
        if (name.isEmpty() || description.isEmpty() || newStatus == null) {
            logger.error("Name and description are required");
            return true;
        }

        taskService.updateTask(name, description, task.getId());
        System.out.println("Task updated successfully");

        return true;
    }
}
