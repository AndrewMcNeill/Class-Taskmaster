package ui;

import database.Database;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import models.Task;
import sample.Main;
import java.time.LocalDate;

public class TaskBox extends HBox {

    private LocalDate date;

    private TextField taskTitle     = new TextField();
    private DatePicker taskDate     = new DatePicker();
    private TagsButton tagsButton   = new TagsButton();

    TaskBox() {

        taskTitle.setPromptText("Add A Task...");
        taskDate.setEditable(false);
        taskDate.getEditor().setDisable(true);
        taskDate.setPromptText("Pick a Date...");
        taskDate.setOnAction(e -> {
           date = taskDate.getValue();

        });

        Button addTask = new Button("Add Task");
        addTask.setOnMouseClicked(e->{
            if(!(taskTitle.getText().equals(""))) {
                String tag = tagsButton.tagsButton.getText().equals("Tags") ? "No Tag!" : tagsButton.tagsButton.getText();     //default to no tag if isnt chosen
                date = date == null ? LocalDate.now() : date;                                               //default to todays date if no date entered
                Task task = new Task(0, taskTitle.getText(), date, "", tag, false);
                Main.addTask(task);
                Database.getInstance().insertTask(task);
                clearUI();
            }

        });
        this.getChildren().addAll(taskTitle, taskDate, tagsButton.tagsButton, addTask);
    }

    private void clearUI() {
        tagsButton.clearTagButton();
        taskDate.getEditor().clear();
        taskTitle.clear();
    }

}

