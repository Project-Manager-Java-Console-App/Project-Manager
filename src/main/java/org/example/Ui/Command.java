package org.example.Ui;


import java.sql.SQLException;

public interface Command {
    void execute() throws SQLException;
}
