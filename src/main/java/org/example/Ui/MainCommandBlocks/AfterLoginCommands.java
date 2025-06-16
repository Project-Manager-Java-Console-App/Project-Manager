package org.example.Ui.MainCommandBlocks;

import org.example.Service.ProjectService;
import org.example.Ui.AuthCommands.ExitCommand;
import org.example.Ui.AuthCommands.LogOutCommand;
import org.example.Ui.Command;
import org.example.Ui.ProjectCommands.CreateProjectCommand;
import org.example.Ui.ProjectCommands.FindByNameProject;
import org.example.dataBase.DatabaseUtils;

import java.util.Scanner;

public class AfterLoginCommands {

   public static Command getCommand(int choice, ProjectService projectService, Scanner scanner, DatabaseUtils dbUtils) {
       return switch (choice){
           case 1 -> new CreateProjectCommand(projectService,scanner);
           case 2 -> new FindByNameProject(projectService,scanner);
           case 3 -> new LogOutCommand();
           case 4 -> new ExitCommand(dbUtils);
           default -> () ->System.out.println("Invalid choice");
       };
   }
}
