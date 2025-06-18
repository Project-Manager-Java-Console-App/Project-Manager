package org.example.Ui.TaskCommands;

import org.example.Exceptions.TaskNotFound;
import org.example.Exceptions.UsernameAlreadyExistsException;
import org.example.Service.TaskService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Task;

import java.sql.SQLException;
import java.util.Scanner;

public class FindTaskByNameCommand implements Command {
    private final TaskService taskService;
    private final Scanner scanner;

    public FindTaskByNameCommand(TaskService taskService, Scanner scanner) {
        this.taskService = taskService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        System.out.println("Searching for the task");
        System.out.println("Enter the name of the task: ");
        String name = scanner.nextLine();
        if (name.isEmpty()){
            System.err.println("Task name is required");
        }
        try{
            Task task = taskService.findByName(name);
            if(task == null){
                throw new TaskNotFound(name);
            }
            SessionManager.setCurrentTask(task);
            System.out.println("Task found");
            System.out.println(task);
        }catch (Exception e){
            throw new TaskNotFound("Task not found");
        }
        return true;
    }
}
