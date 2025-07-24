package org.example.ui.userCommands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Users;
import org.example.service.UserService;
import org.example.ui.Command;

import java.util.Scanner;

public class FindUserByNameCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;
    private final Logger logger = LogManager.getLogger(FindUserByNameCommand.class);

    public FindUserByNameCommand(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        System.out.println("Finding user by name");
        System.out.println("Enter User username");
        String username = scanner.nextLine();
        if (username.isEmpty()) {
            logger.error("username is empty");
            return true;
        }

        Users users = userService.findByName(username);
        if (users == null) {
            logger.error("Failed to find user by username: {}", username);
            return true;
        }
        System.out.println("User found:");
        System.out.println(users);
        return true;
    }
}
