package org.example.Ui.UserCommands;

import org.example.Service.TaskService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Task;
import org.example.model.Users;

import java.util.List;

public class DisplayTasksCreatedByUserCommand implements Command {
    private final TaskService taskService;
    private final SessionManager sessionManager;

    public DisplayTasksCreatedByUserCommand(SessionManager sessionManager, TaskService taskService) {
        this.taskService = taskService;
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean execute() {
        Users user = sessionManager.getCurrentUser();
        if (user == null) {
            System.err.println("User is empty");
            return true;
        }
        System.out.println("Displaying tasks created by " + user.getName());

        System.out.println("Tasks added to: ");
        List<Task> task = taskService.getAllTasksCreatedByUser(user.getId());
        if (task.isEmpty()) {
            System.out.println("No tasks assigned to user");
        } else {
            for (Task tasks : task) {
                System.out.println(tasks);
            }
        }

        return true;
    }
}
