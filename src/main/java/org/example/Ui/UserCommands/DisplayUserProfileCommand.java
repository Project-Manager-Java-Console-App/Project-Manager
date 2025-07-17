package org.example.Ui.UserCommands;


import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Users;

public class DisplayUserProfileCommand implements Command {
    private final SessionManager sessionManager;

    public DisplayUserProfileCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean execute() {
        System.out.println("Displaying user profile");
        Users user = sessionManager.getCurrentUser();
        System.out.println(user);
        return true;
    }
}
