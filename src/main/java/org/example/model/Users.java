package org.example.model;


import org.example.Auth.PasswordUtils;

import java.sql.SQLException;


public class Users{

    private int id;
    private final String username;
    private final byte[] passwordHash;
    private final byte[] salt;

    private Users(String username, byte[] passwordHash, byte[] salt){
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

        return new Users(username,hashBytes,saltBytes);
    }

    public static Users createUser(String username){
        return new Users(username,null,null);
    }

    public static Users loginUser(String username, byte[] passwordHash,byte[] salt) {
        return new Users(username,passwordHash,salt);
    }

    public byte[] getPasswordHash() {return passwordHash;}
    public byte[] getSalt() {return salt;}
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getUsername() {return username;}

    public String toString(){return "\nUsername: " + username.toUpperCase()+"\nId: "+id;}

}
