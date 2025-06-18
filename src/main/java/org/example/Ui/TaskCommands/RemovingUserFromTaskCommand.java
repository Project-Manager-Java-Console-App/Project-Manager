package org.example.Ui.TaskCommands;

import org.example.Service.TaskUserService;
import org.example.Service.UserService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Task;
import org.example.model.Users;

import java.sql.SQLException;
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
    public boolean execute() {
        scanner.nextLine();
        Task task = SessionManager.getCurrentTask();
        if (task==null){
            throw new RuntimeException("Task is required");
        }
        System.out.println("Removing user from task: " + task.getName());
        System.out.println("Enter username to remove: ");
        String username = scanner.nextLine();
        if (username.isEmpty()){
            throw new RuntimeException("username is required");
        }
        try{
            Users user = userService.findByName(username);
            if(user == null){
                throw new RuntimeException("User is required");
            }
            boolean removed = taskUserService.removeUserFromTask(task.getId(), user.getId());
            if(!removed){
                throw  new SQLException("Failed to remove user from task");
            }
            System.out.println("Removed user from task: " + task.getName());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return true;
    }
}
