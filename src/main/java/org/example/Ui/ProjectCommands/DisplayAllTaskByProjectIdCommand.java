package org.example.Ui.ProjectCommands;


import org.example.Service.TaskService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.model.Task;

import java.util.List;

public class DisplayAllTaskByProjectIdCommand implements Command {

    private final TaskService taskService;
    private final SessionManager sessionManager;

    public DisplayAllTaskByProjectIdCommand(SessionManager sessionManager, TaskService taskService) {
        this.taskService = taskService;
        this.sessionManager = sessionManager;
    }


    @Override
    public boolean execute() {
        Project project = sessionManager.getCurrentProject();
        if (project == null) {
            System.err.println("Project is required");
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
