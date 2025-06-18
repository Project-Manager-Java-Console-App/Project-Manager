package org.example.Ui.TaskCommands;

import org.example.Service.TaskService;
import org.example.Ui.Command;
import org.example.model.*;

import java.time.LocalDate;
import java.util.Scanner;

public class CreateTaskInProject implements Command {
    private final TaskService taskService;
    private final Scanner scanner;

    public CreateTaskInProject(TaskService taskService, Scanner scanner) {
        this.taskService = taskService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        Project project = SessionManager.getCurrentProject();
        if (project==null){
            System.err.println("Project is required");
            return true;
        }
        Users user = SessionManager.getCurrentUser();
        if (user==null){
            System.err.println("User is required");
            return true;
        }
        System.out.println("Creating task " + project.getName() + " in " + user.getUsername());
        System.out.println("Please enter name: ");
        String name = scanner.nextLine();
        System.out.println("Please enter description: ");
        String description = scanner.nextLine();
        if (name.isEmpty() || description.isEmpty()){
            System.err.println("Name or description is required");
        }

        try {
            Task task = taskService.createTask(name, project.getId(), description, Status.IN_PROGRESS, LocalDate.now(), user.getId());
            if (task == null) {
                throw new RuntimeException("Failed to create task " + project.getName() + " in " + user.getUsername());
            }
            SessionManager.setCurrentTask(task);
        }catch (Exception e){
            System.err.println("Failed to create task in" + project.getName() + " by " + user.getUsername());
        }
        return true;
    }
}
