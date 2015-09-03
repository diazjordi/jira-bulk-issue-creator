package com.yp.grid.jira.uploader.main;

import com.yp.grid.jira.uploader.model.Task;
import com.yp.grid.jira.uploader.parsers.ExcelParser;
import com.yp.grid.jira.uploader.tools.IssueManager;
import com.yp.grid.jira.uploader.tools.PropertyFactory;

import java.util.*;

/**
 * Created by jd5582 on 8/21/15.
 */
public class JiraUploaderMain {

    private static ExcelParser xlParser = new ExcelParser();
    private static PropertyFactory properties = new PropertyFactory();

    public static void main(String[] args) throws Exception {

        getUserInfo(); // good

        ArrayList<Task> tasks = xlParser.parseExcelIntoTasks(properties.getInputFilePath()); // good

        IssueManager manager = new IssueManager(); // good

        manager.createTasks(tasks); //

        System.out.println("Done!");

        System.exit(0);
    }

    private static void getUserInfo() {
        Scanner scanner = new Scanner(System.in);
        // Ask user for credentials or property file
        System.out.println("Welcome to JIRA-Uploader!");
        System.out.print("Enter 1 to enter property file location, or enter 2 to exit: ");
        String response = scanner.next();
        if (response.matches("1")) {
            System.out.print("Enter Property File Path: ");
            String propFilePath = scanner.next();
            if (validatePropFile(propFilePath)) {
                properties = new PropertyFactory(propFilePath);
            } else {
                getUserInfo();
            }
        } else if (response.matches("2")) {
            System.exit(0);
        } else {
            System.out.println("This is not hard, enter 1 or 2 dingus!");
            System.out.println();
            System.out.println("Let's try this again");
            getUserInfo();
        }
    }

    private static boolean validatePropFile(String filePath) {
        boolean properFile = true;
        if (!filePath.endsWith(".properties")) {
            System.out.println("Not a proper property file");
            properFile = false;
        }
        return properFile;
    }

}
