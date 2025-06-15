package org.example.Ui.MainCommandBlocks;

import org.example.Service.*;
import org.example.Ui.AuthCommands.ExitCommand;
import org.example.Ui.AuthCommands.LogOutCommand;
import org.example.Ui.Command;
import org.example.Ui.ProjectCommands.*;
import org.example.Ui.TaskCommands.*;
import org.example.Ui.UserCommands.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FuncCommands {

    private final Map<Integer,Command> commands = new HashMap<>();

    public FuncCommands(
            ProjectService projectService,
            TaskService taskService,
            UserService userService,
            ProjectUserService projectUserService,
            TaskUserService taskUserService,
            Scanner scanner) {
        commands.put(1,new CreateProjectCommand(projectService,scanner));
        commands.put(2,new UpdateProjectCommand(projectService,scanner));
        commands.put(3,new FindByNameProject(projectService,scanner));
        commands.put(4,new FindByIdProjectCommand(projectService,scanner));
        commands.put(5,new AddTaskToProject(projectService,taskService,scanner));
        commands.put(6,new AddUserToProject(projectUserService,userService,scanner));
        commands.put(7,new RemovingUserFromProject(projectUserService,userService,scanner));
        commands.put(8,new DisplayAllTaskByProjectId(taskService));
        commands.put(9,new DisplayAllUsersByProject(projectUserService));
        commands.put(10,new DeletingProjectCommand(projectService,scanner));
        commands.put(11,new CreateTaskInProject(taskService,scanner));
        commands.put(12,new UpdateTaskCommand(scanner,taskService));
        commands.put(13,new FindTaskByNameCommand(taskService,scanner));
        commands.put(14,new FindByIdTaskCommand(taskService,scanner));
        commands.put(15,new RemovingUserFromTaskCommand(taskUserService,userService,scanner));
        commands.put(16,new ChangeTaskStatusCommand(taskService,scanner));
        commands.put(17,new AddingUserToTaskCommand(taskUserService,userService,scanner));
        commands.put(18,new DisplayUsersAddedToTaskCommand(taskUserService));
        commands.put(19,new DeleteTaskCommand(taskService,scanner));
        commands.put(20,new ChangeUsernameCommand(userService,scanner));
        commands.put(21,new FindUserByNameCommand(userService,scanner));
        commands.put(22,new FindByIdUserCommand(userService,scanner));
        commands.put(23,new DisplayAllProjectsCreatedByUser(projectUserService));
        commands.put(24,new DisplayAllProjectsUserAddedCommand(projectUserService));
        commands.put(25,new DisplayTasksCreatedByUserCommand(taskService));
        commands.put(26,new DisplayTaskUserAdded(taskUserService));
        commands.put(27,new DisplayUserProfile());
        commands.put(28,new DeletingUserCommand(userService,scanner));
        commands.put(29,new LogOutCommand());
        commands.put(30,new ExitCommand());

    }

    public Command getCommand(int choice){
        Command command = commands.get(choice);
        if(command == null){
            command = ()-> System.out.println("Invalid choice");
        }
        return command;
    }
}
