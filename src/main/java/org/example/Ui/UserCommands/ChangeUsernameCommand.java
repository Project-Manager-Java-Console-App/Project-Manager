package org.example.Ui.UserCommands;

import org.example.Service.UserService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Users;

import java.util.Scanner;

public class ChangeUsernameCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;
    private final SessionManager sessionManager;

    public ChangeUsernameCommand(SessionManager sessionManager, UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        Users users = sessionManager.getCurrentUser();
        if (users == null) {
            System.err.println("User is required");
            return true;
        }
        System.out.println("Changing username: ");
        System.out.println("Enter your new Username: ");
        String username = scanner.nextLine();
        if (username.isEmpty()) {
            System.err.println("Username is required");
            return true;
        }

        Users us = Users.createUser(username);
        Users updated = userService.updateUser(us, users.getId());
        System.out.println("User " + username + " has been updated" + updated);
        return true;
    }
}
