package org.example.ui;

import org.example.repository.auxiliary.MySqlProjectUserRepository;
import org.example.repository.auxiliary.MySqlTaskUserRepository;
import org.example.repository.auxiliary.ProjectUserRepository;
import org.example.repository.auxiliary.TaskUserRepository;
import org.example.repository.core.*;
import org.example.service.*;

import java.util.Scanner;

public class AppContext {
    public final ProjectRepository projectRepository;
    public final ProjectUserRepository projectUserRepository;
    public final TaskRepository taskRepository;
    public final TaskUserRepository taskUserRepository;
    public final UserRepository userRepository;
    public final ProjectService projectService;
    public final TaskService taskService;
    public final ProjectUserService projectUserService;
    public final TaskUserService taskUserService;
    public final UserService userService;
    public final Scanner scanner;

    public AppContext() {
        this.scanner = new Scanner(System.in);
        this.projectRepository = new MySqlProjectRepository();
        this.projectUserRepository = new MySqlProjectUserRepository();
        this.taskRepository = new MySqlTaskRepository();
        this.taskUserRepository = new MySqlTaskUserRepository();
        this.userRepository = new MySqlUserRepository();
        this.projectService = new ProjectService(projectRepository, taskRepository);
        this.taskService = new TaskService(taskRepository);
        this.projectUserService = new ProjectUserService(projectUserRepository);
        this.taskUserService = new TaskUserService(taskUserRepository);
        this.userService = new UserService(userRepository);
    }
}
