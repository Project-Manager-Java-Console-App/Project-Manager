package org.example.Ui.AuthCommands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Ui.Command;
import org.example.model.SessionManager;

public class LogOutCommand implements Command {
    private final SessionManager sessionManager;
    private final Logger logger = LogManager.getLogger(LogOutCommand.class);

    public LogOutCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean execute() {
        sessionManager.logout();
        System.out.println("Logged out successfully");
        logger.info("Logged out successfully");
        return true;
    }
}
