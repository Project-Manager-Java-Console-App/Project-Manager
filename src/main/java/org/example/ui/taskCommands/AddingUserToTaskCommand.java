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

public class AddingUserToTaskCommand implements Command {
    private final TaskUserService taskUserService;
    private final UserService userService;
    private final Scanner scanner;
    private final Logger logger = LogManager.getLogger(AddingUserToTaskCommand.class);

    public AddingUserToTaskCommand(TaskUserService taskUserService, UserService userService, Scanner scanner) {
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
        System.out.println("Adding user to " + task.getName());
        System.out.println("Enter username of User you want to add: ");
        String username = scanner.nextLine();
        if (username.isEmpty()) {
            logger.error("Username is required");
        }

        Users users = userService.findByName(username);
        if (users == null) {
            logger.error("User not found");
            return true;
        }
        boolean addedUser = taskUserService.addUserToTask(task.getId(), users.getId());
        if (!addedUser) {
            logger.error("Failed to add user");
        }
        return true;

    }
}
