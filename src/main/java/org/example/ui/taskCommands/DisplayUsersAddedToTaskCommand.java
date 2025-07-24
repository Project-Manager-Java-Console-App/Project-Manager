package org.example.ui.taskCommands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.SessionManager;
import org.example.model.Task;
import org.example.service.TaskUserService;
import org.example.ui.Command;

import java.util.List;

public class DisplayUsersAddedToTaskCommand implements Command {
    private final TaskUserService taskUserService;
    private final Logger logger = LogManager.getLogger(DisplayUsersAddedToTaskCommand.class);

    public DisplayUsersAddedToTaskCommand(TaskUserService taskUserService) {
        this.taskUserService = taskUserService;
    }

    @Override
    public boolean execute() {
        Task task = SessionManager.getInstance().getCurrentTask();
        if (task == null) {
            logger.error("Task is null");
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
