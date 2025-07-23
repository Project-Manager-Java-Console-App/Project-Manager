package org.example.ui.authCommands;

import org.example.model.SessionManager;
import org.example.model.Users;
import org.example.service.UserService;
import org.example.ui.Command;

import java.util.Scanner;

public class LoginCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;


    public LoginCommand(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;

    }

    @Override
    public boolean execute() {

        scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Users user = userService.loginUser(username, password);
        if (user == null) {
            System.out.println("Invalid username or password");
        }
        SessionManager.getInstance().login(user);
        System.out.println("Login complete. Welcome " + username);
        return true;
    }
}
