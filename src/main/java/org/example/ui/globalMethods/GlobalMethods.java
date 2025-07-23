package org.example.ui.globalMethods;

import org.example.model.Status;

import java.util.Scanner;

public class GlobalMethods {

    public static Status enterStatus(Scanner scanner) {
        System.out.println("Enter new status (1)IN_PROGRESS, (2)COMPLETED, (3)FAILED, (4)ABORTED): ");
        int status = scanner.nextInt();
        Status newStatus = null;
        switch (status) {
            case 1: {
                newStatus = Status.IN_PROGRESS;
                break;
            }
            case 2: {
                newStatus = Status.COMPLETED;
                break;
            }
            case 3: {
                newStatus = Status.FAILED;
                break;
            }
            case 4: {
                newStatus = Status.ABORTED;
                break;
            }
            default: {
                System.out.println("Invalid status");
                break;
            }
        }
        return newStatus;
    }
}
