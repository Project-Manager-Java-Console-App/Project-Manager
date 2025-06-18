package org.example.Ui.TaskCommands;

import org.example.Service.TaskUserService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Task;

import java.util.List;

public class DisplayUsersAddedToTaskCommand implements Command {
    private final TaskUserService taskUserService;

    public DisplayUsersAddedToTaskCommand(TaskUserService taskUserService) {
        this.taskUserService = taskUserService;
    }

    @Override
    public boolean execute() {
        Task task = SessionManager.getCurrentTask();
        if (task == null){
            System.err.println("Task is required");
            return true;
        }
        System.out.println("Displaying users added to "+ task.getName());

        try{
            List<Integer> users = taskUserService.getUsersInTask(task.getId());
            if(users.isEmpty()){
                System.out.println("No users added to the task");
            }
            for (Integer user : users) {
                System.out.println(user);
            }
        }catch (Exception e){
            System.err.println("Failed to display users");
        }
        return true;
    }
}
