package org.example.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Repository.ProjectUserRepository;
import org.example.model.Project;

import java.util.List;

public class ProjectUserService {
    private final ProjectUserRepository projectUserRepository;
    private final Logger logger;

    public ProjectUserService(ProjectUserRepository projectUserRepository) {
        this.projectUserRepository = projectUserRepository;
        this.logger = LogManager.getLogger(ProjectUserService.class);
    }

    public List<Integer> getUsersAssignedToProject(Integer projectId) {
        if (projectId == null) {
            System.err.println("Invalid Project ID");
            logger.error("Invalid Project ID. Project id is null");
            return null;
        }
        logger.info("Getting users assigned to project ", projectId);
        return projectUserRepository.getUsersIn(projectId);
    }

    public boolean removeUserFromProject(Integer projectId, Integer userId) {
        if (projectId == null || userId == null) {
            System.err.println("Invalid IDs");
            logger.error("Invalid IDs, One of the IDs is null", projectId, userId);
            return false;
        }
        logger.info("Removing User From Project");
        return projectUserRepository.delete(projectId, userId);
    }

    public boolean addUserToProject(Integer projectId, Integer userId) {
        if (projectId == null || userId == null) {
            System.err.println("Invalid IDs");
            logger.error("Invalid IDs", projectId, userId);
            return false;
        }
        logger.info("Adding User To Project");
        return projectUserRepository.save(projectId, userId);
    }

    public List<Project> getAllProjectsCreatedByUser(Integer userId) {
        if (userId == null) {
            System.err.println("Invalid User ID");
            logger.error("Invalid User ID. User id is null. Create by function!");
            return null;
        }
        logger.info("Displaying all the projects created by user");
        return projectUserRepository.getAllProjectsCreatedByUser(userId);
    }

    public List<Integer> getAllProjectsWhereUserIsAdded(Integer userId) {
        if (userId == null) {
            System.err.println("Invalid User ID");
            logger.error("Invalid User ID. User id is null. Added in function!");
            return null;
        }
        logger.info("Displaying all the projects where user is added");
        return projectUserRepository.getAllAddedToUser(userId);
    }
}
