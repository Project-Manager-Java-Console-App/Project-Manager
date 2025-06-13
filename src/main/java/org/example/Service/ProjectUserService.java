package org.example.Service;

import org.example.Exceptions.UserIdNotFound;
import org.example.Repository.ProjectUserRepository;
import org.example.model.Users;

import java.sql.SQLException;
import java.util.List;

public class ProjectUserService {
    private final ProjectUserRepository projectUserRepository;

    public ProjectUserService(ProjectUserRepository projectUserRepository) {
        this.projectUserRepository = projectUserRepository;
    }

    public List<Users> getUsersAssignedToProject(Integer projectId) throws UserIdNotFound {
        if (projectId == null) {
            throw new UserIdNotFound();
        }
        return projectUserRepository.getUsersInProject(projectId);
    }

    public boolean removeUserFromProject(Integer projectId, Integer userId) throws UserIdNotFound, SQLException {
        if (projectId == null||userId == null) {
            throw new SQLException("Invalid ids");
        }
        return projectUserRepository.removeUserFromProject(projectId, userId);
    }

    public boolean addUserToProject(Integer projectId, Integer userId) throws UserIdNotFound, SQLException {
        if (projectId == null||userId == null) {
            throw new SQLException("Invalid ids");
        }
        return projectUserRepository.addUserToProject(projectId, userId);
    }

    public List<Integer> getAllProjectsAssignedToUser(Integer userId) throws UserIdNotFound {
        if (userId == null) {
            throw new UserIdNotFound();
        }
        return projectUserRepository.getAllProjectsAssignedToUser(userId);
    }
}
