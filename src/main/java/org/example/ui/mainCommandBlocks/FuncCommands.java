package org.example.ui.mainCommandBlocks;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.ui.AppContext;
import org.example.ui.Command;
import org.example.ui.authCommands.ExitCommand;
import org.example.ui.authCommands.LogOutCommand;
import org.example.ui.projectCommands.*;
import org.example.ui.taskCommands.*;
import org.example.ui.userCommands.*;

import java.util.HashMap;
import java.util.Map;

public class FuncCommands implements CommandProvider {

    private final Map<Integer, Command> commands = new HashMap<>();
    private final Logger logger = LogManager.getLogger(FuncCommands.class);

    public FuncCommands(
            AppContext appContext) {
        commands.put(1, new CreateProjectCommand(appContext.projectService, appContext.scanner));
        commands.put(2, new UpdateProjectCommand(appContext.projectService, appContext.scanner));
        commands.put(3, new FindByNameProjectCommand(appContext.projectService, appContext.scanner));
        commands.put(4, new FindByIdProjectCommand(appContext.projectService, appContext.scanner));
        commands.put(5, new AddTaskToProjectCommand(appContext.projectService, appContext.taskService, appContext.scanner));
        commands.put(6, new AddUserToProjectCommand(appContext.projectUserService, appContext.userService, appContext.scanner));
        commands.put(7, new RemovingUserFromProjectCommand(appContext.projectUserService, appContext.userService, appContext.scanner));
        commands.put(8, new DisplayAllTaskByProjectIdCommand(appContext.taskService));
        commands.put(9, new DisplayAllUsersByProjectCommand(appContext.projectUserService));
        commands.put(10, new DeletingProjectCommand(appContext.projectService, appContext.scanner));
        commands.put(11, new CreateTaskInProjectCommand(appContext.taskService, appContext.scanner));
        commands.put(12, new UpdateTaskCommand(appContext.scanner, appContext.taskService));
        commands.put(13, new FindTaskByNameCommand(appContext.taskService, appContext.scanner));
        commands.put(14, new FindByIdTaskCommand(appContext.taskService, appContext.scanner));
        commands.put(15, new RemovingUserFromTaskCommand(appContext.taskUserService, appContext.userService, appContext.scanner));
        commands.put(16, new ChangeTaskStatusCommand(appContext.taskService, appContext.scanner));
        commands.put(17, new AddingUserToTaskCommand(appContext.taskUserService, appContext.userService, appContext.scanner));
        commands.put(18, new DisplayUsersAddedToTaskCommand(appContext.taskUserService));
        commands.put(19, new DeleteTaskCommand(appContext.taskService, appContext.scanner));
        commands.put(20, new ChangeUsernameCommand(appContext.userService, appContext.scanner));
        commands.put(21, new FindUserByNameCommand(appContext.userService, appContext.scanner));
        commands.put(22, new FindByIdUserCommand(appContext.userService, appContext.scanner));
        commands.put(23, new DisplayAllProjectsCreatedByUserCommand(appContext.projectUserService));
        commands.put(24, new DisplayAllProjectsUserAddedCommand(appContext.projectUserService));
        commands.put(25, new DisplayTasksCreatedByUserCommand(appContext.taskService));
        commands.put(26, new DisplayTaskUserAddedCommand(appContext.taskUserService));
        commands.put(27, new DisplayUserProfileCommand());
        commands.put(28, new DeletingUserCommand(appContext.userService, appContext.projectService, appContext.projectUserService, appContext.scanner));
        commands.put(29, new LogOutCommand());
        commands.put(30, new ExitCommand());
    }

    @Override
    public Command getCommand(int choice) {
        return commands.getOrDefault(choice, () -> {
            logger.error("Invalid choice");
            return false;
        });
    }
}
