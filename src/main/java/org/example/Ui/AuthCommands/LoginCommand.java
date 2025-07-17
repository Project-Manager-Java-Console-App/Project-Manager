package org.example.Ui.AuthCommands;

import org.example.Service.UserService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Users;

import java.util.Scanner;

public class LoginCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;
    private final SessionManager sessionManager;

    public LoginCommand(SessionManager sessionManager, UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean execute() {

        scanner.nextLine();
        System.out.print("Username:");
        String username = scanner.nextLine();
        System.out.print("Password:");
        String password = scanner.nextLine();

        Users user = userService.loginUser(username, password.toCharArray());
        if (user == null) {
            System.out.println("Invalid username or password");
        }
        sessionManager.login(user);
        System.out.println("Login complete. Welcome " + username);
        return true;
    }
}
