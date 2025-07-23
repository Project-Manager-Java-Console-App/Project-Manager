package org.example.ui.taskCommands;

import org.example.model.SessionManager;
import org.example.model.Task;
import org.example.service.TaskService;
import org.example.ui.Command;

import java.sql.SQLException;
import java.util.Scanner;

public class DeleteTaskCommand implements Command {

    private final TaskService taskService;
    private final Scanner scanner;


    public DeleteTaskCommand(TaskService taskService, Scanner scanner) {
        this.taskService = taskService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        Task task = SessionManager.getInstance().getCurrentTask();
        if (task == null) {
            System.err.println(" task is null");
            return true;
        }
        System.out.println("Deleting task " + task.getName());
        System.out.println("Do you want to delete the task " + task.getName() + "(Y/N)");
        String answer = scanner.nextLine();
        if (answer.isEmpty()) {
            System.err.println("Answer can't be empty");
            return true;
        }
        try {
            if (answer.equals("Y")) {
                boolean deleted = taskService.delete(task);
                if (!deleted) {
                    System.err.println("Task could not be deleted");
                    return true;
                }
                System.out.println("Task deleted successfully");
                SessionManager.getInstance().setCurrentTask(null);
            } else if (answer.equals("N")) {
                System.out.println("Task is not deleted.");
            }
        } catch (SQLException e) {
            System.err.println("Error while deleting task " + e.getMessage());

        }
        return true;
    }
}
