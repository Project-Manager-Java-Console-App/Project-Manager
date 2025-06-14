package org.example.Service;

import org.example.Exceptions.UserIdNotFound;
import org.example.Exceptions.UsernameAlreadyExistsException;
import org.example.Repository.MySqlProjectRepository;
import org.example.Repository.ProjectRepository;
import org.example.model.Project;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
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
        return projectRepository.update(Project.create(name, description, projectId));
    }

    public Project findProjectByName(String name) throws UsernameAlreadyExistsException {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name is required");
        }
        return projectRepository.findByName(name);
    }

    public Project findById(int id) throws SQLException {
        if(id == 0){
            throw new SQLException("Id is required");
        }
        return projectRepository.findById(id);
    }

    public boolean deleteProject(Project project) throws SQLException{
        if(project == null){
            throw new IllegalArgumentException("Project is required");
        }
        return projectRepository.delete(project);
    }

    public List<Project> getAllProjectsCreatedByUserId(int userId) throws UserIdNotFound {
        if(userId == 0){
            throw new IllegalArgumentException("UserId is required");
        }
        return projectRepository.getAllProjectsCreatedByUser(userId);
    }
}
