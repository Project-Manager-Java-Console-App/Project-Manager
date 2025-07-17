package org.example.model;

import java.time.LocalDate;

public class Project extends AbstractModel {

    private final String description;
    private final LocalDate createdDate;
    private final int createdByUserId;

    private Project(String name, String description, int createdByUserId) {
        this.name = name;
        this.description = description;
        this.createdDate = LocalDate.now();
        this.createdByUserId = createdByUserId;
    }

    public static Project create(String name, String description, int createdByUserId) {
        if (name == null || description == null || createdByUserId == 0)
            throw new IllegalArgumentException("Name, description and createdByUserId are required!");

        return new Project(name, description, createdByUserId);
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public int getCreatedByUserId() {
        return createdByUserId;
    }


    public String getDescription() {
        return description;
    }


    public String toString() {
        return "\nProject: " + name + "\ncreated by: " + createdByUserId + "\ndescription = " + description + "\ncreatedDate = " + getCreatedDate().toString();
    }


}
