package org.example.Ui.UserCommands;

import org.example.Service.TaskService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Task;
import org.example.model.Users;

import java.util.List;

public class DisplayTaskUserAdded implements Command {
    private final TaskService taskService;
    public DisplayTaskUserAdded(TaskService taskService) {
        this.taskService = taskService;
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
            List<Task> tasks = taskService.getAllTasksCreatedByUser(user.getId());
            if(tasks.isEmpty()){
                System.out.println("No tasks assigned to user");
            }
            else{
                for(Task task : tasks){
                    System.out.println(task);
                }
            }
        }catch (Exception e){
            System.err.println("Failed to display task assigned to user");
        }
    }
}
