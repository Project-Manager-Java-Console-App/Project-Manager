package org.example.Ui.MainCommandBlocks;

import org.example.Ui.AppContext;
import org.example.Ui.AuthCommands.ExitCommand;
import org.example.Ui.AuthCommands.LogOutCommand;
import org.example.Ui.Command;
import org.example.Ui.ProjectCommands.CreateProjectCommand;
import org.example.Ui.ProjectCommands.FindByNameProjectCommand;


public class AfterLoginCommands implements CommandProvider {

    private final AppContext appContext;

    public AfterLoginCommands(AppContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public Command getCommand(int choice) {
        return switch (choice) {
            case 1 ->
                    new CreateProjectCommand(appContext.sessionManager, appContext.projectService, appContext.scanner);
            case 2 ->
                    new FindByNameProjectCommand(appContext.sessionManager, appContext.projectService, appContext.scanner);
            case 3 -> new LogOutCommand(appContext.sessionManager);
            case 4 -> new ExitCommand(appContext.factory);
            default -> () -> {
                System.out.println("Invalid choice");
                return true;
            };
        };
    }
}
