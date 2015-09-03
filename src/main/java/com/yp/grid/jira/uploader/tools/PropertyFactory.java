package com.yp.grid.jira.uploader.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * @author: Jordi Diaz
 *
 * The Property Factory provides access to all configurable properties
 */

public class PropertyFactory {

    private static String username;
    private static String password;
    private static String inputFilePath;
    private static String apiMain;
    private static String apiPath;
    private static String authPath;
    private static String issueMetaPath;
    private static String propFilePath;

    public PropertyFactory() {

    }

    public PropertyFactory(String propFilePath){
        this.propFilePath = propFilePath;
        try {
            parseProperties(propFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setUsername(String username) {
        PropertyFactory.username = username;
    }

    public static String getUsername() {
        return username;
    }

    public static void setPassword(String password) {
        PropertyFactory.password = password;
    }

    public static String getPassword() {
        return password;
    }

    public static void setInputFilePath(String filepath) {
        PropertyFactory.inputFilePath = filepath;
    }

    public static String getInputFilePath() {
        return inputFilePath;
    }

    public static void setApiMain(String apiMain) {
        PropertyFactory.apiMain = apiMain;
    }

    public static String getApiMain() {
        return apiMain;
    }

    public static void setApiPath(String apiPath) {
        PropertyFactory.apiPath = apiPath;
    }

    public static String getApiPath() {
        return apiPath;
    }

    public static void setAuthPath(String authPath) {
        PropertyFactory.authPath = authPath;
    }

    public static String getAuthPath() {
        return authPath;
    }

    public static void setIssueMetaPath(String issueMetaPath) {
        PropertyFactory.issueMetaPath = issueMetaPath;
    }

    public static String getIssueMetaPath() {
        return issueMetaPath;
    }

    public void setPropFile(String propFile) {
        PropertyFactory.propFilePath = propFile;
    }

    public static String getPropFilePath() {
        return propFilePath;
    }

    private void parseProperties(String propFile) throws IOException {
        File propertyFile = new File(propFile);
        FileInputStream mainInputStream = new FileInputStream(propertyFile);
        Properties mainProperties = new Properties();
        try {
            mainProperties.load(mainInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mainInputStream.close();
        }
        Set<Object> keys = mainProperties.keySet();
        for(Object k : keys){
            String key = (String)k;
            if(key.matches("username")){
                this.username = mainProperties.getProperty(key);
            }
            else if(key.matches("password")){
                this.password = mainProperties.getProperty(key);
            }
            else if(key.matches("input.filepath")){
                this.inputFilePath = mainProperties.getProperty(key);
            }
            else if(key.matches("api.main")){
                this.apiMain = mainProperties.getProperty(key);
            }
            else if(key.matches("api.path")){
                this.apiPath = mainProperties.getProperty(key);
            }
            else if(key.matches("api.auth")){
                this.authPath = mainProperties.getProperty(key);
            }
            else if(key.matches("api.issue.meta")){
                this.issueMetaPath = mainProperties.getProperty(key);
            }
        }
    }

    @Override
    public String toString() {
        return "PropertyFactory{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", inputFilePath='" + inputFilePath + '\'' +
                ", apiMain='" + apiMain + '\'' +
                ", apiPath='" + apiPath + '\'' +
                ", authPath='" + authPath + '\'' +
                ", propFilePath='" + propFilePath + '\'' +
                '}';
    }
}
