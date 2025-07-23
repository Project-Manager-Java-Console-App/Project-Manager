package org.example.repository.auxiliary;

import org.example.model.Project;

import java.util.List;

public interface ProjectUserRepository extends AuxiliaryCrudRepository<Integer> {
    List<Project> getAllProjectsCreatedByUser(Integer userId);
}
