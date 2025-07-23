package org.example.ui.mainCommandBlocks;

import org.example.ui.AppContext;
import org.example.ui.Command;
import org.example.ui.authCommands.ExitCommand;
import org.example.ui.authCommands.LoginCommand;
import org.example.ui.authCommands.RegisterCommand;


public class AuthCommandFactory implements CommandProvider {

    private final AppContext appContext;

    public AuthCommandFactory(AppContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public Command getCommand(int choice) {
        return switch (choice) {
            case 1 -> new RegisterCommand(appContext.userService, appContext.scanner);
            case 2 -> new LoginCommand(appContext.userService, appContext.scanner);
            case 3 -> new ExitCommand();
            default -> () -> {
                System.out.println("Invalid choice");
                return true;
            };
        };
    }
}



