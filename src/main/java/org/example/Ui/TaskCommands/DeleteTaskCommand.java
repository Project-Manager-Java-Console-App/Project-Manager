package org.example.Ui.TaskCommands;

import org.example.Service.TaskService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Task;

import java.util.Scanner;

public class DeleteTaskCommand implements Command {

    private final TaskService taskService;
    private final Scanner scanner;

    public DeleteTaskCommand(TaskService taskService,Scanner scanner) {
        this.taskService = taskService;
        this.scanner = scanner;
    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        Task task = SessionManager.getCurrentTask();
        if(task==null){
            System.err.println("Task is required");
            return true;
        }
        System.out.println("Deleting task "+task.getName());
        System.out.println("Do you want to delete the task "+task.getName()+ "(Y/N)");
        String answer = scanner.nextLine();
        if (answer.isEmpty()){
            System.err.println("Answer is required");
        }
        try{
            if (answer.equals("Y")) {
                boolean deleted = taskService.delete(task);
                if (!deleted) {
                    throw new RuntimeException("Failed to delete task");
                }
                System.out.println("Task deleted successfully");
                SessionManager.setCurrentTask(null);
            }else if (answer.equals("N")) {
                System.out.println("Task is not deleted.");
            }
        }catch (Exception e){
            System.err.println("Failed to delete task");
        }
        return true;
    }
}
