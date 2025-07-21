package org.example.Ui.AuthCommands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Ui.Command;
import org.example.dataBase.DatabaseUtils;

import java.sql.SQLException;

public class ExitCommand implements Command {
    private final DatabaseUtils dbUtils;
    private final Logger logger = LogManager.getLogger(ExitCommand.class);

    public ExitCommand(DatabaseUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    @Override
    public boolean execute() {
        try {
            dbUtils.closeConnection();
            logger.info("Closing database connection and exiting.");
            System.out.println("Exiting...");

            return false;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            logger.error("Failed to exit the application.", e.getCause());
            return true;
        }

    }
}
