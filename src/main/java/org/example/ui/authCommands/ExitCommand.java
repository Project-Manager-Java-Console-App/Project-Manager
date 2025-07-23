package org.example.ui.authCommands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dataBase.DatabaseManager;
import org.example.ui.Command;

public class ExitCommand implements Command {

    private final Logger logger = LogManager.getLogger(ExitCommand.class);

    @Override
    public boolean execute() {

        logger.info("Closing database connection and exiting.");
        System.out.println("Exiting...");
        DatabaseManager.getInstance().closeConnection();
        return false;

    }
}
