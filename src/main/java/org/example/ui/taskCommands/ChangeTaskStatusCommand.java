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

public class ChangeTaskStatusCommand implements Command {
    private final TaskService taskService;
    private final Scanner scanner;
    private final Logger logger = LogManager.getLogger(ChangeTaskStatusCommand.class);

    public ChangeTaskStatusCommand(TaskService taskService, Scanner scanner) {
        this.taskService = taskService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        Task task = SessionManager.getInstance().getCurrentTask();
        if (task == null) {
            System.err.println("Task is required");
            return true;
        }
        System.out.println("Change task status of " + task.getName());
        Status newStatus = enterStatus(scanner);
        if (newStatus == null) {
            System.err.println("Status is required");
        }
        boolean changedStatus = taskService.changeStatus(task.getId(), newStatus);
        if (!changedStatus) {
            logger.error("Change task status failed");
            return true;
        }
        System.out.println("Changed task status of " + task.getName());

        return true;
    }
}
