package org.example.Ui.UserCommands;

import org.example.Service.TaskUserService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Task;
import org.example.model.Users;

import java.util.List;

public class DisplayTaskUserAdded implements Command {
    private final TaskUserService taskUserService;

    public DisplayTaskUserAdded(TaskUserService taskUserService) {
        this.taskUserService = taskUserService;
    }

    @Override
    public void execute() {
        Users user = SessionManager.getCurrentUser();
        if (user == null){
            System.err.println("User is required");
            return;
        }
        System.out.println("Displaying Task in which "+user.getUsername()+" is assigned!");

        System.out.println("Assigned to task: ");
        try{
            List<Integer> tasks = taskUserService.getAllTaskAssignedToUser(user.getId());
            if(tasks.isEmpty()){
                System.out.println("No tasks assigned to user");
            }
            else{
                for(Integer ids : tasks){
                    System.out.println(ids);
                }
            }
        }catch (Exception e){
            System.err.println("Failed to display task assigned to user");
        }
    }
}
