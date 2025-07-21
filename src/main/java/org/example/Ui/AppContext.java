package org.example.Ui;

import org.example.Repository.*;
import org.example.Service.*;
import org.example.dataBase.DatabaseUtils;
import org.example.model.SessionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class AppContext {

    public final DatabaseUtils factory;
    public final Connection connection;
    public final ProjectRepository projectRepository;
    public final ProjectUserRepository projectUserRepository;
    public final TaskRepository taskRepository;
    public final TaskUserRepository taskUserRepository;
    public final UserRepository userRepository;
    public final SessionManager sessionManager;
    public final ProjectService projectService;
    public final TaskService taskService;
    public final ProjectUserService projectUserService;
    public final TaskUserService taskUserService;
    public final UserService userService;
    public final Scanner scanner;


    public AppContext() throws SQLException {
        this.factory = new DatabaseUtils();
        this.connection = DatabaseUtils.getInstance().getConnection();
        this.scanner = new Scanner(System.in);
        this.sessionManager = SessionManager.getInstance();
        this.projectRepository = new MySqlProjectRepository(connection);
        this.projectUserRepository = new MySqlProjectUserRepository(connection);
        this.taskRepository = new MySqlTaskRepository(connection);
        this.taskUserRepository = new MySqlTaskUserRepository(connection);
        this.userRepository = new MySqlUserRepository(connection);
        this.projectService = new ProjectService(sessionManager, projectRepository, taskRepository);
        this.taskService = new TaskService(taskRepository);
        this.projectUserService = new ProjectUserService(projectUserRepository);
        this.taskUserService = new TaskUserService(taskUserRepository);
        this.userService = new UserService(userRepository);
    }
}
