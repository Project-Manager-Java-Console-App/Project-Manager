package org.example.model;


public class Users{

    private int id;
    private String username;

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String toString(){return "\nUsername: " + username.toUpperCase()+"\nId: "+id;}

}
