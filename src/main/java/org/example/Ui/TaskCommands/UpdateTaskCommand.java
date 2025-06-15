package org.example.Ui.TaskCommands;

import org.example.Service.TaskService;
import org.example.Ui.Command;
import org.example.model.*;

import java.util.Scanner;

import static org.example.Ui.GlobalMethods.GlobalMethods.enterStatus;

public class UpdateTaskCommand implements Command {
    private final TaskService taskService;
    private final Scanner scanner;

    public UpdateTaskCommand(Scanner scanner, TaskService taskService) {
        this.scanner = scanner;
        this.taskService = taskService;
    }
    @Override
    public void execute() {
        scanner.nextLine();
        Task task = SessionManager.getCurrentTask();
        if (task==null){
            System.err.println("Task is required");
            return;
        }
        System.out.println("Enter new name: ");
        String name = scanner.nextLine();
        System.out.println("Enter new description: ");
        String description = scanner.nextLine();

        Status newStatus =enterStatus(scanner);
        if(name.isEmpty()||description.isEmpty()||newStatus==null){
            System.err.println("Name, description and status is required");
            return;
        }

        try{
            taskService.updateTask(name,description,newStatus,task.getId());
            System.out.println("Task updated successfully");
        }catch (Exception e){
            System.err.println("Failed to update");
        }

    }
}
