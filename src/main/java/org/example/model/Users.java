package org.example.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Users extends AbstractModel {

    private final String passwordHash;
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    private Users(String username, String passwordHash) {
        this.name = username;
        this.passwordHash = passwordHash;

    }

    public static Users registerNew(String username, String passwordHash) {
        String hashBytes = passwordEncoder.encode(passwordHash);
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

    public String toString() {
        return "\nUsername: " + name.toUpperCase() + "\nId: " + id;
    }

}
