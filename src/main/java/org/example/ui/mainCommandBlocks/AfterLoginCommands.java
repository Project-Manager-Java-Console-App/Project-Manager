package org.example.ui.mainCommandBlocks;

import org.example.ui.AppContext;
import org.example.ui.Command;
import org.example.ui.authCommands.ExitCommand;
import org.example.ui.authCommands.LogOutCommand;
import org.example.ui.projectCommands.CreateProjectCommand;
import org.example.ui.projectCommands.FindByNameProjectCommand;


public class AfterLoginCommands implements CommandProvider {

    private final AppContext appContext;

    public AfterLoginCommands(AppContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public Command getCommand(int choice) {
        return switch (choice) {
            case 1 -> new CreateProjectCommand(appContext.projectService, appContext.scanner);
            case 2 -> new FindByNameProjectCommand(appContext.projectService, appContext.scanner);
            case 3 -> new LogOutCommand();
            case 4 -> new ExitCommand();
            default -> () -> {
                System.out.println("Invalid choice");
                return true;
            };
        };
    }
}
