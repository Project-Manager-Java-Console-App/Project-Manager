package org.example.Ui;

public class DisplayChoiceMenu {

    public static void DisplayAuthMenu(){
        System.out.println("""
                            (1) Registration
                            (2) Login
                            (3) Exit
                            """);
    }

    public static void DisplayAfterLoginMenu(){
        System.out.println("""
                            (1) Create New Project
                            (2) Find Project By Name
                            (3) Logout
                            (4) Exit
                            """);
    }

    public static void DisplayFuncMenu(){
        System.out.println("""
                (1) Create New Project
                (2) Edit Project
                (3) Find Project By Name
                (4) Find Project By Id
                (5) Add task to Project
                (6) Add User To Project
                (7) Remove User From Project
                (8) Display Tasks in Project
                (9) Display Users in Project
                (10) Delete Project
                (11) Create New Task
                (12) Edit Task
                (13) Find Task By Name
                (14) Find Task By Id
                (15) Remove User from Task
                (16) Change Task change
                (17) Add User To Task
                (18) Display User in Task
                (19) Delete Task
                (20) Edit User username
                (21) Find User By Name
                (22) Find User By Id
                (23) Display Projects created by User
                (24) Display Projects which assign User
                (25) Display Tasks created by User
                (26) Display Tasks which assign User
                (27) Display User profile
                (28) Delete User
                (29) Log out
                (30) Exit
                """);
    }
}
