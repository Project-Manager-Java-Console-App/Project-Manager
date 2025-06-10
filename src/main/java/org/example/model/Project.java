package org.example.model;

import java.time.LocalDate;

public class Project {

    private int id;
    private String name;
    private String description;
    private LocalDate createdDate;
    private int createdByUserId;

    public Project(){
        createdDate = LocalDate.now();
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public int getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(int createdBy) {
        this.createdByUserId = createdBy;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return "\nProject: " + name +"\ncreated by: "+createdByUserId +"\ndescription = " + description + "\ncreatedDate = "+ getCreatedDate().toString();
    }
}
