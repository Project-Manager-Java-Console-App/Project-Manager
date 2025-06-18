package org.example.Ui.UserCommands;

import org.example.Exceptions.TaskNotFound;
import org.example.Exceptions.UserNotFound;
import org.example.Service.TaskUserService;
import org.example.Ui.Command;
import org.example.model.SessionManager;
import org.example.model.Users;

import java.sql.SQLException;
import java.util.List;

public class DisplayTaskUserAdded implements Command {
    private final TaskUserService taskUserService;

    public DisplayTaskUserAdded(TaskUserService taskUserService) {
        this.taskUserService = taskUserService;
    }

    @Override
    public boolean execute() {
        Users user = SessionManager.getCurrentUser();
        if (user == null){
            throw new UserNotFound("User is empty");
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
        }catch (SQLException e){
        throw new RuntimeException(e);
        }
        return true;
    }
}
