package org.example.Ui.TaskCommands;

import org.example.Service.TaskUserService;
import org.example.Service.UserService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Task;
import org.example.model.Users;

import java.util.Scanner;

public class RemovingUserFromTaskCommand implements Command {

    private final TaskUserService taskUserService;
    private final UserService userService;
    private final Scanner scanner;
    private final SessionManager sessionManager;

    public RemovingUserFromTaskCommand(SessionManager sessionManager, TaskUserService taskUserService, UserService userService, Scanner scanner) {
        this.taskUserService = taskUserService;
        this.userService = userService;
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
        System.out.println("Removing user from task: " + task.getName());
        System.out.println("Enter username to remove: ");
        String username = scanner.nextLine();
        if (username.isEmpty()) {
            System.err.println("username is required");
            return true;
        }
        Users user = userService.findByName(username);
        if (user == null) {
            System.err.println("User is required");
            return true;
        }
        boolean removed = taskUserService.removeUserFromTask(task.getId(), user.getId());
        if (!removed) {
            System.err.println("Failed to remove user from task");
            return true;
        }
        System.out.println("Removed user from task: " + task.getName());
        return true;
    }
}
