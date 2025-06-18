package org.example.Ui.UserCommands;

import org.example.Exceptions.UserNotFound;
import org.example.Service.UserService;
import org.example.Ui.Command;
import org.example.model.Users;

import java.sql.SQLException;
import java.util.Scanner;

public class FindUserByNameCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;

    public FindUserByNameCommand(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute()  {
        scanner.nextLine();
        System.out.println("Finding user by name");
        System.out.println("Enter User username");
        String username = scanner.nextLine();
        if (username.isEmpty()){
            System.err.println("Username is required");
            return true;
        }

        try {
            Users users = userService.findByName(username);
            if (users == null){
                throw new UserNotFound(username);
            }
            System.out.println("User found:");
            System.out.println(users);
        }catch (SQLException e){
            throw new UserNotFound(username);
        }
        return true;
    }
}
