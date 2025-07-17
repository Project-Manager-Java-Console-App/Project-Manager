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
    public boolean execute() {
        try {
            dbUtils.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return true;
        }
        System.out.println("Exiting...");
        return false;
    }
}
