package org.example.Ui.MainCommandBlocks;

import org.example.Ui.AppContext;
import org.example.Ui.AuthCommands.ExitCommand;
import org.example.Ui.AuthCommands.LoginCommand;
import org.example.Ui.AuthCommands.RegisterCommand;
import org.example.Ui.Command;


public class AuthCommandFactory implements CommandProvider{

        private final AppContext appContext;
        public AuthCommandFactory(AppContext appContext) {
            this.appContext = appContext;
        }

    @Override
    public Command getCommand(int choice) {
        return switch (choice){
            case 1 -> new RegisterCommand(appContext.userService, appContext.scanner);
            case 2 -> new LoginCommand(appContext.userService, appContext.scanner);
            case 3 -> new ExitCommand(appContext.factory);
            default -> () ->{System.out.println("Invalid choice");return true;};
        };
    }
}



