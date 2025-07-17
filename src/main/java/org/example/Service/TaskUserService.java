package org.example.Service;

import org.example.Repository.TaskUserRepository;

import java.sql.SQLException;
import java.util.List;

public class TaskUserService {

    private final TaskUserRepository taskUserRepository;

    public TaskUserService(TaskUserRepository taskUserRepository) {
        this.taskUserRepository = taskUserRepository;
    }

    public List<Integer> getUsersInTask(int taskId) {
        if (taskId < 1) {
            System.err.println("Invalid task id");
            return null;
        }
        return taskUserRepository.getUsersIn(taskId);
    }

    public boolean removeUserFromTask(int taskId, int userId) {
        if (taskId < 1 || userId < 1) {
            System.err.println("Invalid ids");
            return false;
        }
        return taskUserRepository.delete(taskId, userId);
    }

    public boolean addUserToTask(int taskId, int userId) throws SQLException {
        if (taskId < 1 || userId < 1) {
            System.err.println("Invalid ids");
            return false;
        }
        return taskUserRepository.save(taskId, userId);
    }

    public List<Integer> getAllTaskAssignedToUser(int userId) {
        if (userId < 1) {
            System.err.println("Invalid user id");
            return null;
        }
        return taskUserRepository.getAllAddedToUser(userId);
    }
}
