package org.example.Exceptions;

public class UsernameAlreadyExistsException extends  Exception{
    public UsernameAlreadyExistsException(String message) {
        super("Username "+message+" already exists");
    }
}
