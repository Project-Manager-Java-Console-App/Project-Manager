package org.example.Exceptions;

public class TaskIdNotFound extends RuntimeException {
    public TaskIdNotFound() {
        super("Task id not fount");
    }
}
