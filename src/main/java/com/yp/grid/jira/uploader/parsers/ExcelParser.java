package com.yp.grid.jira.uploader.parsers;

import java.io.*;
import java.util.*;

import com.yp.grid.jira.uploader.model.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;

/**
 * Created by jd5582 on 8/24/15.
 */
public class ExcelParser {

    public ArrayList<Task> parseExcelIntoTasks(String filePath) throws IOException {
        ArrayList<Task> tasks = new ArrayList<Task>();
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (String.valueOf(row.getCell(1)).toLowerCase().matches("task")) {
                    Task task = new Task();
                    task.setProject(String.valueOf(row.getCell(0)));
                    task.setIssueType(String.valueOf(row.getCell(1)));
                    task.setAssignee(String.valueOf(row.getCell(2)));
                    task.setReporter(String.valueOf(row.getCell(3)));
                    task.setComponent(String.valueOf(row.getCell(4)));
                    task.setSprint(String.valueOf(row.getCell(5)));
                    task.setEpic(String.valueOf(row.getCell(6)));
                    task.setDescription(String.valueOf(row.getCell(7)));
                    task.setSummary(String.valueOf(row.getCell(8)));
                    int i = (int) Double.parseDouble(row.getCell(9).toString());
                    task.setStoryPoints(i);
                    int h = (int) Double.parseDouble(row.getCell(10).toString());
                    task.setLOE(h);
                    int j = (int) Double.parseDouble(row.getCell(11).toString());
                    task.setJupiterID(j);
                    task.setBoard(String.valueOf(row.getCell(12)));
                    if (row.getCell(13).toString().toLowerCase().matches("x")) {
                        task.setCreateTask(true);
                    }
                    tasks.add(task);
                }
                if (String.valueOf(row.getCell(1)).toLowerCase().matches("subtask")) {
                    SubTask subtask = new SubTask();
                    subtask.setProject(String.valueOf(row.getCell(0)));
                    subtask.setIssueType(String.valueOf(row.getCell(1)));
                    subtask.setAssignee(String.valueOf(row.getCell(2)));
                    subtask.setReporter(String.valueOf(row.getCell(3)));
                    subtask.setComponent(String.valueOf(row.getCell(4)));
                    subtask.setSprint(String.valueOf(row.getCell(5)));
                    subtask.setEpic(String.valueOf(row.getCell(6)));
                    subtask.setDescription(String.valueOf(row.getCell(7)));
                    subtask.setSummary(String.valueOf(row.getCell(8)));
                    int i = (int) Double.parseDouble(row.getCell(9).toString());
                    subtask.setStoryPoints(i);
                    int h = (int) Double.parseDouble(row.getCell(10).toString());
                    subtask.setLOE(h);
                    int j = (int) Double.parseDouble(row.getCell(11).toString());
                    subtask.setJupiterID(j);
                    subtask.setBoard(String.valueOf(row.getCell(12)));
                    if (row.getCell(13).toString().toLowerCase().matches("x")) {
                        subtask.setCreateTask(true);
                    }
                    subtask.setParentTask(tasks.get(tasks.size() - 1));
                    tasks.get(tasks.size() - 1).addSubTask(subtask);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
