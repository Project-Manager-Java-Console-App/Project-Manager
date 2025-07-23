package org.example.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Status;
import org.example.model.Task;
import org.example.repository.core.TaskRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


public class TaskService {

    private final TaskRepository taskRepository;
    private final Logger logger;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.logger = LogManager.getLogger(TaskService.class);
    }

    public Task createTask(String name, int project_id, String description, Status status, LocalDate startDate, int createdBy) {
        if (name == null || description == null || status == null || startDate == null || createdBy == 0 || project_id == 0) {
            System.err.println("Name, description, status, startDate, createdBy, project_id are required");
            logger.error(name, " " + description + " " + status + " " + startDate + " " + createdBy);
            return null;
        }
        logger.info(name, " " + description + " " + status + " " + startDate + " " + createdBy);
        return taskRepository.save(Task.create(name, project_id, description, status, startDate, createdBy));
    }

    public boolean delete(Task task) throws SQLException {
        if (task == null) {
            System.err.println("Task object is null");
            logger.error("Task object is null.");
            return false;
        }
        logger.info("deleting task ", task.getName());
        return taskRepository.delete(task);
    }

    public Task findByName(String name) {
        if (name == null || name.isEmpty()) {
            System.err.println("Task name is null or empty");
            logger.error("Task name is null or empty");
            return null;
        }
        logger.info("The task is found by name");
        return taskRepository.findByName(name);
    }

    public List<Task> getAllTasksCreatedByUser(Integer userId) {
        if (userId == null) {
            System.err.println("userId is null");
            logger.error("UserId is null. Invalid input into the method");
            return null;
        }
        logger.info("Displaying all the tasks created by user");
        return taskRepository.getAllTasksCreatedByUser(userId);
    }

    public Task findById(Integer id) {
        if (id == null) {
            System.err.println("Task id is null");
            logger.error("Task id is null. Invalid input into the method ");
            return null;
        }
        logger.info("The task is found by id");
        return taskRepository.findById(id);
    }

    public boolean changeStatus(Integer id, Status newStatus) {
        if (id == null || newStatus == null) {
            System.err.println("Task id is null or new status is null");
            logger.error("Task id is null or new status is null", id, newStatus);
            return false;
        }
        logger.info("Changing status of task id to ", newStatus);
        return taskRepository.changeStatus(id, newStatus);
    }

    public List<Task> findTasksByProjectId(Integer projectId) {
        if (projectId == null) {
            System.err.println("Project id is null");
            logger.error("Project id is null. Invalid input into the method");
            return null;
        }
        logger.info("Displaying all the tasks assigned to the project");
        return taskRepository.findTasksByProjectId(projectId);
    }

    public void updateTask(String name, String description, int id) {
        if (name == null || description == null || id == 0) {
            System.err.println("Task: name, description, id are null or empty");
            logger.error("Task: name, description, id are null or empty", name, description, id);
            return;
        }
        Task ts = Task.create(name, 0, description, Status.IN_PROGRESS, LocalDate.now(), 0);
        Task updated = taskRepository.update(ts, id);
        if (updated == null) {
            System.err.println("update failed");
            logger.error("Task update failed");
            return;
        }
        logger.info("The task is updated");

    }


}
