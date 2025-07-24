package org.example.ui.userCommands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.SessionManager;
import org.example.model.Task;
import org.example.model.Users;
import org.example.service.TaskService;
import org.example.ui.Command;

import java.util.List;

public class DisplayTasksCreatedByUserCommand implements Command {
    private final TaskService taskService;
    private final Logger logger = LogManager.getLogger(DisplayTasksCreatedByUserCommand.class);

    public DisplayTasksCreatedByUserCommand(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public boolean execute() {
        Users user = SessionManager.getInstance().getCurrentUser();
        if (user == null) {
            logger.error("User is empty");
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
