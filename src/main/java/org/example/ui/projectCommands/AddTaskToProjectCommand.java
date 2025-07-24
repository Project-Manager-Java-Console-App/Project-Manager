package org.example.ui.projectCommands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.model.Task;
import org.example.service.ProjectService;
import org.example.service.TaskService;
import org.example.ui.Command;

import java.util.Scanner;

public class AddTaskToProjectCommand implements Command {
    private final ProjectService projectService;
    private final TaskService taskService;
    private final Scanner scanner;
    private final Logger logger = LogManager.getLogger(AddTaskToProjectCommand.class);

    public AddTaskToProjectCommand(ProjectService projectService, TaskService taskService, Scanner scanner) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.scanner = scanner;

    }

    @Override
    public boolean execute() {
        scanner.nextLine();
        Project project = SessionManager.getInstance().getCurrentProject();
        if (project == null) {
            logger.error("No current project selected.");
            return true;
        }
        System.out.println("Adding task to project: " + project.getName());
        System.out.println("Please enter the name of the task you would like to add: ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            logger.error("Please enter the name of the task you would like to add: ");
        }
        Task task = taskService.findByName(name);

        if (task == null) {
            logger.error("Task {}", name + "Not Found");
            return true;
        }
        projectService.addTaskToProject(project.getId(), task.getId());

        return true;
    }
}
