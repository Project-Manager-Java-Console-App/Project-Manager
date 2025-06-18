package org.example.Ui.UserCommands;

import org.example.Service.UserService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Users;

import java.sql.SQLException;
import java.util.Scanner;

public class ChangeUsernameCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;

    public ChangeUsernameCommand(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute()  {
        scanner.nextLine();
        Users users = SessionManager.getCurrentUser();
        if (users == null){
            throw new RuntimeException("User is required");
        }
        System.out.println("Changing username: ");
        System.out.println("Enter your new Username: ");
        String username = scanner.nextLine();
        if (username.isEmpty()){
           throw new RuntimeException("Username is required");
        }

        try{
            boolean updated = userService.updateUser(username, users.getId());
            System.out.println("User " + username + " has been updated"+ updated);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
