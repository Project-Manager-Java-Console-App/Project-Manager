package org.example.Ui.MainCommandBlocks;

import org.example.Service.UserService;
import org.example.Ui.AuthCommands.ExitCommand;
import org.example.Ui.AuthCommands.LoginCommand;
import org.example.Ui.AuthCommands.RegisterCommand;
import org.example.Ui.Command;
import org.example.dataBase.DatabaseUtils;

import java.util.Scanner;

public class AuthCommandFactory {
    public static Command getCommand(int choice, UserService userService, Scanner scanner, DatabaseUtils databaseUtils){
        return switch (choice){
            case 1 -> new RegisterCommand(userService, scanner);
            case 2 -> new LoginCommand(userService, scanner);
            case 3 -> new ExitCommand(databaseUtils);
            default -> () ->System.out.println("Invalid choice");
        };
    }
}
