package org.example.Ui.UserCommands;

import org.example.Exceptions.UserNotFound;
import org.example.Service.UserService;
import org.example.Ui.Command;
import org.example.model.Users;

import java.util.Scanner;

public class FindByIdUserCommand implements Command {
    private final UserService userService;
    private final Scanner scanner;

    public FindByIdUserCommand(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        System.out.println("Searching for user by id");
        System.out.println("Enter id: ");
        int id =scanner.nextInt();
        if (id == -1){
            throw new IllegalArgumentException("id is null");
        }

        try {
            Users user = userService.findById(id);
            if (user == null) {
                throw new UserNotFound("User not found");
            }
            System.out.println("User found");
            System.out.println(user);
        }catch (Exception e) {
            throw new UserNotFound("User not found");
        }
        return true;
    }
}
