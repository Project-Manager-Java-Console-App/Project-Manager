package org.example.Ui.UserCommands;

import org.example.Service.TaskUserService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Users;

import java.util.List;

public class DisplayTaskUserAddedCommand implements Command {
    private final TaskUserService taskUserService;
    private final SessionManager sessionManager;

    public DisplayTaskUserAddedCommand(SessionManager sessionManager, TaskUserService taskUserService) {
        this.taskUserService = taskUserService;
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean execute() {
        Users user = sessionManager.getCurrentUser();
        if (user == null) {
            System.err.println("User is empty");
            return true;
        }
        System.out.println("Displaying Task in which " + user.getName() + " is assigned!");

        System.out.println("Assigned to task: ");
        List<Integer> tasks = taskUserService.getAllTaskAssignedToUser(user.getId());
        if (tasks.isEmpty()) {
            System.out.println("No tasks assigned to user");
        } else {
            for (Integer ids : tasks) {
                System.out.println(ids);
            }
        }
        return true;
    }
}
