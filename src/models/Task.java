package models;

import database.Database;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {

    private int id;
    private String title;
    private LocalDate date;
    private String stringDate;
    private String description;
    private String tag;
    private boolean completed;

    public Task(int id, String title, LocalDate date, String description, String tag, boolean completed) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.stringDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.description = description;
        this.tag = tag;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        Database.getInstance().sqlQuery(
                "UPDATE `tasks` SET title = '" + title + "' WHERE taskid="+id+";",
                true);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
        this.setStringDate(this.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        //TODO: Add an AND clause to the outside statement WHERE
        // to add support for multiple tags
        Database.getInstance().sqlQuery(
                "UPDATE `tagstaskrelational` SET tagid = (" +
                        "SELECT tagid FROM `tags` WHERE tagname = '" + tag + "'" +
                        ") WHERE taskid="+id+";",
                true
        );
        this.tag = tag;
        //INSERT INTO tagstaskrelational VALUES((SELECT LAST_INSERT_ID() FROM tasks LIMIT 1),(SELECT tagid FROM tags WHERE tagname = '" +
        //                task.getTag() + "' LIMIT 1))
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
        Database.getInstance().sqlQuery(
                "UPDATE `tasks` SET completed = '" +
                        (completed ? 1 : 0)
                        + "' WHERE taskid="+id+";",
                true);
    }

    public String getStringDate() {
        return stringDate;
    }

    public void setStringDate(String stringDate) {
        this.stringDate = stringDate;
        Database.getInstance().sqlQuery(
                "UPDATE `tasks` SET date = '" + stringDate + "' WHERE taskid="+id+";",
                true);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        Database.getInstance().sqlQuery(
                "UPDATE `descriptions` SET description = '"+ description +"' " +
                        "WHERE taskid = "+id+";",
                true
        );
    }
}

