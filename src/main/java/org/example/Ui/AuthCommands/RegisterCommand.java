package org.example.Ui.AuthCommands;

import org.example.Exceptions.UsernameAlreadyExistsException;
import org.example.Service.UserService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Users;

import java.sql.SQLException;
import java.util.Scanner;

public class RegisterCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;

    public RegisterCommand(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {

        System.out.print("Enter username: ");
        scanner.nextLine();
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try{
            Users user= userService.registerUser(username,password);
            System.out.println("registered user"+ user);
            System.out.print("Welcome "+username);
            SessionManager.login(user);
        }catch (UsernameAlreadyExistsException | SQLException e){
            System.err.println(e.getMessage());
        }
    }
}
