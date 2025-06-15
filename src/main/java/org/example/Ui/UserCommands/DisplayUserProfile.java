package org.example.Ui.UserCommands;


import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Users;

public class DisplayUserProfile implements Command {

    @Override
    public void execute()  {
        System.out.println("Displaying user profile");
        Users user = SessionManager.getCurrentUser();
        System.out.println(user);
    }
}
