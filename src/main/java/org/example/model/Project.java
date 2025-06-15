package org.example.model;

import java.time.LocalDate;

public class Project {

    private int id;
    private final String name;
    private final String description;
    private final LocalDate createdDate;
    private final int createdByUserId;

    private Project( String name, String description, int createdByUserId) {
        this.name = name;
        this.description = description;
        this.createdDate = LocalDate.now();
        this.createdByUserId = createdByUserId;

    }

    public static Project create(String name, String description,  int createdByUserId) {
        if(name == null || description == null || createdByUserId == 0)
            throw new IllegalArgumentException("Name, description and createdByUserId are required!");

        return new Project(name, description, createdByUserId);
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public int getCreatedByUserId() {
        return createdByUserId;
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
        if(this.id != 0)
            throw  new IllegalArgumentException("Project id has already been created");

        this.id = id;
    }

    public String toString() {
        return "\nProject: " + name +"\ncreated by: "+createdByUserId +"\ndescription = " + description + "\ncreatedDate = "+ getCreatedDate().toString();
    }
}
