package org.example.model;


import org.example.Auth.PasswordUtils;


public class Users extends AbstractModel {

    private final String passwordHash;


    private Users(String username, String passwordHash) {
        this.name = username;
        this.passwordHash = passwordHash;

    }

    public static Users registerNew(String username, String passwordHash) {
        if (username == null || passwordHash == null) {
            System.err.println("Username and password hash are mandatory");
            return null;
        }

        String hashBytes = PasswordUtils.hash(passwordHash);

        return new Users(username, hashBytes);
    }

    public static Users createUser(String username) {
        return new Users(username, null);
    }

    public static Users loginUser(String username, String passwordHash) {
        return new Users(username, passwordHash);
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return "\nUsername: " + name.toUpperCase() + "\nId: " + id;
    }

}
