package org.example.Ui.AuthCommands;

import org.example.Service.UserService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Users;

import java.sql.SQLException;
import java.util.Scanner;

public class LoginCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;

    public LoginCommand(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        scanner.nextLine();
        System.out.print("Username:");
        String username = scanner.nextLine();
        System.out.print("Password:");
        String password = scanner.nextLine();

        try{
            Users user = userService.loginUser(username,password.toCharArray());
            if(user == null){
                System.out.println("Invalid username or password");
            }
            SessionManager.login(user);
            System.out.println("Login complete. Welcome " + username);
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }
}
