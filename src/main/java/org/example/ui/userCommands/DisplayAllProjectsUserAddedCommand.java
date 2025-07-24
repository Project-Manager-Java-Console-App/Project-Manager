package org.example.ui.userCommands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.SessionManager;
import org.example.model.Users;
import org.example.service.ProjectUserService;
import org.example.ui.Command;

import java.util.List;

public class DisplayAllProjectsUserAddedCommand implements Command {
    private final ProjectUserService projectUserService;
    private final Logger logger = LogManager.getLogger(DisplayAllProjectsUserAddedCommand.class);

    public DisplayAllProjectsUserAddedCommand(ProjectUserService projectUserService) {
        this.projectUserService = projectUserService;
    }

    @Override
    public boolean execute() {
        Users user = SessionManager.getInstance().getCurrentUser();
        if (user == null) {
            logger.error("User is empty");
            return true;
        }
        System.out.println("Displaying all projects in which the user: " + user.getName() + " is added.");

        System.out.println("Projects added to: ");
        List<Integer> project_id = projectUserService.getAllProjectsWhereUserIsAdded(user.getId());
        if (project_id.isEmpty()) {
            System.out.println("No projects assigns user: " + user.getName());
        } else {
            for (Integer id : project_id) {
                System.out.println(id);
            }
        }
        return true;
    }
}
