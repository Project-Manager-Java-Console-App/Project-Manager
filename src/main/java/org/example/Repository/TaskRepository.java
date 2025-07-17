package org.example.Repository;


import org.example.model.Status;
import org.example.model.Task;

import java.util.List;

public interface TaskRepository extends MainTablesRepository<Task> {

    List<Task> getAllTasksCreatedByUser(Integer userId);

    boolean changeStatus(Integer task_id, Status status);

    List<Task> findTasksByProjectId(Integer projectId);

    boolean assignTaskToProject(Integer projectId, Integer taskId);

}
