package org.example.Ui.UserCommands;

import org.example.Exceptions.UserIdNotFound;
import org.example.Exceptions.UserNotFound;
import org.example.Service.TaskService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Task;
import org.example.model.Users;

import java.util.List;

public class DisplayTasksCreatedByUserCommand implements Command {
    private final TaskService taskService;

    public DisplayTasksCreatedByUserCommand(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public boolean execute()  {
        Users user = SessionManager.getCurrentUser();
        if (user == null){
            throw new UserNotFound("User is empty");
        }
        System.out.println("Displaying tasks created by "+ user.getUsername());

        System.out.println("Tasks added to: ");
        try{
            List<Task> task = taskService.getAllTasksCreatedByUser(user.getId());
            if (task.isEmpty()){
                System.out.println("No tasks assigned to user");
            }else {
                for (Task tasks : task){
                    System.out.println(tasks);
                }
            }

        }catch (Exception e){
            throw new UserIdNotFound();
        }
        return true;
    }
}
