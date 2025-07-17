package org.example;

import org.example.Ui.AppContext;
import org.example.Ui.Command;
import org.example.Ui.DisplayChoiceMenu;
import org.example.Ui.MainCommandBlocks.AfterLoginCommands;
import org.example.Ui.MainCommandBlocks.AuthCommandFactory;
import org.example.Ui.MainCommandBlocks.CommandProvider;
import org.example.Ui.MainCommandBlocks.FuncCommands;

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
            throw new RuntimeException(e.getMessage());
        }
    }

    private static CommandProvider commandProvider(AppContext context) {
        if (!context.sessionManager.isLoggedIn()) {
            DisplayChoiceMenu.DisplayAuthMenu();
            return new AuthCommandFactory(context);
        } else if (context.sessionManager.getCurrentProject() == null) {
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