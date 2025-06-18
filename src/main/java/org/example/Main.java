package org.example;

import org.example.Ui.AppContext;
import org.example.Ui.Command;
import org.example.Ui.DisplayChoiceMenu;
import org.example.Ui.MainCommandBlocks.AfterLoginCommands;
import org.example.Ui.MainCommandBlocks.AuthCommandFactory;
import org.example.Ui.MainCommandBlocks.CommandProvider;
import org.example.Ui.MainCommandBlocks.FuncCommands;
import org.example.model.SessionManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)  {

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
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }



    private static CommandProvider commandProvider(AppContext context) {
        if(!SessionManager.isLoggedIn()) {
            DisplayChoiceMenu.DisplayAuthMenu();
            return new AuthCommandFactory( context.userService, context.factory,context.scanner);
        }else if(SessionManager.getCurrentProject() == null) {
            DisplayChoiceMenu.DisplayAfterLoginMenu();
            return new AfterLoginCommands(context.projectService,context.factory,context.scanner);
        }else {
            DisplayChoiceMenu.DisplayFuncMenu();
            return new FuncCommands(
                    context.projectService,
                    context.taskService,
                    context.userService,
                    context.projectUserService,
                    context.taskUserService,
                    context.factory,
                    context.scanner
            );
        }

    }

    private static int askUserChoice(Scanner scanner){
        System.out.println("Enter your choice: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Enter a choice");
        }
        return scanner.nextInt();
    }
}