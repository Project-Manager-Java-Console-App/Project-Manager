package org.example.Ui.ProjectCommands;

import org.example.Exceptions.TaskNotFound;
import org.example.Service.ProjectService;
import org.example.Service.TaskService;
import org.example.Ui.Command;
import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.model.Task;

import java.util.Scanner;

public class AddTaskToProject implements Command {
    private final ProjectService projectService;
    private final TaskService taskService;
    private final Scanner scanner;

    public AddTaskToProject(ProjectService projectService,TaskService taskService, Scanner scanner) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
     scanner.nextLine();
     Project project = SessionManager.getCurrentProject();
     if(project==null){
         System.err.println("No current project selected.");
         return;
     }
     System.out.println("Adding task to project: "+project.getName());
     System.out.println("Please enter the name of the task you would like to add: ");
     String name = scanner.nextLine();
     if (name.isEmpty()) {
         System.err.println("Please enter the name of the task you would like to add: ");
         return;
     }
     try{
         Task task = taskService.findByName(name);

         if(task == null){
             throw new TaskNotFound("Task "+name+"Not Found");
         }
         projectService.addTaskToProject(project.getId(), task.getId());

     }catch (Exception e){
         System.err.println("Failed to add task to project: "+project.getName());
     }

    }
}
