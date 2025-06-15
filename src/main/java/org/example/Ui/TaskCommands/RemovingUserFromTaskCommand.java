package org.example.Ui.TaskCommands;

import org.example.Exceptions.UserNotFound;
import org.example.Service.TaskUserService;
import org.example.Service.UserService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Task;
import org.example.model.Users;

import java.util.Scanner;

public class RemovingUserFromTaskCommand implements Command {

    private final TaskUserService taskUserService;
    private final UserService userService;
    private final Scanner scanner;

    public RemovingUserFromTaskCommand(TaskUserService taskUserService, UserService userService, Scanner scanner) {
        this.taskUserService = taskUserService;
        this.userService = userService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        scanner.nextLine();
        Task task = SessionManager.getCurrentTask();
        if (task==null){
            System.err.println("Task is required");
            return;
        }
        System.out.println("Removing user from task: " + task.getName());
        System.out.println("Enter username to remove: ");
        String username = scanner.nextLine();
        if (username.isEmpty()){
            System.err.println("Username is required");
            return;
        }
        try{
            Users user = userService.findByName(username);
            if(user == null){
                throw new UserNotFound(username);
            }
            boolean removed = taskUserService.removeUserFromTask(task.getId(), user.getId());
            if(!removed){
                System.err.println("Failed to remove user from task: " + task.getName());
            }
            System.out.println("Removed user from task: " + task.getName());
        }catch (Exception e){
            System.err.println("Failed to remove user from task: " + task.getName());
        }
    }
}
