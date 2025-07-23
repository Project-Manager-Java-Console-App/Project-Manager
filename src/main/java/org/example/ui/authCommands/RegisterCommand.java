package org.example.ui.authCommands;

import org.example.model.SessionManager;
import org.example.model.Users;
import org.example.service.UserService;
import org.example.ui.Command;

import java.util.Scanner;

public class RegisterCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;


    public RegisterCommand(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;

    }

    @Override
    public boolean execute() {

        System.out.print("Enter username: ");
        scanner.nextLine();
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Users user = userService.registerUser(username, password);
        System.out.println("registered user" + user);
        System.out.println("Welcome " + username);
        SessionManager.getInstance().login(user);
        return true;
    }
}
