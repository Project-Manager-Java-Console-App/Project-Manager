package org.example.Ui.AuthCommands;

import org.example.Ui.Command;
import org.example.model.SessionManager;

public class LogOutCommand implements Command {
    private final SessionManager sessionManager;

    public LogOutCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean execute() {
        sessionManager.logout();
        System.out.println("Logged out successfully");
        return true;
    }
}
