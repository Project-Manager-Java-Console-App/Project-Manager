package org.example.Service;

import org.example.Exceptions.UsernameAlreadyExistsException;
import org.example.Repository.ProjectRepository;
import org.example.Repository.TaskRepository;
import org.example.model.Project;
import org.example.model.SessionManager;
import java.sql.SQLException;


public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    public Project createProject(String name, String description, int createdByUserId){
        if(name == null || description == null || createdByUserId == 0){
            throw new IllegalArgumentException("Name, description, createdByUserId, createdDate are required");
        }
        return projectRepository.save(Project.create(name,description,createdByUserId));
    }

    public Project updateProject(String name, String description, int projectId) throws SQLException {
        if(name == null || description == null || projectId == 0){
            throw new IllegalArgumentException("Name, description, projectId are required");
        }
        int userId= SessionManager.getCurrentUser().getId();
        return projectRepository.update(Project.create(name, description, userId), projectId);
    }

    public Project findProjectByName(String name) throws UsernameAlreadyExistsException {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name is required");
        }
        Project project = projectRepository.findByName(name);
        if(project == null){
            throw new IllegalArgumentException("Project with name " + name + " does not exist");
        }
        return project;
    }

    public Project findById(int id) throws SQLException {
        if(id == 0){
            throw new IllegalArgumentException("Id is required");
        }
        Project project = projectRepository.findById(id);
        if(project == null){
            throw new NullPointerException();
        }
        return project;
    }

    public boolean deleteProject(Project project) throws SQLException{
        if(project == null){
            throw new IllegalArgumentException("Project is required");
        }
        return projectRepository.delete(project);
    }


    public void addTaskToProject(int projectId, Integer taskId) throws SQLException{
        if(projectId == 0||taskId == 0){
            throw new IllegalArgumentException("ProjectId and taskId is required");
        }
        boolean added = taskRepository.assignTaskToProject(projectId, taskId);
        if(!added){
            throw new SQLException("Unable to assign task to project");
        }
    }
}
