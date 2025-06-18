package org.example.Ui.TaskCommands;

import org.example.Exceptions.UserNotFound;
import org.example.Service.TaskService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Task;

import java.sql.SQLException;
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
            throw new UserNotFound("Task not found");
        }
        System.out.println("Deleting task "+task.getName());
        System.out.println("Do you want to delete the task "+task.getName()+ "(Y/N)");
        String answer = scanner.nextLine();
        if (answer.isEmpty()){
            throw new IllegalArgumentException("answer cannot be empty");
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
        }catch (SQLException e){
            throw new RuntimeException("Failed to delete task");
        }
        return true;
    }
}
