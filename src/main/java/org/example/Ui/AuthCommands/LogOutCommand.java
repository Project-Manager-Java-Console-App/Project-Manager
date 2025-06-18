package org.example.Ui.AuthCommands;

import org.example.Ui.Command;
import org.example.model.SessionManager;

public class LogOutCommand implements Command {

    @Override
    public boolean execute()  {
        SessionManager.logout();
        System.out.println("Logged out successfully");
        return true;
    }
}
