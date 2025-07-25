package org.example.ui.userCommands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.SessionManager;
import org.example.model.Users;
import org.example.service.TaskUserService;
import org.example.ui.Command;

import java.util.List;

public class DisplayTaskUserAddedCommand implements Command {
    private final TaskUserService taskUserService;
    private final Logger logger = LogManager.getLogger(DisplayTaskUserAddedCommand.class);


    public DisplayTaskUserAddedCommand(TaskUserService taskUserService) {
        this.taskUserService = taskUserService;
    }

    @Override
    public boolean execute() {
        Users user = SessionManager.getInstance().getCurrentUser();
        if (user == null) {
            logger.error("User is empty");
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
