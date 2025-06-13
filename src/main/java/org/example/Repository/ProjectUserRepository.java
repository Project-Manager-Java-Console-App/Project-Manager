package org.example.Repository;

import org.example.Exceptions.ProjectIdNotFound;
import org.example.Exceptions.UserIdNotFound;
import org.example.model.Users;

import java.sql.SQLException;
import java.util.List;

public interface ProjectUserRepository {
    List<Users> getUsersInProject(Integer projectId) throws ProjectIdNotFound;
    boolean removeUserFromProject(Integer projectId, Integer userId) throws SQLException;
    boolean addUserToProject(Integer projectId, Integer userId) throws SQLException;
    List<Integer> getAllProjectsAssignedToUser(Integer userId) throws UserIdNotFound;

}
