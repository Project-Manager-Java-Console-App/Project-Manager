package org.example.model;

public abstract class AbstractModel {
    protected int id;
    protected String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (this.id != 0)
            System.err.println("Id has already been created");
        this.id = id;
    }

    public String getName() {
        return name;
    }
}
