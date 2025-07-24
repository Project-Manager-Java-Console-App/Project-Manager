package org.example.ui.projectCommands;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.model.Task;
import org.example.service.TaskService;
import org.example.ui.Command;

import java.util.List;

public class DisplayAllTaskByProjectIdCommand implements Command {

    private final TaskService taskService;
    private final Logger logger = LogManager.getLogger(DisplayAllTaskByProjectIdCommand.class);

    public DisplayAllTaskByProjectIdCommand(TaskService taskService) {
        this.taskService = taskService;
    }


    @Override
    public boolean execute() {
        Project project = SessionManager.getInstance().getCurrentProject();
        if (project == null) {
            logger.error("Project is required");
            return true;
        }
        System.out.println("Displaying All Tasks from" + project.getName());
        List<Task> tasks = taskService.findTasksByProjectId(project.getId());
        for (Task task : tasks) {
            System.out.println(task);
        }
        return true;
    }
}
