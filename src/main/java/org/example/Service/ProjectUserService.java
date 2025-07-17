package org.example.Service;

import org.example.Repository.ProjectUserRepository;
import org.example.model.Project;

import java.util.List;

public class ProjectUserService {
    private final ProjectUserRepository projectUserRepository;

    public ProjectUserService(ProjectUserRepository projectUserRepository) {
        this.projectUserRepository = projectUserRepository;
    }

    public List<Integer> getUsersAssignedToProject(Integer projectId) {
        if (projectId == null) {
            System.err.println("Invalid Project ID");
        }
        return projectUserRepository.getUsersIn(projectId);
    }

    public boolean removeUserFromProject(Integer projectId, Integer userId) {
        if (projectId == null || userId == null) {
            System.err.println("Invalid IDs");
        }
        return projectUserRepository.delete(projectId, userId);
    }

    public boolean addUserToProject(Integer projectId, Integer userId) {
        if (projectId == null || userId == null) {
            System.err.println("Invalid IDs");
        }
        return projectUserRepository.save(projectId, userId);
    }

    public List<Project> getAllProjectsCreatedByUser(Integer userId) {
        if (userId == null) {
            System.err.println("Invalid User ID");
        }
        return projectUserRepository.getAllProjectsCreatedByUser(userId);
    }

    public List<Integer> getAllProjectsWhereUserIsAdded(Integer userId) {
        if (userId == null) {
            System.err.println("Invalid User ID");
        }
        return projectUserRepository.getAllAddedToUser(userId);
    }
}
