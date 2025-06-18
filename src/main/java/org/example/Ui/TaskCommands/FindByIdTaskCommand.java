package org.example.Ui.TaskCommands;

import org.example.Exceptions.TaskNotFound;
import org.example.Service.TaskService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Task;

import java.sql.SQLException;
import java.util.Scanner;

public class FindByIdTaskCommand implements Command {
    private final TaskService taskService;
    private final Scanner scanner;

    public FindByIdTaskCommand(TaskService taskService, Scanner scanner) {
        this.taskService = taskService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute()  {
        scanner.nextLine();
        System.out.println("Searching for Task by id");
        System.out.println("Enter task id: ");
        int id = scanner.nextInt();
        if (id==0){
            System.err.println("Task id is required");
        }

        try{
            Task task = taskService.findById(id);
            if(task==null){
                throw new TaskNotFound("Task not found");
            }
            SessionManager.setCurrentTask(task);
            System.out.println("Task found");
            System.out.println(task);
        }catch (SQLException e){
            throw new TaskNotFound("Task not found");
        }
        return true;
    }
}
