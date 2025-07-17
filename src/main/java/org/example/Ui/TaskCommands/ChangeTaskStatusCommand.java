package org.example.Ui.TaskCommands;

import org.example.Service.TaskService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Status;
import org.example.model.Task;

import java.util.Scanner;

import static org.example.Ui.GlobalMethods.GlobalMethods.enterStatus;

public class ChangeTaskStatusCommand implements Command {
    private final TaskService taskService;
    private final Scanner scanner;
    private final SessionManager sessionManager;

    public ChangeTaskStatusCommand(SessionManager sessionManager, TaskService taskService, Scanner scanner) {
        this.taskService = taskService;
        this.scanner = scanner;
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        Task task = sessionManager.getCurrentTask();
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
            throw new RuntimeException("Change task status failed");
        }
        System.out.println("Changed task status of " + task.getName());

        return true;
    }
}
