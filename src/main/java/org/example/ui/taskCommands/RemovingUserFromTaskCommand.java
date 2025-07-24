package org.example.ui.taskCommands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.SessionManager;
import org.example.model.Task;
import org.example.model.Users;
import org.example.service.TaskUserService;
import org.example.service.UserService;
import org.example.ui.Command;

import java.util.Scanner;

public class RemovingUserFromTaskCommand implements Command {

    private final TaskUserService taskUserService;
    private final UserService userService;
    private final Scanner scanner;
    private final Logger logger = LogManager.getLogger(RemovingUserFromTaskCommand.class);

    public RemovingUserFromTaskCommand(TaskUserService taskUserService, UserService userService, Scanner scanner) {
        this.taskUserService = taskUserService;
        this.userService = userService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        Task task = SessionManager.getInstance().getCurrentTask();
        if (task == null) {
            logger.error("Task is required");
            return true;
        }
        System.out.println("Removing user from task: " + task.getName());
        System.out.println("Enter username to remove: ");
        String username = scanner.nextLine();
        if (username.isEmpty()) {
            logger.error("username is required");
            return true;
        }
        Users user = userService.findByName(username);
        if (user == null) {
            logger.error("User is required");
            return true;
        }
        boolean removed = taskUserService.removeUserFromTask(task.getId(), user.getId());
        if (!removed) {
            logger.error("Failed to remove user from task");
            return true;
        }
        System.out.println("Removed user from task: " + task.getName());
        return true;
    }
}
