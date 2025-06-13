package org.example.Exceptions;

public class UserIdNotFound extends RuntimeException {
    public UserIdNotFound() {
        super("UserId Not Found");
    }
}
