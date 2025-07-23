package org.example.model;

import java.time.LocalDate;

public class Task extends AbstractModel {

    private final int projectId;
    private final String description;
    private final Status status;
    private final LocalDate startDate;
    private final int createdBy;

    private Task(String name, int projectId, String description, Status status, LocalDate startDate, int createdBy) {
        this.name = name;
        this.projectId = projectId;
        this.description = description;
        this.status = status;
        this.startDate = startDate;
        this.createdBy = createdBy;
    }

    public static Task create(String name, int projectId, String description, Status status, LocalDate startDate, int createdBy) {
        return new Task(name, projectId, description, status, startDate, createdBy);
    }

    public int getProjectId() {
        return projectId;
    }


    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public String toString() {
        return "Task: " + name + "\nDescription: " + description + "\nProject: " + projectId + "\nStatus: " + status;
    }
}
