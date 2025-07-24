package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Project;
import org.example.repository.auxiliary.ProjectUserRepository;

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
            logger.error("Invalid Project ID. Project id is null");
            return null;
        }
        logger.info("Getting users assigned to project ", projectId);
        return projectUserRepository.getUsersIn(projectId);
    }

    public boolean removeUserFromProject(Integer projectId, Integer userId) {
        if (projectId == null || userId == null) {
            logger.error("Invalid IDs, One of the IDs is null");
            return false;
        }
        logger.info("Removing User From Project");
        return projectUserRepository.delete(projectId, userId);
    }

    public boolean addUserToProject(Integer projectId, Integer userId) {
        if (projectId == null || userId == null) {
            logger.error("Invalid IDs", projectId, userId);
            return false;
        }
        logger.info("Adding User To Project");
        return projectUserRepository.save(projectId, userId);
    }

    public List<Project> getAllProjectsCreatedByUser(Integer userId) {
        if (userId == null) {
            logger.error("Invalid User ID. User id is null. Create by function!");
            return null;
        }
        logger.info("Displaying all the projects created by user");
        return projectUserRepository.getAllProjectsCreatedByUser(userId);
    }

    public List<Integer> getAllProjectsWhereUserIsAdded(Integer userId) {
        if (userId == null) {
            logger.error("Invalid User ID. User id is null. Added in function!");
            return null;
        }
        logger.info("Displaying all the projects where user is added");
        return projectUserRepository.getAllAddedToUser(userId);
    }
}
