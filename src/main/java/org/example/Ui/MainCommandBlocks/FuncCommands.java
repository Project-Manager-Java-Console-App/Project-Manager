package org.example.Ui.MainCommandBlocks;

import org.example.Service.*;
import org.example.Ui.AppContext;
import org.example.Ui.AuthCommands.ExitCommand;
import org.example.Ui.AuthCommands.LogOutCommand;
import org.example.Ui.Command;
import org.example.Ui.ProjectCommands.*;
import org.example.Ui.TaskCommands.*;
import org.example.Ui.UserCommands.*;
import org.example.dataBase.DatabaseUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FuncCommands implements CommandProvider {

    private final Map<Integer,Command> commands = new HashMap<>();

    public FuncCommands(
            AppContext appContext) {
        commands.put(1,new CreateProjectCommand(appContext.projectService,appContext.scanner));
        commands.put(2,new UpdateProjectCommand(appContext.projectService,appContext.scanner));
        commands.put(3,new FindByNameProject(appContext.projectService,appContext.scanner));
        commands.put(4,new FindByIdProjectCommand(appContext.projectService,appContext.scanner));
        commands.put(5,new AddTaskToProject(appContext.projectService,appContext.taskService,appContext.scanner));
        commands.put(6,new AddUserToProject(appContext.projectUserService,appContext.userService,appContext.scanner));
        commands.put(7,new RemovingUserFromProject(appContext.projectUserService,appContext.userService,appContext.scanner));
        commands.put(8,new DisplayAllTaskByProjectId(appContext.taskService));
        commands.put(9,new DisplayAllUsersByProject(appContext.projectUserService));
        commands.put(10,new DeletingProjectCommand(appContext.projectService,appContext.scanner));
        commands.put(11,new CreateTaskInProject(appContext.taskService,appContext.scanner));
        commands.put(12,new UpdateTaskCommand(appContext.scanner,appContext.taskService));
        commands.put(13,new FindTaskByNameCommand(appContext.taskService,appContext.scanner));
        commands.put(14,new FindByIdTaskCommand(appContext.taskService,appContext.scanner));
        commands.put(15,new RemovingUserFromTaskCommand(appContext.taskUserService,appContext.userService,appContext.scanner));
        commands.put(16,new ChangeTaskStatusCommand(appContext.taskService,appContext.scanner));
        commands.put(17,new AddingUserToTaskCommand(appContext.taskUserService,appContext.userService,appContext.scanner));
        commands.put(18,new DisplayUsersAddedToTaskCommand(appContext.taskUserService));
        commands.put(19,new DeleteTaskCommand(appContext.taskService,appContext.scanner));
        commands.put(20,new ChangeUsernameCommand(appContext.userService,appContext.scanner));
        commands.put(21,new FindUserByNameCommand(appContext.userService,appContext.scanner));
        commands.put(22,new FindByIdUserCommand(appContext.userService,appContext.scanner));
        commands.put(23,new DisplayAllProjectsCreatedByUser(appContext.projectUserService));
        commands.put(24,new DisplayAllProjectsUserAddedCommand(appContext.projectUserService));
        commands.put(25,new DisplayTasksCreatedByUserCommand(appContext.taskService));
        commands.put(26,new DisplayTaskUserAdded(appContext.taskUserService));
        commands.put(27,new DisplayUserProfile());
        commands.put(28,new DeletingUserCommand(appContext.userService,appContext.projectService,appContext.projectUserService,appContext.scanner));
        commands.put(29,new LogOutCommand());
        commands.put(30,new ExitCommand(appContext.factory));

    }

    @Override
    public Command getCommand(int choice){
        return commands.getOrDefault(choice,() ->{ System.out.println("Invalid choice");return false;});
    }
}
