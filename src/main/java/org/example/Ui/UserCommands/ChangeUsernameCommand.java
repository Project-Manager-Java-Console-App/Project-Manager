package org.example.Ui.UserCommands;

import org.example.Service.UserService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Users;

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
            System.err.println("User is required");
            return true;
        }
        System.out.println("Changing username: ");
        System.out.println("Enter your new Username: ");
        String username = scanner.nextLine();
        if (username.isEmpty()){
            System.err.println("Username is required");
            return true;
        }

        try{
            boolean updated = userService.updateUser(username, users.getId());
            System.out.println("User " + username + " has been updated"+ updated);
        }catch (Exception e) {
            System.err.println("Failed to change username");
        }
        return true;
    }
}
