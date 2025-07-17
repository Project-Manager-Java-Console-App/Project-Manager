package org.example.model;


import org.example.Auth.PasswordUtils;


public class Users extends AbstractModel {

    private final byte[] passwordHash;
    private final byte[] salt;

    private Users(String username, byte[] passwordHash, byte[] salt) {
        this.name = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
    }

    public static Users registerNew(String username, String passwordHash) {
        if (username == null || passwordHash == null) {
            System.err.println("Username and password hash are mandatory");
            return null;
        }
        byte[] saltBytes = PasswordUtils.generateSalt();
        byte[] hashBytes = PasswordUtils.hash(passwordHash.toCharArray(), saltBytes);

        return new Users(username, hashBytes, saltBytes);
    }

    public static Users createUser(String username) {
        return new Users(username, null, null);
    }

    public static Users loginUser(String username, byte[] passwordHash, byte[] salt) {
        return new Users(username, passwordHash, salt);
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return "\nUsername: " + name.toUpperCase() + "\nId: " + id;
    }

}
