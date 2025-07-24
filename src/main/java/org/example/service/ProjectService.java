package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Project;
import org.example.model.SessionManager;
import org.example.repository.core.ProjectRepository;
import org.example.repository.core.TaskRepository;


public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    private final Logger logger;

    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.logger = LogManager.getLogger(ProjectService.class);
    }

    public Project createProject(String name, String description, int createdByUserId) {
        if (name == null || description == null || createdByUserId == 0) {
            logger.error("Project name and description are null!");
            return null;
        }
        logger.info("Creating project");
        return projectRepository.save(Project.create(name, description, createdByUserId));
    }

    public Project updateProject(String name, String description, int projectId) {
        if (name == null || description == null || projectId == 0) {
            logger.error("Project name and description are required!");
            return null;
        }
        logger.info("Updating project");
        int userId = SessionManager.getInstance().getCurrentUser().getId();
        return projectRepository.update(Project.create(name, description, userId), projectId);
    }

    public Project findProjectByName(String name) {
        if (name == null || name.isEmpty()) {
            logger.error("Name is required. The project name is null or empty!");
            return null;
        }
        Project project = projectRepository.findByName(name);
        if (project == null) {
            logger.error("Failed to find Project by name");
            return null;
        }
        logger.info("Finding project by name");
        return project;
    }

    public Project findById(int id) {
        if (id == 0) {
            logger.error("Id is required. The project id is null or empty!");
            return null;
        }
        Project project = projectRepository.findById(id);
        if (project == null) {
            logger.error("Failed to find Project by id");
            return null;
        }
        logger.info("Finding project by id");
        return project;
    }

    public boolean deleteProject(Project project) {
        if (project == null) {
            logger.error("Project is required. The project is null or empty!");
            return false;
        }
        logger.info("Deleting project");
        return projectRepository.delete(project);
    }

    public void addTaskToProject(int projectId, Integer taskId) {
        if (projectId == 0 || taskId == 0) {
            logger.error("ProjectId and taskId is required. The project id is null or empty!");
            return;
        }
        boolean added = taskRepository.assignTaskToProject(projectId, taskId);
        if (!added) {
            logger.error("Unable to assign task to project");
            return;
        }
        logger.info("Adding task to project");
    }
}
