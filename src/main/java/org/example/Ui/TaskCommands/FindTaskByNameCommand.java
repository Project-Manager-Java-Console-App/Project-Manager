package org.example.Ui.TaskCommands;

import org.example.Exceptions.TaskNotFound;
import org.example.Service.TaskService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Task;

import java.util.Scanner;

public class FindTaskByNameCommand implements Command {
    private final TaskService taskService;
    private final Scanner scanner;

    public FindTaskByNameCommand(TaskService taskService, Scanner scanner) {
        this.taskService = taskService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        scanner.nextLine();
        System.out.println("Searching for the task");
        System.out.println("Enter the name of the task: ");
        String name = scanner.nextLine();
        if (name.isEmpty()){
            System.err.println("Task name is required");
            return;
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
            System.err.println("Failed to find");
        }
    }
}
