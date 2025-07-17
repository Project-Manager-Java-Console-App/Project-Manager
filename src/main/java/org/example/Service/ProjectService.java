package org.example.Service;

import org.example.Repository.ProjectRepository;
import org.example.Repository.TaskRepository;
import org.example.model.Project;
import org.example.model.SessionManager;


public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final SessionManager sessionManager;

    public ProjectService(SessionManager sessionManager, ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.sessionManager = sessionManager;
    }

    public Project createProject(String name, String description, int createdByUserId) {
        if (name == null || description == null || createdByUserId == 0) {
            System.err.println("Name, description, createdByUserId is null!");
            return null;
        }
        return projectRepository.save(Project.create(name, description, createdByUserId));
    }

    public Project updateProject(String name, String description, int projectId) {
        if (name == null || description == null || projectId == 0) {
            System.err.println("Name, description, projectId are required");
            return null;
        }
        int userId = sessionManager.getCurrentUser().getId();
        return projectRepository.update(Project.create(name, description, userId), projectId);
    }

    public Project findProjectByName(String name) {
        if (name == null || name.isEmpty()) {
            System.err.println("Name is required");
            return null;
        }
        Project project = projectRepository.findByName(name);
        if (project == null) {
            System.err.println("Project with name " + name + " does not exist");
            return null;
        }
        return project;
    }

    public Project findById(int id) {
        if (id == 0) {
            System.err.println("Id is required");
            return null;
        }
        Project project = projectRepository.findById(id);
        if (project == null) {
            System.err.println("Project with id " + id + " does not exist");
            return null;
        }
        return project;
    }

    public boolean deleteProject(Project project) {
        if (project == null) {
            System.err.println("Project is required");
            return false;
        }
        return projectRepository.delete(project);
    }


    public void addTaskToProject(int projectId, Integer taskId) {
        if (projectId == 0 || taskId == 0) {
            System.err.println("ProjectId and taskId is required");
            return;
        }
        boolean added = taskRepository.assignTaskToProject(projectId, taskId);
        if (!added) {
            System.err.println("Unable to assign task to project");
        }
    }
}
