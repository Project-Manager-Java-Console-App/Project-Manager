package org.example.model;

import java.time.LocalDate;

public class Task {

    private int id;
    private final int project_id;
    private final String name;
    private final String description;
    private final Status status;
    private final LocalDate startDate;
    private final int createdBy;
    private LocalDate endDate;

    private Task(String name, int project_id, String description, Status status,LocalDate startDate, int createdBy) {
        this.name = name;
        this.project_id = project_id;
        this.description = description;
        this.status = status;
        this.startDate = LocalDate.now();
        this.createdBy = createdBy;
    }

    public static Task create(String name, int projectId, String description, Status status,LocalDate startDate, int createdBy) {
        if(name == null || description == null || status == null|| startDate == null || createdBy == 0||projectId == 0){
            throw new IllegalArgumentException("Name, description, status, startDate, createdBy, project_id are required");
        }
        return new Task(name, projectId, description, status,startDate, createdBy);
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public int getProject_id() {return project_id;}

    public String getName() {return name;}

    public String getDescription() {return description;}

    public Status getStatus() {return status;}

    public LocalDate getStartDate() {return startDate;}

    public LocalDate getEndDate() {return endDate;}

    public void setEndDate(LocalDate endDate) {this.endDate = endDate;}

    public int getCreatedBy() {return createdBy;}

    public String toString(){
        return "Task: "+ name +"\nDescription: "+description+"\nProject: "+project_id+"\nStatus: "+status;
    }
}
