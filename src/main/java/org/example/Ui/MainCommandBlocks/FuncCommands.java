package org.example.Ui.MainCommandBlocks;


import org.example.Ui.AppContext;
import org.example.Ui.AuthCommands.ExitCommand;
import org.example.Ui.AuthCommands.LogOutCommand;
import org.example.Ui.Command;
import org.example.Ui.ProjectCommands.*;
import org.example.Ui.TaskCommands.*;
import org.example.Ui.UserCommands.*;

import java.util.HashMap;
import java.util.Map;

public class FuncCommands implements CommandProvider {

    private final Map<Integer, Command> commands = new HashMap<>();

    public FuncCommands(
            AppContext appContext) {
        commands.put(1, new CreateProjectCommand(appContext.sessionManager, appContext.projectService, appContext.scanner));
        commands.put(2, new UpdateProjectCommand(appContext.sessionManager, appContext.projectService, appContext.scanner));
        commands.put(3, new FindByNameProjectCommand(appContext.sessionManager, appContext.projectService, appContext.scanner));
        commands.put(4, new FindByIdProjectCommand(appContext.sessionManager, appContext.projectService, appContext.scanner));
        commands.put(5, new AddTaskToProjectCommand(appContext.sessionManager, appContext.projectService, appContext.taskService, appContext.scanner));
        commands.put(6, new AddUserToProjectCommand(appContext.sessionManager, appContext.projectUserService, appContext.userService, appContext.scanner));
        commands.put(7, new RemovingUserFromProjectCommand(appContext.sessionManager, appContext.projectUserService, appContext.userService, appContext.scanner));
        commands.put(8, new DisplayAllTaskByProjectIdCommand(appContext.sessionManager, appContext.taskService));
        commands.put(9, new DisplayAllUsersByProjectCommand(appContext.sessionManager, appContext.projectUserService));
        commands.put(10, new DeletingProjectCommand(appContext.sessionManager, appContext.projectService, appContext.scanner));
        commands.put(11, new CreateTaskInProjectCommand(appContext.sessionManager, appContext.taskService, appContext.scanner));
        commands.put(12, new UpdateTaskCommand(appContext.sessionManager, appContext.scanner, appContext.taskService));
        commands.put(13, new FindTaskByNameCommand(appContext.sessionManager, appContext.taskService, appContext.scanner));
        commands.put(14, new FindByIdTaskCommand(appContext.sessionManager, appContext.taskService, appContext.scanner));
        commands.put(15, new RemovingUserFromTaskCommand(appContext.sessionManager, appContext.taskUserService, appContext.userService, appContext.scanner));
        commands.put(16, new ChangeTaskStatusCommand(appContext.sessionManager, appContext.taskService, appContext.scanner));
        commands.put(17, new AddingUserToTaskCommand(appContext.sessionManager, appContext.taskUserService, appContext.userService, appContext.scanner));
        commands.put(18, new DisplayUsersAddedToTaskCommand(appContext.sessionManager, appContext.taskUserService));
        commands.put(19, new DeleteTaskCommand(appContext.sessionManager, appContext.taskService, appContext.scanner));
        commands.put(20, new ChangeUsernameCommand(appContext.sessionManager, appContext.userService, appContext.scanner));
        commands.put(21, new FindUserByNameCommand(appContext.userService, appContext.scanner));
        commands.put(22, new FindByIdUserCommand(appContext.userService, appContext.scanner));
        commands.put(23, new DisplayAllProjectsCreatedByUserCommand(appContext.sessionManager, appContext.projectUserService));
        commands.put(24, new DisplayAllProjectsUserAddedCommand(appContext.sessionManager, appContext.projectUserService));
        commands.put(25, new DisplayTasksCreatedByUserCommand(appContext.sessionManager, appContext.taskService));
        commands.put(26, new DisplayTaskUserAddedCommand(appContext.sessionManager, appContext.taskUserService));
        commands.put(27, new DisplayUserProfileCommand(appContext.sessionManager));
        commands.put(28, new DeletingUserCommand(appContext.sessionManager, appContext.userService, appContext.projectService, appContext.projectUserService, appContext.scanner));
        commands.put(29, new LogOutCommand(appContext.sessionManager));
        commands.put(30, new ExitCommand(appContext.factory));

    }

    @Override
    public Command getCommand(int choice) {
        return commands.getOrDefault(choice, () -> {
            System.out.println("Invalid choice");
            return false;
        });
    }
}
