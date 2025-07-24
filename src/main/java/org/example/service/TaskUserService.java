package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.repository.auxiliary.TaskUserRepository;

import java.util.List;

public class TaskUserService {

    private final TaskUserRepository taskUserRepository;
    private final Logger logger;

    public TaskUserService(TaskUserRepository taskUserRepository) {
        this.taskUserRepository = taskUserRepository;
        this.logger = LogManager.getLogger(TaskUserService.class);
    }

    public List<Integer> getUsersInTask(int taskId) {
        if (taskId < 1) {
            logger.error("Invalid task id! The id is smaller than 1");
            return null;
        }
        logger.info("Getting users in task ", taskId);
        return taskUserRepository.getUsersIn(taskId);
    }

    public boolean removeUserFromTask(int taskId, int userId) {
        if (taskId < 1 || userId < 1) {
            logger.error("Invalid ids! The one of ids is smaller than 1 ", taskId, userId);
            return false;
        }
        logger.info("Removing user from task ", taskId);
        return taskUserRepository.delete(taskId, userId);
    }

    public boolean addUserToTask(int taskId, int userId) {
        if (taskId < 1 || userId < 1) {
            logger.error("Invalid ids! The id is smaller than 1 ", taskId, userId);
            return false;
        }
        logger.info("Adding user to task ", taskId);
        return taskUserRepository.save(taskId, userId);
    }

    public List<Integer> getAllTaskAssignedToUser(int userId) {
        if (userId < 1) {
            logger.error("Invalid user id! The id is smaller than 1", userId);
            return null;
        }
        logger.info("Getting task assigned to user ", userId);
        return taskUserRepository.getAllAddedToUser(userId);
    }
}
