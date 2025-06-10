package org.example.model;

public class SessionManager {

    private static Users currentUser;
    private static Project currentProject;
    private static Task currentTask;

    public static void login(Users user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static Users getCurrentUser() {
        return currentUser;
    }

    public static Project getCurrentProject() {
        return currentProject;
    }

    public static void setCurrentProject(Project project) {
        currentProject = project;
    }

    public static Task getCurrentTask() {
        return currentTask;
    }
    public static void setCurrentTask(Task task) {
        currentTask = task;
    }
}
