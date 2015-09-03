package com.yp.grid.jira.uploader.tools;

import java.util.List;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.greenhopper.*;


/**
 * Created by jd5582 on 9/2/15.
 */
public class GHClient {

    private static PropertyFactory properties = new PropertyFactory();

    private static BasicCredentials creds = new BasicCredentials(properties.getUsername(), properties.getPassword());
    private static JiraClient jira = new JiraClient(properties.getApiMain(), creds);
    private static GreenHopperClient gh = new GreenHopperClient(jira);

    public static void findSprint(String boardName, String sprintName) {
        boardName = boardName.toLowerCase();
        sprintName = sprintName.toLowerCase();
        try {
            /* Retrieve all Rapid Boards */
            List<RapidView> allRapidBoards = gh.getRapidViews();

            int boardNum = 0;
            for(RapidView rapidView : allRapidBoards){
                if(rapidView.getName().toLowerCase().matches(boardName)){
                    boardNum = rapidView.getId();
                    System.out.println("Rapid View Name: " + rapidView.getName());
                    System.out.println("Rapid View ID: " + rapidView.getId());
                }
            }

            /* Retrieve a specific Rapid Board by ID */
            RapidView board = gh.getRapidView(boardNum);

            /* Print the name of all current and past sprints */
            List<Sprint> sprints = board.getSprints();
            for (Sprint sprint : sprints){
                if(sprint.getName().toLowerCase().matches(sprintName)){
                    System.out.println("Sprint Name: " + sprint.getName());
                    System.out.println("Sprint ID: " + sprint.getId());
                    SprintReport report = board.getSprintReport(sprint);
                }
            }

        } catch (JiraException ex) {
            System.err.println(ex.getMessage());
            if (ex.getCause() != null)
                System.err.println(ex.getCause().getMessage());
        }
    }
}
