package org.example.Ui;


import java.sql.SQLException;

public interface Command {
    boolean execute() throws SQLException;
}
