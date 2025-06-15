package org.example.Service;

import org.example.Exceptions.UsernameAlreadyExistsException;
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

    public Task createTask(String name, int project_id, String description, Status status , LocalDate startDate, int createdBy) {
        if(name== null || description == null || status == null|| startDate == null || createdBy == 0||project_id == 0) {
            throw new IllegalArgumentException("Name, description, status, startDate, createdBy, project_id are required");
        }
        return taskRepository.save(Task.create(name, project_id, description, status, startDate, createdBy));
    }

    public boolean delete(Task task) throws SQLException{
        if(task == null){
            throw new IllegalArgumentException("task is null");
        }
        return taskRepository.delete(task);
    }

    public Task findByName(String name) throws UsernameAlreadyExistsException {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("name is null or empty");
        }
        return taskRepository.findByName(name);
    }

    public List<Task> getAllTasksCreatedByUser(Integer userId) throws SQLException{
        if(userId == null){
            throw new IllegalArgumentException("userId is null");
        }
        return taskRepository.getAllTasksCreatedByUser(userId);
    }

    public Task findById(Integer id) throws SQLException{
        if(id == null){
            throw new IllegalArgumentException("id is null");
        }
        return taskRepository.findById(id);
    }

    public boolean changeStatus(Integer id, Status newStatus) throws SQLException{
        if(id == null||
        newStatus == null){
            throw new IllegalArgumentException("id or newStatus is null");
        }
        return taskRepository.changeStatus(id, newStatus);
    }

    public List<Task> findTasksByProjectId(Integer projectId) throws SQLException{
        if(projectId == null){
            throw new IllegalArgumentException("projectId is null");
        }
        return taskRepository.findTasksByProjectId(projectId);
    }

    public void updateTask(String name,String description, Status status,int id) throws SQLException{
        if(name == null||description==null||id == 0){
            throw new IllegalArgumentException("name,description, id is required");
        }
        boolean updated= taskRepository.update(id,name,description,status);
        if (!updated) {
            throw new SQLException("Could not update task");
        }
    }


}
