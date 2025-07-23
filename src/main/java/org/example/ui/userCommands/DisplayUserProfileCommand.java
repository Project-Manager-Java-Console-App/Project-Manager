package org.example.ui.userCommands;


import org.example.model.SessionManager;
import org.example.model.Users;
import org.example.ui.Command;

public class DisplayUserProfileCommand implements Command {

    @Override
    public boolean execute() {
        System.out.println("Displaying user profile");
        Users user = SessionManager.getInstance().getCurrentUser();
        System.out.println(user);
        return true;
    }
}
