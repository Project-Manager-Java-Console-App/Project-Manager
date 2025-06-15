package org.example.Ui.UserCommands;

import org.example.Exceptions.UserIdNotFound;
import org.example.Service.TaskUserService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Users;

import java.util.List;

public class DisplayTasksCreatedByUserCommand implements Command {
    private final TaskUserService taskUserService;

    public DisplayTasksCreatedByUserCommand(TaskUserService taskUserService) {
        this.taskUserService = taskUserService;
    }

    @Override
    public void execute()  {
        Users user = SessionManager.getCurrentUser();
        if (user == null){
            System.err.println("User is required");
            return;
        }
        System.out.println("Displaying tasks created by "+ user.getUsername());

        System.out.println("Tasks added to: ");
        try{
            List<Integer> task_ids = taskUserService.getAllTaskAssignedToUser(user.getId());
            if (task_ids.isEmpty()){
                System.out.println("No tasks assigned to user");
            }else {
                for (Integer task_id : task_ids){
                    System.out.println(task_id);
                }
            }

        }catch (Exception e){
            throw new UserIdNotFound();
        }

    }
}
