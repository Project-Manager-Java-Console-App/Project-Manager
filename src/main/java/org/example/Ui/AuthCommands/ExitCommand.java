package org.example.Ui.AuthCommands;

import org.example.Ui.Command;
import org.example.dataBase.DatabaseUtils;

import java.sql.SQLException;

public class ExitCommand implements Command {
    private final DatabaseUtils dbUtils;

    public ExitCommand(DatabaseUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    @Override
    public void execute() throws SQLException {
        try {
            dbUtils.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Exiting...");
     System.exit(0);
    }
}
