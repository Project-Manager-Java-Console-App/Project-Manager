package org.example.Ui.TaskCommands;

import org.example.Service.TaskService;
import org.example.Ui.Command;
import org.example.model.*;

import java.sql.SQLException;
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
    public boolean execute() {
        scanner.nextLine();
        Task task = SessionManager.getCurrentTask();
        if (task==null){
            throw new RuntimeException("Task not found");
        }
        System.out.println("Enter new name: ");
        String name = scanner.nextLine();
        System.out.println("Enter new description: ");
        String description = scanner.nextLine();

        Status newStatus =enterStatus(scanner);
        if(name.isEmpty()||description.isEmpty()||newStatus==null){
            throw new RuntimeException("Name and description are required");
        }

        try{
            taskService.updateTask(name,description,newStatus,task.getId());
            System.out.println("Task updated successfully");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return true;
    }
}
