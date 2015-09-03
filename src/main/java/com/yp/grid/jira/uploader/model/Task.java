package com.yp.grid.jira.uploader.model;


import java.util.ArrayList;

/**
 * Created by jd5582 on 8/24/15.
 */
public class Task {

    private String project;
    private String issueType;
    private String assignee;
    private String reporter;
    private String component;
    private String sprint;
    private String epic;
    private String description;
    private String summary;
    private ArrayList<SubTask> subTasks = new ArrayList<SubTask>();
    private int storyPoints;
    private int LOE;
    private int jupiterID;
    private String board;

    private boolean createTask;

    public Task() {
    }

    public Task(String project, String issueType, String assignee, String reporter, String component, String sprint, String epic, String description, String summary, ArrayList<SubTask> subTasks, int storyPoints, int LOE, int jupiterID, String board, boolean createTask) {
        this.project = project;
        this.issueType = issueType;
        this.assignee = assignee;
        this.reporter = reporter;
        this.component = component;
        this.sprint = sprint;
        this.epic = epic;
        this.description = description;
        this.summary = summary;
        this.subTasks = subTasks;
        this.storyPoints = storyPoints;
        this.LOE = LOE;
        this.jupiterID = jupiterID;
        this.board = board;
        this.createTask = createTask;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getSprint() {
        return sprint;
    }

    public void setSprint(String sprint) {
        this.sprint = sprint;
    }

    public String getEpic() {
        return epic;
    }

    public void setEpic(String epic) {
        this.epic = epic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(ArrayList<SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    public void addSubTask(SubTask subtask){
        this.subTasks.add(subtask);
    }

    public int getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(int storyPoints) {
        this.storyPoints = storyPoints;
    }

    public int getLOE() {
        return LOE;
    }

    public void setLOE(int LOE) {
        this.LOE = LOE;
    }

    public int getJupiterID() {
        return jupiterID;
    }

    public void setJupiterID(int jupiterID) {
        this.jupiterID = jupiterID;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public boolean isCreateTask() {
        return createTask;
    }

    public void setCreateTask(boolean createTask) {
        this.createTask = createTask;
    }

    @Override
    public String toString() {
        return "Task{" +
                "project='" + project + '\'' +
                ", issueType='" + issueType + '\'' +
                ", assignee='" + assignee + '\'' +
                ", reporter='" + reporter + '\'' +
                ", component='" + component + '\'' +
                ", sprint='" + sprint + '\'' +
                ", epic='" + epic + '\'' +
                ", description='" + description + '\'' +
                ", summary='" + summary + '\'' +
                ", subTasks=" + subTasks +
                ", storyPoints=" + storyPoints +
                ", LOE=" + LOE +
                ", jupiterID=" + jupiterID +
                ", board='" + board + '\'' +
                ", createTask=" + createTask +
                '}';
    }
}
