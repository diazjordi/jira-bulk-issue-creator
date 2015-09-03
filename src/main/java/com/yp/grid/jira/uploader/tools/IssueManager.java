package com.yp.grid.jira.uploader.tools;

import com.atlassian.jira.rest.client.*;
import com.atlassian.jira.rest.client.domain.*;
import com.atlassian.jira.rest.client.domain.input.*;
import com.atlassian.util.concurrent.Promise;
import com.yp.grid.jira.uploader.model.*;

import java.util.*;

/**
 * Created by jd5582 on 8/31/15.
 */
public class IssueManager {

    private JiraClientCreator jiraClientCreator = new JiraClientCreator();
    private PropertyFactory properties = new PropertyFactory();
    private HashMap<String, String> priorities = new HashMap<String, String>();
    private HashMap<String, String> projects = new HashMap<String, String>();
    private JiraRestClient jiraRestClient;
    private IssueRestClient issueRestClient;
    private User user;
    private BasicIssue parentIssue;

    public IssueManager() throws Exception {
        try {
            jiraClientCreator.setProperties(properties);
            this.createRestClient();
            this.claimUser();
            this.getProjects();
            this.getPriorities();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTasks(ArrayList<Task> tasks) throws Exception {
        issueRestClient = jiraRestClient.getIssueClient();
        for (Task task : tasks) {
            if (task.isCreateTask()) {
                String project = findProjectKey(task.getProject());
                long type = findIssueTypeID(task.getIssueType());
                BasicComponent component = getComponent(task.getProject(), task.getComponent());
                IssueInputBuilder builder = new IssueInputBuilder(project, type);
                builder.setSummary(task.getSummary());
                if (component == null) {
                    Iterable<BasicComponent> components = getComponents(task.getProject());
                    builder.setComponents(components);
                } else {
                    builder.setComponents(component);
                }
                builder.setAssigneeName(task.getAssignee());                                     // ASSIGNEE
                builder.setDescription(task.getDescription());                                   // DESCRIPTION
                builder.setFieldValue("customfield_23650", String.valueOf(task.getJupiterID())); // JUPITER ID
                builder.setFieldValue("customfield_10482", task.getStoryPoints());               // STORY POINTS
                builder.setFieldValue("timetracking",                                            // TIME ESTIMATE (IN HRS)
                        ComplexIssueInputFieldValue.with("originalEstimate", String.valueOf(task.getLOE() + "h")) );
                IssueInput issueinput = builder.build();
                parentIssue = issueRestClient.createIssue(issueinput).claim();
                if (task.getSubTasks().size() > 0) {
                    for (SubTask subtask : task.getSubTasks()) {
                        if (subtask.isCreateTask()) {
                            String projectSub = findProjectKey(subtask.getProject());
                            long typeSub = findIssueTypeID(subtask.getIssueType());
                            BasicComponent componentSub = getComponent(subtask.getProject(), subtask.getComponent());
                            IssueInputBuilder builderSub = new IssueInputBuilder(projectSub, typeSub);
                            builderSub.setSummary(subtask.getSummary());
                            if (componentSub == null) {
                                Iterable<BasicComponent> componentsSub = getComponents(subtask.getProject());
                                builderSub.setComponents(componentsSub);
                            } else {
                                builderSub.setComponents(componentSub);
                            }
                            builderSub.setAssigneeName(subtask.getAssignee());
                            builderSub.setDescription(subtask.getDescription());
                            builderSub.setFieldValue("customfield_23650", String.valueOf(task.getJupiterID()));
                            builderSub.setFieldValue("customfield_10482", subtask.getStoryPoints());
                            builderSub.setFieldValue("parent", ComplexIssueInputFieldValue.with("key", parentIssue.getKey()));
                            builderSub.setFieldValue("timetracking",
                                    ComplexIssueInputFieldValue.with("originalEstimate", String.valueOf(subtask.getLOE() + "h")) );
                            IssueInput issueInputSub = builderSub.build();
                            issueRestClient.createIssue(issueInputSub).claim();
                        }
                    }
                }
            }
        }
    }

    private void createRestClient() throws Exception {
        JiraClientCreator jiraClientCreator = new JiraClientCreator();
        jiraRestClient = jiraClientCreator.createJiraRestClient();
    }

    private User claimUser() throws Exception {
        try {
            Promise<User> promise = jiraRestClient.getUserClient().getUser(properties.getUsername());
            user = promise.claim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    private void getProjects() throws Exception {
        try {
            ProjectRestClient projectRestClient = jiraClientCreator.createProjectRestClient();
            Promise<Iterable<BasicProject>> projectTypePromise = projectRestClient.getAllProjects();
            Iterable<BasicProject> projects = projectTypePromise.claim();
            Iterator<BasicProject> iterator2 = projects.iterator();
            while (iterator2.hasNext()) {
                BasicProject projectos = iterator2.next();
                this.projects.put(projectos.getName(), projectos.getKey());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPriorities() throws Exception {
        try {
            MetadataRestClient metadataRestClient = jiraClientCreator.createMetaDataRestClient();
            Promise<Iterable<Priority>> prioritiesPromise = metadataRestClient.getPriorities();
            Iterable<Priority> priorities = prioritiesPromise.claim();
            Iterator<Priority> iterator1 = priorities.iterator();
            while (iterator1.hasNext()) {
                Priority priority = iterator1.next();
                this.priorities.put(priority.getName(), priority.getId().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BasicComponent getComponent(String project, String componentName) throws Exception {
        BasicComponent component = null;
        try {
            ProjectRestClient projectRestClient = jiraClientCreator.createProjectRestClient();
            Promise<Project> projectPromise = projectRestClient.getProject(project);
            Project projecto = projectPromise.claim();
            Iterable<BasicComponent> components = projecto.getComponents();
            Iterator<BasicComponent> iterator = components.iterator();
            while (iterator.hasNext()) {
                BasicComponent componentCompare = iterator.next();
                if (componentCompare.getName().matches(componentName)) {
                    component = componentCompare;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return component;
    }

    private Iterable<BasicComponent> getComponents(String project) throws Exception {
        Iterable<BasicComponent> components = null;
        try {
            ProjectRestClient projectRestClient = jiraClientCreator.createProjectRestClient();
            Promise<Project> projectPromise = projectRestClient.getProject(project);
            Project projecto = projectPromise.claim();
            components = projecto.getComponents();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return components;
    }

    public String findProjectKey(String projectIn) {
        projectIn = projectIn.toLowerCase();
        String projectOut = null;
        for (HashMap.Entry<String, String> entry : projects.entrySet()) {
            String entryValue = entry.getValue().toLowerCase();
            if (entryValue.matches(projectIn)) {
                projectOut = entryValue.toUpperCase();
            }
        }
        return projectOut;
    }

    public long findIssueTypeID(String issueType) {
        issueType = issueType.toLowerCase();
        long type = 0;
        if (issueType.matches("issue")) {
            type = 1;
        } else if (issueType.matches("plan")) {
            type = 2;
        } else if (issueType.matches("task")) {
            type = 3;
        } else if (issueType.matches("subtask")) {
            type = 4;
        } else if (issueType.matches("subissue")) {
            type = 5;
        } else if (issueType.matches("subplan")) {
            type = 6;
        } else if (issueType.matches("epic")) {
            type = 113;
        } else {
            type = 1;
        }
        return type;
    }
}
