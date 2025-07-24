package org.example.ui.authCommands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.SessionManager;
import org.example.ui.Command;

public class LogOutCommand implements Command {
    private final Logger logger = LogManager.getLogger(LogOutCommand.class);

    @Override
    public boolean execute() {
        SessionManager.getInstance().logout();
        logger.info("Logged out successfully");
        return true;
    }
}
