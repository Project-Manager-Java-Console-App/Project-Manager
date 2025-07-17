package org.example.Service;


import org.example.Repository.TaskRepository;
import org.example.model.Status;
import org.example.model.Task;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(String name, int project_id, String description, Status status, LocalDate startDate, int createdBy) {
        if (name == null || description == null || status == null || startDate == null || createdBy == 0 || project_id == 0) {
            System.err.println("Name, description, status, startDate, createdBy, project_id are required");
            return null;
        }
        return taskRepository.save(Task.create(name, project_id, description, status, startDate, createdBy));
    }

    public boolean delete(Task task) throws SQLException {
        if (task == null) {
            System.err.println("Task object is null");
            return false;
        }
        return taskRepository.delete(task);
    }

    public Task findByName(String name) {
        if (name == null || name.isEmpty()) {
            System.err.println("Task name is null or empty");
            return null;
        }
        return taskRepository.findByName(name);
    }

    public List<Task> getAllTasksCreatedByUser(Integer userId) {
        if (userId == null) {
            System.err.println("userId is null");
            return null;
        }
        return taskRepository.getAllTasksCreatedByUser(userId);
    }

    public Task findById(Integer id) {
        if (id == null) {
            System.err.println("Task id is null");
            return null;
        }
        return taskRepository.findById(id);
    }

    public boolean changeStatus(Integer id, Status newStatus) {
        if (id == null || newStatus == null) {
            System.err.println("Task id is null or new status is null");
            return false;
        }
        return taskRepository.changeStatus(id, newStatus);
    }

    public List<Task> findTasksByProjectId(Integer projectId) {
        if (projectId == null) {
            System.err.println("Project id is null");
            return null;
        }
        return taskRepository.findTasksByProjectId(projectId);
    }

    public void updateTask(String name, String description, int id) {
        if (name == null || description == null || id == 0) {
            System.err.println(" name, description, id are null or empty");
            return;
        }
        Task ts = Task.create(name, 0, description, Status.IN_PROGRESS, LocalDate.now(), 0);
        Task updated = taskRepository.update(ts, id);
        if (updated == null) {
            System.err.println("update failed");
        }
    }


}
