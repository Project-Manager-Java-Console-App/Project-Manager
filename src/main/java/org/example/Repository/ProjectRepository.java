package org.example.Repository;

import org.example.Exceptions.UsernameAlreadyExistsException;
import org.example.model.Project;
import java.sql.SQLException;

public interface ProjectRepository {

    Project save(Project project);
    boolean delete(Project project) throws SQLException;
    Project update(Project project,int id) throws SQLException;
    Project findByName(String name) throws UsernameAlreadyExistsException;
    Project findById(Integer id) throws SQLException;
    }
