package org.example.Ui.AuthCommands;

import org.example.Service.UserService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Users;

import java.util.Scanner;

public class RegisterCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;
    private final SessionManager sessionManager;

    public RegisterCommand(SessionManager sessionManager, UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
        this.sessionManager = sessionManager;
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
        sessionManager.login(user);
        return true;
    }
}
