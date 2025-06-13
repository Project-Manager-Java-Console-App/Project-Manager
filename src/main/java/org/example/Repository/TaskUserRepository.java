package org.example.Repository;

import org.example.model.Users;

import java.sql.SQLException;
import java.util.List;

public interface TaskUserRepository {

    List<Users> getUsersInTask(Integer taskId) throws SQLException;
    boolean removeUserFromTask(Integer taskId, Integer userId) throws SQLException;
    boolean addUserToTask(Integer taskId, Integer userId) throws SQLException;
    List<Integer> getAllTasksAssignedToUser(Integer userId) throws SQLException;

}
