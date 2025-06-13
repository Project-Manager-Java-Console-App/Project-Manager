package org.example.Service;

import org.example.Repository.TaskUserRepository;
import org.example.model.Users;

import java.sql.SQLException;
import java.util.List;

public class TaskUserService {

    private final TaskUserRepository taskUserRepository;

    public TaskUserService(TaskUserRepository taskUserRepository) {
        this.taskUserRepository = taskUserRepository;
    }

    public List<Users> getUsersInTask(int taskId) throws SQLException {
        if (taskId < 1) {
            throw new SQLException("Invalid task id");
        }
        return taskUserRepository.getUsersInTask(taskId);
    }

    public boolean removeUserFromTask(int taskId, int userId) throws SQLException {
        if (taskId < 1||userId < 1) {
            throw new SQLException("Invalid ids");
        }
        return taskUserRepository.removeUserFromTask(taskId, userId);
    }

    public boolean addUserToTask(int taskId, int userId) throws SQLException {
        if (taskId < 1||userId < 1) {
            throw new SQLException("Invalid ids");
        }
        return taskUserRepository.addUserToTask(taskId, userId);
    }

    public List<Integer> getAllTaskAssignedToUser(int userId) throws SQLException {
        if (userId < 1) {
            throw new SQLException("Invalid ids");
        }
        return taskUserRepository.getAllTasksAssignedToUser(userId);
    }
}
