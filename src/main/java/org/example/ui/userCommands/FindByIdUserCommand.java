package org.example.ui.userCommands;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Users;
import org.example.service.UserService;
import org.example.ui.Command;

import java.util.Scanner;

public class FindByIdUserCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;
    private final Logger logger = LogManager.getLogger(FindByIdUserCommand.class);

    public FindByIdUserCommand(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        System.out.println("Searching for user by id");
        System.out.println("Enter id: ");
        int id = scanner.nextInt();
        if (id == -1) {
            logger.error("id is null");
            return true;
        }

        Users user = userService.findById(id);
        if (user == null) {
            logger.error("User not found");
            return true;
        }
        System.out.println("User found");
        System.out.println(user);
        return true;
    }
}
