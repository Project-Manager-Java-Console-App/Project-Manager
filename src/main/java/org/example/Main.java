package org.example;

import org.example.dataBase.DatabaseManager;
import org.example.model.SessionManager;
import org.example.ui.AppContext;
import org.example.ui.Command;
import org.example.ui.DisplayChoiceMenu;
import org.example.ui.mainCommandBlocks.AfterLoginCommands;
import org.example.ui.mainCommandBlocks.AuthCommandFactory;
import org.example.ui.mainCommandBlocks.CommandProvider;
import org.example.ui.mainCommandBlocks.FuncCommands;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            AppContext context = new AppContext();
            int choice;
            boolean running = true;

            while (running) {
                CommandProvider commandProvider = commandProvider(context);
                choice = askUserChoice(context.scanner);
                Command command = commandProvider.getCommand(choice);
                running = command.execute();
            }
        } catch (Exception e) {
            DatabaseManager.getInstance().closeConnection();
            throw new RuntimeException(e.getMessage());
        }
    }

    private static CommandProvider commandProvider(AppContext context) {
        if (!SessionManager.getInstance().isLoggedIn()) {
            DisplayChoiceMenu.DisplayAuthMenu();
            return new AuthCommandFactory(context);
        } else if (SessionManager.getInstance().getCurrentProject() == null) {
            DisplayChoiceMenu.DisplayAfterLoginMenu();
            return new AfterLoginCommands(context);
        } else {
            DisplayChoiceMenu.DisplayFuncMenu();
            return new FuncCommands(context);
        }
    }

    private static int askUserChoice(Scanner scanner) {
        System.out.println("Enter your choice: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Enter a choice");
        }
        return scanner.nextInt();
    }
}