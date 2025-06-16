package org.example.Ui;

import org.example.Exceptions.UsernameAlreadyExistsException;

import java.sql.SQLException;

public interface Command {
    void execute() throws SQLException;
}
