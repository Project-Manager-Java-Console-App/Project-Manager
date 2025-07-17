package org.example.Repository;

import org.example.model.Project;

import java.util.List;

public interface ProjectUserRepository extends AdditionalTablesRepository<Integer> {
    List<Project> getAllProjectsCreatedByUser(Integer userId);
}
