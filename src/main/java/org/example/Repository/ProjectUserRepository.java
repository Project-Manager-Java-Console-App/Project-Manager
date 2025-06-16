package org.example.Repository;

import org.example.Exceptions.ProjectIdNotFound;
import org.example.Exceptions.UserIdNotFound;
import org.example.model.Project;

import java.sql.SQLException;
import java.util.List;

public interface ProjectUserRepository {
    List<Integer> getUsersInProject(Integer projectId) throws ProjectIdNotFound;
    boolean removeUserFromProject(Integer projectId, Integer userId) throws SQLException;
    boolean addUserToProject(Integer projectId, Integer userId) throws SQLException;
    List<Project> getAllProjectsCreatedByUser(Integer userId) throws UserIdNotFound;
    List<Integer> getAllProjectsWhereUserIsAdded(Integer userId) throws UserIdNotFound;
}
