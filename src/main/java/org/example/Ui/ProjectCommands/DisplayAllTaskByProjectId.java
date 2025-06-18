package org.example.Ui.ProjectCommands;

import org.example.Exceptions.TaskNotFound;
import org.example.Service.TaskService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.model.Task;

import java.sql.SQLException;
import java.util.List;

public class DisplayAllTaskByProjectId implements Command {

    private final TaskService taskService;

    public DisplayAllTaskByProjectId(TaskService taskService) {
        this.taskService = taskService;
    }


    @Override
    public boolean execute() {
        Project project = SessionManager.getCurrentProject();
        if (project==null){
            System.err.println("Project is required");
            return true;
        }
        System.out.println("Displaying All Tasks from"+project.getName());
        try {
            List<Task> tasks = taskService.findTasksByProjectId(project.getId());
            for (Task task : tasks) {
                System.out.println(task);
            }
        }catch (TaskNotFound e){
            throw new TaskNotFound(e.getMessage());
        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
        return true;
    }
}
