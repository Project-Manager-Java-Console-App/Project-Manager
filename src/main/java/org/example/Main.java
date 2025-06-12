package org.example;

import org.example.Exceptions.UsernameAlreadyExistsException;
import org.example.ProjectDAO.ProjectDAO;
import org.example.ProjectDAO.TaskDAO;
import org.example.ProjectDAO.UsersDAO;
import org.example.model.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) throws SQLException {
        UsersDAO UDao = new UsersDAO();
        ProjectDAO PDao = new ProjectDAO();
        TaskDAO TDao = new TaskDAO();
        Scanner sc = new Scanner(System.in);


        boolean running = true;

        while(running) {
            Users user = SessionManager.getCurrentUser();
            Project projects =SessionManager.getCurrentProject();
            Task task =SessionManager.getCurrentTask();

            if(!SessionManager.isLoggedIn()) {
                System.out.println("Enter: 1 for registration");
                System.out.println("Enter: 2 for login");
                System.out.println("Enter: 3 for exit");
                int choice = sc.nextInt();
                switch(choice) {
                    case 1:{

                        sc.nextLine();
                        System.out.println("Please enter your username to register:");
                        String username = sc.nextLine();

                        System.out.println("\nPlease enter your password: ");
                        char[] password = sc.nextLine().toCharArray();

                        try {
                            int userId = UDao.register(username,password);
                            System.out.println("registration completed "+ userId);
                            break;
                        }catch (UsernameAlreadyExistsException e) {
                            System.err.println(e.getMessage());
                        }catch (SQLException ex) {
                            System.err.println("Database error" + ex.getMessage());
                            break;
                        }
                    }
                    case 2:{
                        sc.nextLine();
                        System.out.println("Please enter your username to login: ");
                        String username = sc.nextLine();
                        System.out.println("[Got the username] "+username);
                        System.out.println("Please enter your password: ");
                        char[] password = sc.nextLine().toCharArray();
                        Users users = UDao.authenticate(username,password);
                        if(users != null) {
                            SessionManager.login(users);
                            System.out.println("Login completed");
                            System.out.println("Welcome back, "+username.toUpperCase()+"!");
                        }else {
                            System.out.println("Login failed");
                        }
                        break;
                    }
                    case 3:{
                        running = false;
                        break;
                    }
                    default:{
                        System.out.println("Please enter a valid choice");
                    }
                }
            }else {
                if(SessionManager.getCurrentProject()==null){
                    sc.nextLine();
                    System.out.println("""
                            Enter 1 for creating a project
                            Enter 2 for finding a project by name
                            Enter 3 for displaying user profile""");

                    int choice = sc.nextInt();
                    switch (choice){
                        case 1:{
                            try{
                                Project p = createProject(user.getId());
                                SessionManager.setCurrentProject(p);
                                System.out.println("Project created");
                                break;
                            }catch (SQLException e) {
                                System.err.println("Project already exists");
                            }
                            break;
                        }
                        case 2:{
                            System.out.println("Please enter your searched project name.");
                            findingProject(PDao, sc);
                            break;
                        }
                        case 3:{
                            PrintUserProfile(user);
                            break;
                        }
                        default:{
                            System.out.println("Please enter a valid choice");
                        }
                    }
                }else {

                    System.out.println("\nWARNING:\nFirst search for project/task by name and then do the operation!!\n Before updating, deleting, adding etc project/task!!");
                    //Project func
                    System.out.println("Enter 1 for creating a project");
                    System.out.println("Enter 2 for updating a project");
                    System.out.println("Enter 3 for finding a project by name");
                    System.out.println("Enter 4 for assigning a task to a project");
                    System.out.println("Enter 5 for assigning a user to a project");
                    System.out.println("Enter 6 for removing a user from a project");
                    System.out.println("Enter 7 for displaying all tasks associated with a project");
                    System.out.println("Enter 8 for displaying all users associated with a project");
                    System.out.println("Enter 9 for deleting a project");
                    //Task func
                    System.out.println("Enter 10 for creating a new task in current project");
                    System.out.println("Enter 11 for updating a task");
                    System.out.println("Enter 12 for finding a task");
                    System.out.println("Enter 13 for removing a user from a task");
                    System.out.println("Enter 14 for change task Status");
                    System.out.println("Enter 15 for assigning a user to a task");
                    System.out.println("Enter 16 for displaying all users id associated with a task");
                    System.out.println("Enter 17 for deleting a task");

                    //User func
                    System.out.println("Enter 18 for updating a username");
                    System.out.println("Enter 19 for finding a user by name");
                    System.out.println("Enter 20 for displaying all projects created by user");
                    System.out.println("Enter 21 for displaying all projects in which the user is assigned");
                    System.out.println("Enter 22 for displaying all tasks created by user");
                    System.out.println("Enter 23 for displaying all tasks in which the user is assigned");
                    System.out.println("Enter 24 for displaying a user profile");
                    System.out.println("Enter 25 for deleting a your user profile");
                    System.out.println("Enter 26 for Log out");
                    System.out.println("Enter 27 for exit");

                    System.out.println("Enter Your choice: ");


                    int choice = sc.nextInt();

                    switch (choice) {
                        case 1:{
                                Project p = createProject(user.getId());
                                SessionManager.setCurrentProject(p);
                                System.out.println("Project created");
                            break;
                        }
                        case 2:{
                            sc.nextLine();
                            System.out.println("Please enter your new project name: ");
                            String newName = sc.nextLine();
                            System.out.println("please enter your new project description: ");
                            String newDescription = sc.nextLine();

                            Project newProject = new Project();
                            newProject.setName(newName);
                            newProject.setDescription(newDescription);


                            if(PDao.update(newProject,projects.getId())){
                                System.out.println("Project updated");
                            }else {
                                System.out.println("Project not updated");
                            }
                            break;
                        }
                        case 3:{
                            System.out.println("\nPlease enter your searched project name.");
                            findingProject(PDao, sc);
                            break;
                        }
                        case 4:{
                            sc.nextLine();
                            System.out.println("Adding existing task to a "+projects.getName()+" project!");
                            System.out.println("Please enter task name that you want to add: ");
                            String taskName = sc.nextLine();
                            Task newTask = TDao.findByName(taskName);
                            if(newTask == null){
                                System.out.println("Task not found");
                            }else {
                                boolean addTask = PDao.addTaskToProject(projects.getId(),newTask.getId());
                                if(addTask){
                                    System.out.println("Task added");
                                }else {
                                    System.out.println("Task not added");
                                }
                            }
                            break;
                        }
                        case 5:{
                            sc.nextLine();
                            System.out.println("Adding a user to a "+projects.getName()+" project");
                            System.out.println("Please enter username of user you wish to add: ");
                            String username = sc.nextLine();
                            Users newUser = UDao.findByName(username);
                            if(newUser == null){
                                System.out.println("User not found");
                            }else {
                                boolean add = PDao.addUserTo(projects.getId(), newUser.getId());
                                if (add){
                                    System.out.println("User added");
                                }else {
                                    System.out.println("User not added");
                                }
                            }
                            break;
                        }
                        case 6:{
                            sc.nextLine();
                            System.out.println("Removing a user from a "+projects.getName()+" project!");
                            System.out.println("Please enter username: ");
                            String newUsername = sc.nextLine();
                            Users u = UDao.findByName(newUsername);
                            boolean remove = PDao.removeUserFrom(projects.getId(),u.getId());
                            if(remove){
                                System.out.println("User removed");
                            }else {
                                System.out.println("User failed to be removed.");
                            }
                            break;
                        }
                        case 7:{
                            System.out.println("Displaying all tasks associated with a project");
                            List<Task> ts = PDao.findTasksByProjectId(projects.getId());
                            if(ts == null){
                                System.out.println("No tasks associated with a project");
                            }
                            else {
                                for(Task t : ts){
                                    System.out.println(t);
                                }
                            }
                            break;
                        }
                        case 8:{
                            System.out.println("Displaying added users id associated with a "+projects.getName()+" project!");
                            List<Integer> user_ids =PDao.getAddedUsersTo(projects.getId());
                            if(user_ids.isEmpty()){
                                System.out.println("There is no user associated with this project");
                            }
                            for(Integer user_id : user_ids){
                                System.out.println(user_id);
                            }
                            break;
                        }
                        case 9:{
                            sc.nextLine();
                            System.out.println("Project "+projects.getName()+"is going to be deleted!");
                            System.out.println("Do you want to delete this project?(Y/N)");
                            String delete = sc.nextLine();
                            if(delete.equals("Y")){
                                boolean deleted = PDao.delete(projects.getId());
                                if(deleted){
                                    SessionManager.setCurrentProject(null);
                                    System.out.println("Project deleted");
                                }else {
                                    System.out.println("Project "+projects.getName()+"failed to delete.");
                                }
                            }
                            break;
                        }
                        case 10:{
                            System.out.println("Creating a new Task in current open project: "+projects.getName());
                            Task t = createTask(user.getId(), projects.getId());
                            System.out.println("Task created:\n"+ t);
                            SessionManager.setCurrentTask(t);
                            break;
                        }
                        case 11:{
                            sc.nextLine();
                            System.out.println("Updating a task "+task.getName()+" in current open project: "+projects.getName());
                            Task t = new Task();
                            System.out.println("Please enter new task name: ");
                            String newTaskName = sc.nextLine();
                            System.out.println("Please enter new task description: ");
                            String newDescription = sc.nextLine();

                            Status st = statusChange(sc);

                            t.setName(newTaskName);
                            t.setDescription(newDescription);
                            t.setStatus(st);

                            boolean update = TDao.update(t, task.getId());
                            if(update){
                                System.out.println("Task updated");
                            }else {
                                System.out.println("Task failed to update");
                                break;
                            }
                            break;
                        }
                        case 12:{
                            sc.nextLine();
                            System.out.println("Please enter your searched task name: ");
                            String newName = sc.nextLine();
                            Task ts = TDao.findByName(newName);
                            if(ts == null){
                                System.out.println("Task not found");
                                break;
                            }else {
                                SessionManager.setCurrentTask(ts);
                                System.out.println("Task found");
                                System.out.println(ts);
                            }
                            break;
                        }
                        case 13:{
                            sc.nextLine();
                            System.out.println("Removing a user from a task: "+ task.getName());
                            System.out.println("Please enter your searched user name to be removed: ");
                            String newUserName = sc.nextLine();
                            Users u = UDao.findByName(newUserName);
                            if(u == null){
                                System.out.println("User not found");
                                break;
                            }
                            if(TDao.removeUserFrom(task.getId(),u.getId())){
                                System.out.println("User is removed successfully!");
                            }
                            break;
                        }
                        case 14:{
                            sc.nextLine();
                            System.out.println("Changing status of a task "+task.getName());
                            Status st = statusChange(sc);

                            boolean changeStatus = TDao.changeStatus(task.getId(), st);
                            if(changeStatus){
                                System.out.println("Task status changed");
                            }else {
                                System.out.println("Task status not changed");
                            }
                            break;
                        }
                        case 15:{
                            sc.nextLine();
                            System.out.println("Adding user to a task: "+task.getName());
                            System.out.println("Please enter the username of user you want to add: ");
                            String username = sc.nextLine();
                            Users u = UDao.findByName(username);
                            if(TDao.addUserTo(task.getId(),u.getId())){
                                System.out.println("User: "+u.getUsername()+" added!");
                            }else {
                                System.out.println("User not found");
                            }
                            break;
                        }
                        case 16:{
                            System.out.println("Users assigned to the current task: "+task.getName());
                            List<Integer> usersInTask = TDao.getAddedUsersTo(task.getId());
                            for(Integer userId : usersInTask){
                                System.out.println(userId);
                            }
                            break;
                        }
                        case 17:{
                            sc.nextLine();
                            System.out.println("Current task: "+task.getName()+" is going to be deleted.");
                            System.out.println("Do you want to delete this task? [Y/N]");
                            String delete = sc.nextLine();
                            if(delete.equals("Y")){
                                if(TDao.delete(task.getId())){
                                    SessionManager.setCurrentTask(null);
                                    System.out.println("Your task has been deleted successfully.");
                                }else {
                                    System.out.println("Your task could not be deleted.");
                                    break;
                                }
                            }
                            break;
                        }
                        case 18:{
                            System.out.println("Please enter your new username : ");
                            String newUsername = sc.nextLine();
                            Users newUser = new Users();
                            newUser.setUsername(newUsername);
                            if(UDao.update(newUser, user.getId())){
                                System.out.println("Username updated successfully");
                            }else {
                                System.out.println("Username not updated");
                            }
                            break;
                        }
                        case 19:{
                            System.out.println("Enter the name of User who you are searching for: ");
                            String username = sc.nextLine();
                            Users findUser = UDao.findByName(username);
                            if(findUser != null) {
                                PrintUserProfile(findUser);
                            }else {
                                System.out.println("User not found");
                            }
                            break;
                        }
                        case 20:{
                            List<Project> createdByUserProjects = PDao.findByUserId(user.getId());
                            for (Project project : createdByUserProjects) {
                                System.out.println(project.toString());
                            }
                            break;
                        }
                        case 21:{
                            System.out.println("Displaying all projects in which the user is assigned: ");
                            List<Integer> prs = PDao.getAllByUser(user.getId());
                            for(Integer projectId : prs) {
                                System.out.println(projectId);
                            }
                            break;
                        }
                        case 22:{
                            System.out.println("All tasks created by user");
                            List<Task> createdByUserTasks = TDao.findByUserId(user.getId());
                            for(Task tasks : createdByUserTasks) {
                                System.out.println(tasks.toString());
                            }
                            break;
                        }
                        case 23:{
                            System.out.println("All tasks which assign user");
                            List<Integer> assignUserTasks =TDao.getAllByUser(user.getId());
                            for(Integer taskId : assignUserTasks) {
                                System.out.println(taskId);
                            }
                            break;
                        }
                        case 24:{

                            PrintUserProfile(user);
                            break;
                        }
                        case 25:{
                            sc.nextLine();
                            System.out.println("Your account is going to be deleted");
                            System.out.println("Are you sure you want to delete your Account? (Y/N)");
                            String answer = sc.nextLine();
                            if(answer.equalsIgnoreCase("Y")) {
                                int userId = user.getId();
                                SessionManager.logout();
                                System.out.println(UDao.delete(userId));
                            }else {
                                break;
                            }

                            break;
                        }
                        case 26:{
                            SessionManager.logout();
                            System.out.println("Logout completed");
                            break;
                        }
                        case 27:{
                            System.out.println("GoodBye!!");
                            running = false;
                            break;
                        }
                        default:{
                            System.out.println("Please enter a valid choice");
                        }
                    }
                }

            }
        }

        sc.close();
    }

    private static Status statusChange(Scanner sc) {
        System.out.println("Please enter new task status:((1) for In_Progress, (2) for Completed, (3) for Failed,(4) for Aborted) ");
        String status = sc.nextLine();
        Status st = null;
        switch (status){
            case "1":{
                st = Status.IN_PROGRESS;
                break;
            }
            case "2":{
                st = Status.COMPLETED;
                break;
            }
            case "3":{
                st = Status.FAILED;
                break;
            }
            case "4":{
                st = Status.ABORTED;
                break;
            }
            default:{
                System.out.println("Invalid task status");
            }
        }
        return st;
    }

    private static void findingProject(ProjectDAO PDao, Scanner sc) throws SQLException {
        sc.nextLine();
        String newName = sc.nextLine();
        Project newProject = PDao.findByName(newName);
        if(newProject == null){
            System.out.println("Project not found");
        }else {
            SessionManager.setCurrentProject(newProject);
            System.out.println("Project found");
            System.out.println(newProject);
        }

    }

    public static Task createTask(int user_id, int project_id) throws SQLException {
        Scanner sc = new Scanner(System.in);
        TaskDAO ts = new TaskDAO();
        System.out.println("Please enter the name of Task");
        String task_name = sc.nextLine();
        System.out.println("Please enter the description of Task");
        String task_description = sc.nextLine();

        Task newTask = new Task();
        newTask.setProject_id(project_id);
        newTask.setStartDate(LocalDate.now());
        if(newTask.getStatus()== Status.COMPLETED){
            newTask.setEndDate(LocalDate.now());
        }
        newTask.setStatus(Status.IN_PROGRESS);
        newTask.setName(task_name);
        newTask.setDescription(task_description);
        newTask.setCreatedBy(user_id);

       int id =  ts.save(newTask);
       newTask.setId(id);

        return newTask;
    }

    public static Project createProject(int user_id) throws SQLException {
        Scanner sc = new Scanner(System.in);
        ProjectDAO projectFunc = new ProjectDAO();

        System.out.println("Enter the name of the project: ");
        String name = sc.nextLine();
        System.out.println("Enter the description of the project: ");
        String description = sc.nextLine();

        Project project = new Project();
        LocalDate date = LocalDate.now();

        project.setName(name);
        project.setDescription(description);
        project.setCreatedByUserId(user_id);
        project.setCreatedDate(date);



        int id = projectFunc.save(project);
        project.setId(id);


        return project;
    }

    public static void PrintUserProfile(Users user) throws SQLException {

        ProjectDAO projectDAO = new ProjectDAO();
        List<Integer> project_ids = projectDAO.getAllByUser(user.getId());

        System.out.println("\tProfile"+user);

        System.out.println("\nProjects in which "+user.getUsername().toUpperCase()+" with id: "+user.getId()+" is assigned to");
        for (Integer project_id : project_ids) {
            System.out.println(project_id);
        }

        System.out.println("\nProjects which are created by "+user.getUsername());
        List<Project> createdByUserProjects = projectDAO.findByUserId(user.getId());
        for (Project project : createdByUserProjects) {
            System.out.println(project.toString());
        }
    }
}