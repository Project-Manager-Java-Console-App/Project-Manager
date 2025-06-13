package org.example.model;


import org.example.Auth.PasswordUtils;

import java.sql.SQLException;
import java.util.Base64;

public class Users{

    private int id;
    private final String username;
    private final String passwordHash;
    private final String salt;

    private Users(String username, String passwordHash, String salt){
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
    }

    public static Users registerNew(String username,String passwordHash) throws SQLException {
        if(username == null || passwordHash == null){
            throw new SQLException("Username or password cannot be null!!");
        }

        byte[] saltBytes = PasswordUtils.generateSalt();
        byte[] hashBytes = PasswordUtils.hash(passwordHash.toCharArray(),saltBytes);

        String saltBase64 = Base64.getEncoder().encodeToString(saltBytes);
        String hashBase64 = Base64.getEncoder().encodeToString(hashBytes);

        return new Users(username,hashBase64,saltBase64);
    }

    public static Users createUser(String username){
        return new Users(username,null,null);
    }

    public String getPasswordHash() {return passwordHash;}
    public String getSalt() {return salt;}
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getUsername() {return username;}

    public String toString(){return "\nUsername: " + username.toUpperCase()+"\nId: "+id;}

}
