package org.example.Repository;

import org.example.Exceptions.UserIdNotFound;
import org.example.Exceptions.UsernameAlreadyExistsException;
import org.example.model.Project;

import java.sql.SQLException;
import java.util.List;

public interface ProjectRepository {

    Project save(Project project);
    boolean delete(Project project) throws SQLException;
    Project update(Project project) throws SQLException;
    Project findByName(String name) throws UsernameAlreadyExistsException;
    List<Project> getAllProjectsCreatedByUser(Integer userId) throws UserIdNotFound;
    Project findById(Integer id) throws SQLException;
    }
