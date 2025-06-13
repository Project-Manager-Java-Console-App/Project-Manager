package org.example.Repository;


import org.example.Exceptions.TaskIdNotFound;
import org.example.Exceptions.UsernameAlreadyExistsException;
import org.example.model.Status;
import org.example.model.Task;

import java.sql.SQLException;
import java.util.List;

public interface TaskRepository {

    Task save(Task task) throws TaskIdNotFound;
    boolean delete(Task task) throws SQLException;
    Task findByName(String name) throws UsernameAlreadyExistsException;
    List<Task> getAllTasksCreatedByUser(Integer userId) throws SQLException;
    Task findById(Integer id) throws SQLException;
    boolean changeStatus(Integer task_id, Status status) throws SQLException;
    List<Task> findTasksByProjectId(Integer projectId) throws SQLException;
}
