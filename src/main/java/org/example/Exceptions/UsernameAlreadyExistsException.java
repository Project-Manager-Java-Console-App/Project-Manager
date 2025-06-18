package org.example.Exceptions;

public class UsernameAlreadyExistsException extends  Exception{
    public UsernameAlreadyExistsException() {
        super("Username  already exists");
    }
}
