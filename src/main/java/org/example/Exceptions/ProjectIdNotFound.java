package org.example.Exceptions;

public class ProjectIdNotFound extends RuntimeException {
    public ProjectIdNotFound() {
        super("Project Id Not Found!!");
    }
}
