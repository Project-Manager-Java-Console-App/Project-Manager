package org.example;

import org.example.Repository.*;
import org.example.Service.*;
import org.example.Ui.Command;
import org.example.Ui.DisplayChoiceMenu;
import org.example.Ui.MainCommandBlocks.AfterLoginCommands;
import org.example.Ui.MainCommandBlocks.AuthCommandFactory;
import org.example.Ui.MainCommandBlocks.FuncCommands;
import org.example.dataBase.DatabaseUtils;
import org.example.model.SessionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        try {
            DatabaseUtils database = DatabaseUtils.getInstance();
            Connection connection = database.getConnection();
            ProjectRepository projectRepository = new MySqlProjectRepository(connection);
            ProjectUserRepository projectUserRepository = new MySqlProjectUserRepository(connection);
            TaskRepository taskRepository = new MySqlTaskRepository(connection);
            TaskUserRepository taskUserRepository = new MySqlTaskUserRepository(connection);
            UserRepository userRepository = new MySqlUserRepository(connection);

            ProjectService projectService = new ProjectService(projectRepository, taskRepository);
            TaskService taskService = new TaskService(taskRepository);
            ProjectUserService projectUserService = new ProjectUserService(projectUserRepository);
            TaskUserService taskUserService = new TaskUserService(taskUserRepository);
            UserService userService = new UserService(userRepository);
            Scanner scanner = new Scanner(System.in);

            int choice;

            while (true) {
                if (!SessionManager.isLoggedIn()) {
                    DisplayChoiceMenu.DisplayAuthMenu();
                    System.out.println("Please enter your choice: ");
                    choice = scanner.nextInt();
                    Command command = AuthCommandFactory.getCommand(choice, userService, scanner);
                    command.execute();
                } else if (SessionManager.getCurrentProject() == null) {
                    DisplayChoiceMenu.DisplayAfterLoginMenu();
                    System.out.println("Please enter your choice: ");
                    choice = scanner.nextInt();
                    Command command = AfterLoginCommands.getCommand(choice, projectService, scanner);
                    command.execute();
                } else {
                    DisplayChoiceMenu.DisplayFuncMenu();
                    System.out.println("Please enter your choice: ");
                    choice = scanner.nextInt();
                    Command command = new FuncCommands(
                            projectService,
                            taskService,
                            userService,
                            projectUserService,
                            taskUserService,
                            scanner
                    ).getCommand(choice);
                    command.execute();
                }
            }
        }catch (Exception e) {
            System.err.println("Failed to connect to the database");
        }finally {
            DatabaseUtils.closeConnection();
        }
    }
}