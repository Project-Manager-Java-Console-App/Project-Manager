package org.example.model;

import java.time.LocalDate;

public class Task {

    private int id;

    private int project_id;

    private String name;

    private String description;

    private Status status;

    private  LocalDate startDate;

    private int createdBy;

    private  LocalDate endDate;

    public Task() {
        startDate = LocalDate.now();
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public int getProject_id() {return project_id;}

    public void setName(String name) {this.name = name;}

    public String getName() {return name;}

    public void setProject_id(int project_id) {this.project_id = project_id;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public Status getStatus() {return status;}

    public void setStatus(Status status) {this.status = status;}

    public LocalDate getStartDate() {return startDate;}

    public LocalDate getEndDate() {return endDate;}

    public void setEndDate(LocalDate endDate) {this.endDate = endDate;}

    public void setStartDate(LocalDate startDate) {this.startDate = startDate;}

    public int getCreatedBy() {return createdBy;}

    public void setCreatedBy(int createdBy) {this.createdBy = createdBy;}

    public String toString(){
        return "Task: "+ name +"\nDescription: "+description+"\nProject: "+project_id+"\nStatus: "+status;
    }
}
