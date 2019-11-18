package models;

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
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getStringDate() {
        return stringDate;
    }

    public void setStringDate(String stringDate) {
        this.stringDate = stringDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

