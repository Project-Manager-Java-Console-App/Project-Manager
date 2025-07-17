package org.example.Ui.TaskCommands;

import org.example.Service.TaskUserService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Task;

import java.util.List;

public class DisplayUsersAddedToTaskCommand implements Command {
    private final TaskUserService taskUserService;
    private final SessionManager sessionManager;

    public DisplayUsersAddedToTaskCommand(SessionManager sessionManager, TaskUserService taskUserService) {
        this.taskUserService = taskUserService;
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean execute() {
        Task task = sessionManager.getCurrentTask();
        if (task == null) {
            System.err.println("Task is null");
            return true;
        }
        System.out.println("Displaying users added to " + task.getName());

        List<Integer> users = taskUserService.getUsersInTask(task.getId());
        if (users.isEmpty()) {
            System.out.println("No users added to the task");
            return true;
        }
        for (Integer user : users) {
            System.out.println(user);
        }
        return true;
    }
}
