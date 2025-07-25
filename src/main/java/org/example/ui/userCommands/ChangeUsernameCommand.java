package org.example.ui.userCommands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.SessionManager;
import org.example.model.Users;
import org.example.service.UserService;
import org.example.ui.Command;

import java.util.Scanner;

public class ChangeUsernameCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;
    private final Logger logger = LogManager.getLogger(ChangeUsernameCommand.class);

    public ChangeUsernameCommand(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        Users users = SessionManager.getInstance().getCurrentUser();
        if (users == null) {
            logger.error("User is required");
            return true;
        }
        System.out.println("Changing username: ");
        System.out.println("Enter your new Username: ");
        String username = scanner.nextLine();
        if (username.isEmpty()) {
            logger.error("Username is required");
            return true;
        }

        Users us = Users.createUser(username);
        Users updated = userService.updateUser(us, users.getId());
        System.out.println("User " + username + " has been updated" + updated);
        return true;
    }
}
