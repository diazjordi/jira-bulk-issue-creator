package com.yp.grid.jira.uploader.tools;

import com.atlassian.jira.rest.client.*;
import com.atlassian.jira.rest.client.domain.BasicIssue;
import com.atlassian.jira.rest.client.domain.Issue;
import com.atlassian.jira.rest.client.domain.User;
import com.atlassian.jira.rest.client.domain.input.IssueInput;
import com.atlassian.jira.rest.client.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;

import java.net.URI;

/**
 * Created by jd5582 on 8/24/15.
 */
public class JiraClientCreator {

    private PropertyFactory properties;

    private JiraRestClient jiraRestClient;
    private MetadataRestClient metadataRestClient;
    private ProjectRestClient projectRestClient;

    public JiraClientCreator() {
    }

    public JiraClientCreator(PropertyFactory properties) {
        this.properties = properties;
    }

    public JiraRestClient createJiraRestClient() throws Exception {
        try {
            JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
            URI uri = new URI(properties.getApiMain());
            jiraRestClient = factory.createWithBasicHttpAuthentication(uri, properties.getUsername(), properties.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jiraRestClient;
    }

    public MetadataRestClient createMetaDataRestClient() throws Exception {
        if(jiraRestClient == null){
            createJiraRestClient();
        }
        try {
            metadataRestClient = jiraRestClient.getMetadataClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return metadataRestClient;
    }

    public ProjectRestClient createProjectRestClient() throws Exception {
        if(jiraRestClient == null){
            createJiraRestClient();
        }
        try {
            projectRestClient = jiraRestClient.getProjectClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projectRestClient;
    }

    public PropertyFactory getProperties() {
        return properties;
    }

    public void setProperties(PropertyFactory properties) {
        this.properties = properties;
    }

}
